package com.home.vkphotos.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.home.vkphotos.App;
import com.home.vkphotos.LifeCycleListener;
import com.home.vkphotos.photos.model.ImageBundle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageFetcher implements LifeCycleListener.LifeCycleCallbacks {

    private ExecutorService executors;
    private HashMap<Integer, AsyncWorker> asyncData;
    private Cache cache;

    private boolean isPausedWork = false;
    private Object pauseWorkLock = new Object();

    public ImageFetcher(Context context, LifeCycleListener lifeCycleListener) {
        cache = new Cache(context);
        asyncData = new HashMap<>();
        lifeCycleListener.subscribe(this);
        executors = Executors.newFixedThreadPool(8);
    }

    public void onPause() {
        synchronized (pauseWorkLock) {
            isPausedWork = true;
        }
    }

    public void onResume() {
        synchronized (pauseWorkLock) {
            isPausedWork = false;
            pauseWorkLock.notifyAll();
        }
    }

    public void submit(ImageView imageView, ImageBundle imageBundle) {
        AsyncWorker worker = asyncData.get(imageView.hashCode());
        if (worker != null) worker.cancel(true);

        imageView.setImageBitmap(null);
        worker = new AsyncWorker(imageView, imageBundle);
        worker.executeOnExecutor(executors);
        asyncData.put(imageView.hashCode(), worker);
    }

    @Override
    public void onLowMemoryCall() {
        cache.clean();
    }

    public void onCancel() {
        onPause();

        Set<Integer> keys = asyncData.keySet();
        for (int id = 0; id < keys.size(); id++) {
            AsyncWorker worker = asyncData.get(id);
            worker.cancel(true);
        }

        onResume();
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
            if (isCancelled() || imageView.get() == null) return null;

            synchronized (pauseWorkLock) {
                while (isPausedWork && !isCancelled()) {
                    try {
                        pauseWorkLock.wait();
                    } catch (InterruptedException e) {
                        Log.e(App.TAG, "Interrupted exception", e);
                    }
                }
            }

            final String key = String.valueOf(imageBundle.getUrl().hashCode());
            Bitmap bitmap = cache.getFromMemoryCache(key);
            if (bitmap == null) {
                try {
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    options.inJustDecodeBounds = true;

                    imageView.get().measure(
                            View.MeasureSpec.UNSPECIFIED,
                            View.MeasureSpec.UNSPECIFIED);

                    final int reqWidth = imageView.get().getMeasuredWidth();
                    final int reqHeight = imageView.get().getMeasuredHeight();

                    bitmap = cache.get(key, options, reqWidth, reqHeight);
                    if (bitmap == null) {
                        cache.add(key, bitmapToStream(makeWebRequest(imageBundle)));
                        bitmap = cache.get(key, options, reqWidth, reqHeight);
                    } else {
                        Log.d(App.TAG, "Hit the cache");
                    }

                    cache.addInMemoryCache(key, bitmap);
                } catch (IOException e) {
                    Log.e(App.TAG, "Failed to obtain bitmap", e);
                }
            } else {
                Log.d(App.TAG, "Hit in memory cache");
            }

            cache.getFromMemoryCache(String.valueOf(imageBundle.getUrl().hashCode()));
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if (isCancelled() || imageView.get() == null) return;

            imageView.get().setImageDrawable(new BitmapDrawable(imageView.get().getResources(), result));
            imageView.get().setScaleType(ImageView.ScaleType.CENTER_CROP);
            asyncData.remove(imageView.get().hashCode());
        }

        @Override
        protected void onCancelled(Bitmap bitmap) {
            super.onCancelled(bitmap);
            synchronized (pauseWorkLock) {
                pauseWorkLock.notifyAll();
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

        private InputStream bitmapToStream(Bitmap bitmap) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();
            ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
            return bs;
        }
    }
}
