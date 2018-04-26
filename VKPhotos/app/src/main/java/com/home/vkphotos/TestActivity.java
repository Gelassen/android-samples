package com.home.vkphotos;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static android.os.Environment.isExternalStorageRemovable;

public class TestActivity extends AppCompatActivity {

    private ImageView imageView;

    private ImageFetcherNew imageFetcher;

    private Cache cache;

    private Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        cache = new Cache(getDiskCacheDir(this, "cache"));

        findViewById(R.id.cache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String str = "Hello world!";
                    ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));

//                    byte[] buffer = new byte[1024];
//                    BufferedInputStream buffStream = new BufferedInputStream(stream);
//                    buffStream.read(buffer);

                    cache.writeToCachedSnapshoot("hey", stream);

                    String strCached = cache.readFromCachedSnapshot("hey");

                    Log.d(App.CACHE, "Cached: " + strCached);
                } catch (Exception ex) {
                    Log.e(App.CACHE, "Processed data", ex);
                }
            }
        });

//        cache = new Cache(getDiskCacheDir(this, "cache"));
//
//        final ImageBundle imageBundle = new ImageBundle();
//        imageBundle.setUrl(Images.imageThumbUrls[0]);
//
//        findViewById(R.id.cache).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO complete me
//                Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        cache.writeToCachedSnapshoot(
//                                String.valueOf(imageView.hashCode()),
//                                cache.bitmapToStream(bitmap)
//                        );
//
//                        try {
//                            Bitmap bitmap = cache.readFromCachedSnapshot(String.valueOf(imageView.hashCode()), null);
//                            Log.d(App.CACHE, "Bitmap is not null: " + (bitmap != null));
//                        } catch (IOException e) {
//                            Log.e(App.CACHE, "Obtain data from cache", e);
//                        }
//                    }
//                });
//                thread.start();
//            }
//        });
//
//        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                AsyncTask task = new AsyncTask(imageView, imageBundle);
////                task.execute();
//
//                Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            bitmap = makeWebRequest(imageBundle);
//                        } catch (IOException e) {
//                            Log.e(App.CACHE, "Failed to obtain data", e);
//                        }
//                    }
//                });
//                thread.start();
//            }
//        });
//        imageView = findViewById(R.id.image);
    }

    public static File getDiskCacheDir(Context context, String uniqueName) {
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        final String cachePath =
                Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                        !isExternalStorageRemovable() ? getExternalCacheDir(context).getPath() :
                        context.getCacheDir().getPath();

        return new File(cachePath + File.separator + uniqueName);
    }

    public static File getExternalCacheDir(Context context) {
        return context.getExternalCacheDir();
    }

    public class AsyncTask extends android.os.AsyncTask<Void, Void, Bitmap> {

        private ImageView imageView;
        private ImageBundle imageBundle;

        public AsyncTask(ImageView imageView, ImageBundle imageBundle) {
            this.imageView = imageView;
            this.imageBundle = imageBundle;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.outHeight = imageView.getHeight();
            options.outWidth = imageView.getWidth();

            try {
                Bitmap bitmap = cache.readFromCachedSnapshot(String.valueOf(imageView.hashCode()), options);
                if (bitmap == null) {
                    Log.d(App.CACHE, "Request new data");
                    bitmap = makeWebRequest(imageBundle);
                    cache.writeToCachedSnapshoot(String.valueOf(imageView.hashCode()), cache.bitmapToStream(bitmap));
                } else {
                    Log.d(App.CACHE, "Hit the cache");
                }
                return makeWebRequest(imageBundle);
            } catch (IOException e) {
                Log.e(App.CACHE, "Failed to obtain data", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            BitmapDrawable drawable = new BitmapDrawable(imageView.getResources(), bitmap);
            imageView.setBackground(drawable);
        }

        private Bitmap makeWebRequest(ImageBundle imageBundle) throws IOException {
            final URL url = new URL(imageBundle.getUrl());
            InputStream is = url.openConnection().getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);

            if (is != null) {
                is.close();
            }
            return bitmap;
        }
    }

    private Bitmap makeWebRequest(ImageBundle imageBundle) throws IOException {
        final URL url = new URL(imageBundle.getUrl());
        InputStream is = url.openConnection().getInputStream();
        Bitmap bitmap = BitmapFactory.decodeStream(is);

        if (is != null) {
            is.close();
        }
        return bitmap;
    }
}
