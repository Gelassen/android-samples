package com.home.vkphotos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.home.vkphotos.storage.Cache;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okio.BufferedSink;
import okio.Okio;

import static android.os.Environment.isExternalStorageRemovable;

public class ImageFetcherNew {

    private final int DISK_CACHE_INDEX = 0;

    private ExecutorService executors;
    private DiskLruCache diskLruCache;

    private final Object diskCacheLock = new Object();

    private HashMap<Integer, AsyncWorker> asyncData;

    private com.home.vkphotos.storage.Cache cache = new Cache();

    public ImageFetcherNew(Context context) {
        try {
            diskLruCache = DiskLruCache.open(getDiskCacheDir(context, "cache"), 1, 1,50*1024*1024);
        } catch (IOException e) {
            Log.e(App.TAG, "Failed to obtain data", e);
        }

        asyncData = new HashMap<>();
        executors = Executors.newFixedThreadPool(4);
    }

    public void submit(ImageView imageView, ImageBundle imageBundle) {
        AsyncWorker worker = asyncData.get(imageView.hashCode());
        if (worker != null) worker.cancel(true);

        worker = new AsyncWorker(imageView, imageBundle);
        worker.executeOnExecutor(executors);
        asyncData.put(imageView.hashCode(), worker);
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

    private class AsyncWorker extends AsyncTask<Void, Void, Bitmap> {

        private WeakReference<ImageView> imageView;
        private ImageBundle imageBundle;

        public AsyncWorker(ImageView imageView, ImageBundle imageBundle) {
            this.imageView = new WeakReference<>(imageView);
            this.imageBundle = imageBundle;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            if (isCancelled()) return null;

            Bitmap bitmap = null;
            try {
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.outHeight = imageView.get().getHeight();
                options.outWidth = imageView.get().getWidth();
                final String key = String.valueOf(imageBundle.getUrl().hashCode());//String.valueOf(imageView.get().hashCode());

//                bitmap = makeWebRequest(imageBundle);
//                bitmap = readFromCachedSnapshot(key, options);
                bitmap = cache.get(key);
                if (bitmap == null) {
                    Log.d(App.CACHE, "Get the fresh img");
                    bitmap = makeWebRequest(imageBundle);
//                    writeToCachedSnapshoot(key, bitmapToStream(bitmap));
                    cache.add(key, bitmapToStream(bitmap));
                } else {
                    Log.d(App.CACHE, "Hit the cache");
                }
            } catch (IOException e) {
                Log.e(App.TAG, "Failed to obtain bitmap", e);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if (isCancelled() && imageView.get() == null) return;

            Drawable drawable = new BitmapDrawable(imageView.get().getResources(), result);
            imageView.get().setBackground(drawable);
            asyncData.remove(imageView.get().hashCode());
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

        private void writeToCachedSnapshoot(String key, InputStream is) {
            final int bufferSize = 1024 * 1024;
            OutputStream out = null;
            synchronized (diskCacheLock) {
                try {
                    DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
                    if (snapshot == null) {
                        DiskLruCache.Editor editor = diskLruCache.edit(key);
                        if (editor != null) {
                            OutputStream stream = new BufferedOutputStream(editor.newOutputStream(0), bufferSize);
                            BufferedSink bufferedSink = Okio.buffer(Okio.sink(stream));
                            bufferedSink.writeAll(Okio.source(is));
                            editor.commit();

                            stream.close();
                        }
                    } else {
                        snapshot.getInputStream(DISK_CACHE_INDEX).close();
                    }

                } catch (IOException e) {
                    Log.e(App.TAG, "Failed to write to cache", e);
                }
            }
        }

        private Bitmap readFromCachedSnapshot(String key, BitmapFactory.Options opt) throws IOException {
            synchronized (diskCacheLock) {
                Bitmap bitmap = null;
                InputStream inputStream = null;
                try {
                    final DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
                    if (snapshot != null) {
                        inputStream = snapshot.getInputStream(DISK_CACHE_INDEX);
                        if (inputStream != null) {
                            bitmap = BitmapFactory.decodeStream(inputStream, null, opt);
                        }
                    }
                } catch (IOException ex) {
                    Log.e(App.TAG, "Failed to obtain cached data", ex);
                }
                return bitmap;
            }
        }

        private InputStream bitmapToStream(Bitmap bitmap) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();
            ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
            return bs;
        }
    }
}
