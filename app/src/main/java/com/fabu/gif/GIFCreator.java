package com.fabu.gif;

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

public class GIFCreator {
    private static final String LOG_TAG = GIFCreator.class.getSimpleName();

    public GIF createGIF(ArrayList<Bitmap> bitmaps) {
        GIF gif = new GIF(bitmaps);
        return gif;
    }
}
