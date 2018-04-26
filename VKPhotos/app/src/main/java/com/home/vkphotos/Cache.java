package com.home.vkphotos;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import okio.BufferedSink;
import okio.Okio;

public class Cache {

    private static final int DISK_CACHE_INDEX = 0;

    private DiskLruCache diskLruCache;

    private Object diskCacheLock = new Object();

    public Cache(File cacheDir) {
        try {
            diskLruCache = DiskLruCache.open(cacheDir, 1, 1,50*1024*1024);
        } catch (IOException e) {
            Log.e(App.TAG, "Failed to obtain data", e);
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

    public void writeToCachedSnapshoot(String key, InputStream is) {
        final int bufferSize = 1024 * 1024;
        OutputStream out = null;
        synchronized (diskCacheLock) {
            try {
                DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
                if (true) {
                    DiskLruCache.Editor editor = diskLruCache.edit(key);
                    if (editor != null) {
                        OutputStream stream = new BufferedOutputStream(editor.newOutputStream(0), bufferSize);
                        InputStream bis = new BufferedInputStream(is);
//                        BufferedSink bufferedSink = Okio.buffer(Okio.sink(stream));
//                        bufferedSink.writeAll(Okio.source(is));
//                        editor.commit();

                        int b;
                        while ((b = bis.read()) != -1) {
                            stream.write(b);
                        }

                        stream.close();
                    }

                    diskLruCache.flush();
                } else {
                    snapshot.getInputStream(DISK_CACHE_INDEX).close();
                }

            } catch (IOException e) {
                Log.e(App.TAG, "Failed to write to cache", e);
            }
        }
    }

    public Bitmap readFromCachedSnapshot(String key, BitmapFactory.Options opt) throws IOException {
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

    public InputStream bitmapToStream(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        byte[] bitmapdata = bos.toByteArray();
        ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
        return bs;
    }

    public String readFromCachedSnapshot(String key) {
        StringBuffer result = new StringBuffer();
        final byte[] buffer = new byte[1024];
        synchronized (diskCacheLock) {
            FileInputStream inputStream = null;
            try {
                final DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
                if (snapshot != null) {
                    inputStream = (FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX);

                    FileDescriptor fileDescriptor = inputStream.getFD();
                    FileInputStream fis = new FileInputStream(fileDescriptor);
                    while (fis.read(buffer) != -1) {
                        result.append(new String(buffer));
                    }

//                    if (inputStream != null) {
//                        BufferedInputStream is = new BufferedInputStream(inputStream);
//                        while (is.read(buffer) != -1) {
//                            result.append(new String(buffer));
//                        }
//                    }
                }
            } catch (Exception ex) {
                Log.e(App.TAG, "Failed to obtain cached data", ex);
            }
        }

        return result.toString();
    }
}
