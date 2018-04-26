package com.home.vkphotos;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.widget.ImageView;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okio.BufferedSink;
import okio.Okio;

public class ImageFetcher {

    // being region -- debug field
    private Map<String, Boolean> orders = new ArrayMap<>();

    private OkHttpClient client;

    private ExecutorService executor;
    private HashMap<Integer, Future<?>> data;

    private DiskLruCache diskLruCache;

    public Map<String, Boolean> getOrders() {
        return orders;
    }

    public ImageFetcher() {
        ThreadFactory factory = new ThreadFactory() {

            @Override
            public Thread newThread(final @NonNull Runnable r) {
                return new Thread(r) {
                    @Override
                    public void interrupt() {
                        super.interrupt();
                        if (r instanceof CancelableRunnable) {
                            ((CancelableRunnable) r).cancel();
                        }
                    }
                };
            }
        };
        executor = Executors.newFixedThreadPool(20, factory);
        client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
        data = new HashMap<>();
    }

    public void cancel(Future future) {
        future.cancel(true);
    }

    public void process(final ImageView imageView, final ImageBundle imageBundle) {
        // TODO cancel here otherwise it 'future' will still exist and break
        Future<?> f = data.get(imageView.hashCode());
        if (f != null) {
            f.cancel(true);
        }
        Future<?> future = executor.submit(new Worker(imageView, imageBundle));
        data.put(imageView.hashCode(), future);
    }

    private class Worker extends CancelableRunnable {

        private WeakReference<ImageView> imageViewWeakReference;
        private ImageBundle imageBundle;
        private Handler handler;

        private Bitmap bitmap;
        private InputStream is;

        public Worker(ImageView imageView, ImageBundle imageBundle) {
            this.imageBundle = imageBundle;
            this.imageViewWeakReference = new WeakReference<>(imageView);
            this.handler = new Handler(Looper.getMainLooper());
        }

        @Override
        public void run() {
            super.run();
            Log.d(App.TAG, "Process image: " + imageViewWeakReference.get().hashCode());
            InputStream is = null;
            try {
                // TODO process canceling [DONE] [CHECK IT]
                // TODO process queue [CHECK IT]
                // TODO process caching [DONE] [CHECK IT]

                // TODO assign ImageView in the UI thread [DONE]
                // TODO pass bundle properties [DONE]

                imageViewWeakReference.get().setBackground(null);

                Log.d(App.TAG, "[1] Obtain the image key");
                final String key = String.valueOf(imageViewWeakReference.get().hashCode());
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.outHeight = 96;//imageViewWeakReference.get().getHeight();
                options.outWidth = 96;//imageViewWeakReference.get().getWidth();

                Log.d(App.TAG, "[2] Check image in queue");
                if (data.containsKey(key)) {
                    Log.d(App.TAG, "[2.1] Cancel task if necessary");
                    Future<?> future = data.get(key);
                    future.cancel(true);
                }

                Log.d(App.TAG, "[3] Check image in cache");
                if (isInCache(key)) {
                    Log.d(App.TAG, "[3.1] Obtain image from cache");
                    Bitmap bitmap = readFromCache(key, options);
                    bindImage(bitmap);
                } else {

                    Log.d(App.TAG, "[3.2] Make a web request and bind the image");

                    Request request = new Request.Builder()
                            .url(imageBundle.getUrl())
                            .build();

//                    Response response = client.newCall(request).execute();
//                    is = response.body().byteStream();
//                    writeToCache(key, is);
//                    bindImage(readFromCache(key, options));

                    Bitmap bitmap = makeAWebRequest(imageBundle);//BitmapFactory.decodeStream(is);
                    if (lock.get()) {
                        bindImage(bitmap);
                    } else {
                        Log.d("CANCELLED", "Img is cancelled");
                    }
                }
            } catch (IOException e) {
                Log.e(App.TAG, "Failed to process the data", e);
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        Log.e(App.TAG, "Failed to close the stream" ,e);
                    }
                }
            }
        }

        @Override
        public void cancel() {
            super.cancel();
            // TODO dispose resources
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.e(App.TAG, "Failed to process stream", e);
                }
            }

            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }

            handler = null;
        }

        private Future<?> getFuture(final String key) {
            Future<?> result = null;
            if (data.containsKey(key)) {
                result = data.get(key);
            }
            return result;
        }

        private Bitmap makeAWebRequest(ImageBundle imageBundle) throws IOException {
            final URL url = new URL(imageBundle.getUrl());
            InputStream is = url.openConnection().getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            if (is != null) {
                is.close();
            }
            return bitmap;
        }

        private void bindImage(final Bitmap bitmap) {
            if (imageViewWeakReference.get() == null) return;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.d(App.TAG, "[4] Bind the image");
                    if (imageViewWeakReference.get() == null) return;

                    final BitmapDrawable bitmapDrawable = new BitmapDrawable(imageViewWeakReference.get().getResources(), bitmap);
                    imageViewWeakReference.get()
                            .setBackground(bitmapDrawable);
                }
            });
        }

        private boolean isInCache(final String key) {
            boolean result = false;
            try {
                result = diskLruCache.get(key) != null;
            } catch (IOException e) {
                Log.e(App.TAG, "Failed to obtain the data", e);
            }
            return result;
        }

        private void writeToCache(String key, InputStream is) throws IOException {
            final int bufferSize = 1024 * 1024;
            DiskLruCache.Editor editor = diskLruCache.edit(key);
            OutputStream stream = new BufferedOutputStream(editor.newOutputStream(0), bufferSize);

            BufferedSink bufferedSink = Okio.buffer(Okio.sink(stream));
            bufferedSink.writeAll(Okio.source(is));
            editor.commit();
        }

        private Bitmap readFromCache(String key, BitmapFactory.Options opt) throws IOException {
            final int bufferSize = 1024 * 1024;
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
            InputStream in = snapshot.getInputStream(0);
            BufferedInputStream buffIn = new BufferedInputStream(in, bufferSize);
            return opt == null ? BitmapFactory.decodeStream(buffIn) : BitmapFactory.decodeStream(buffIn, null, opt);
        }

    }


}
