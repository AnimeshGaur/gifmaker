package com.fabu.gif;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.fabu.gif.encoder.AnimatedGifEncoder;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class GIF {
    private static final String LOG_TAG = GIF.class.getSimpleName();

    ArrayList<Bitmap> bitmaps;
    byte[] gifBytes;
    long fps = 10;
    int delay = 1000;

    String fileName;

    public GIF(ArrayList<Bitmap> _bitmaps) {
        bitmaps = _bitmaps;
    }

    public String getFileName() {
        return fileName;
    }

    public String writeToFile() {
        try {
            if (gifBytes.length > 0) {
                String extStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();

                File outputFile = new File(extStorageDir, "/quif_" + Calendar.getInstance().getTimeInMillis() + ".gif");

                BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(outputFile));
                fos.write(create());
                fos.flush();
                fos.close();

                return (outputFile.getAbsolutePath() + "Saved");
            } else {
                Log.i(LOG_TAG, "gifBytes length is less than or eqauls to 0");
                return null;
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG,"error getting gif" + ex.getMessage());
            return null;
        }
    }

    public byte[] create() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        AnimatedGifEncoder animatedGifEncoder = new AnimatedGifEncoder();
        Log.i(LOG_TAG, "Delay: " + delay);

        animatedGifEncoder.setFrameRate(fps);
        animatedGifEncoder.setDelay(delay);
        animatedGifEncoder.setRepeat(0);
        animatedGifEncoder.setQuality(100);
        animatedGifEncoder.start(bos);

        for (int i = 0; i < bitmaps.size(); i++) {
            Log.i(LOG_TAG, "Adding frame");
            animatedGifEncoder.addFrame(bitmaps.get(i));
        }

        Log.i(LOG_TAG, "Finished adding frames");
        animatedGifEncoder.finish();
        gifBytes = bos.toByteArray();

        return gifBytes;
    }
}
