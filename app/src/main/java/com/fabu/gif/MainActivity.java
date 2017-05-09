package com.fabu.gif;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    ImageView imagePreview;
    Button btnRecord, btnSave;
    TextView videoPath;

    long maxDuration;

    GIFCreator gifCreator;
    MediaMetadataRetriever mMediaMetadataRetriever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRecord = (Button)findViewById(R.id.takeVideo);
        btnSave = (Button)findViewById(R.id.saveGif);
        imagePreview = (ImageView)findViewById(R.id.preview);
        videoPath = (TextView)findViewById(R.id.info);

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaMetadataRetriever != null) {
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            videoPath.setText(uri.toString());
            MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();

            try {
                metadataRetriever.setDataSource(getBaseContext(), uri);
                mMediaMetadataRetriever = metadataRetriever;
                String stringDuration = mMediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

                maxDuration = (long) (1000 * Double.parseDouble(stringDuration));
                Bitmap bmFrame = mMediaMetadataRetriever.getFrameAtTime();
                imagePreview.setImageBitmap(bmFrame);
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                Log.d(LOG_TAG, "Something went Wrong");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class GifCreatorTask extends AsyncTask<ArrayList<Bitmap>, Void, String> {

        @Override
        protected String doInBackground(ArrayList<Bitmap>... params) {
            try {
                GIF gif = gifCreator.createGIF(params[0]);
                String path = gif.getFileName();
                return path;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
}
