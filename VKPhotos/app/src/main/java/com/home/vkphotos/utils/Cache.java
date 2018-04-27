package com.home.vkphotos.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.home.vkphotos.App;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Cache {

    private static final int CACHE_UP_LIMIT = 100 * 1024 * 1024;

    private FileUtil fileUtil;
    private File cacheFile;

    public Cache(Context context) {
        fileUtil = new FileUtil();
        cacheFile = context.getCacheDir();
    }

    public void clean() {
        String[] files = getDirectory().list();
        Arrays.sort(files);
        ArrayList list = (ArrayList) Arrays.asList(files);
        for (int idx = 0; idx < list.size(); idx++) {
            list.remove(idx);
        }
    }

    public void add(final String key, InputStream is) {
        FileLock fileLock = null;
        File dir = getDirectory();
        File file = new File(dir, generateFileName(key));

        byte[] buffer = new byte[1024];
        try {
            FileOutputStream stream = new FileOutputStream(file);
            while ((fileLock = stream.getChannel().tryLock()) == null) {
                // no op, wait for lock
            }

            BufferedInputStream bis = new BufferedInputStream(is);
            while (bis.read(buffer) != -1) {
                stream.write(buffer);
            }

            // TODO check if there is no exceeding limit of the cache
            if (fileUtil.fileSize(getDirectory()) >= CACHE_UP_LIMIT) {
                Log.e(App.CACHE, "Remove cached data");
                removeCachedData();
            }
        } catch (FileNotFoundException e) {
            Log.e(App.TAG, "Can not find file", e);
        } catch (IOException e) {
            Log.e(App.TAG, "Can not read or write into file", e);
        } finally {
            if (fileLock != null) {
                try {
                    fileLock.close();
                } catch (IOException e) {
                    Log.e(App.TAG, "Failed to obtain lock", e);
                }
            }
        }
    }

    public Bitmap get(final String key, BitmapFactory.Options options, final int reqWidth, final int reqHeight) {
        Bitmap bitmap = null;
        FileLock fileLock = null;
        try {
            RandomAccessFile file = new RandomAccessFile(new File(getDirectory(), generateFileName(key)), "rw");
            FileChannel fileChannel = file.getChannel();
            while((fileLock = fileChannel.tryLock()) == null) {
                // no op, trying to obtain the lock
            }

            options = getOptions(key, options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;

            bitmap = BitmapFactory.decodeStream(Channels.newInputStream(fileChannel), null, options);
        } catch (FileNotFoundException e) {
            Log.e(App.TAG, "Failed to obtain file from disk", e);
        } catch (IOException e) {
            Log.e(App.TAG, "Failed to read file from disk");
        } finally {
            try {
                if (fileLock != null) {
                    fileLock.release();
                }
            } catch (IOException e) {
                Log.e(App.TAG, "Failed to release lock", e);
            }
        }
        return bitmap;
    }

    private BitmapFactory.Options getOptions(final String key, BitmapFactory.Options options,
                                             final int reqWidth, final int reqHeight) {
        FileLock fileLock = null;
        try {
            RandomAccessFile file = new RandomAccessFile(new File(getDirectory(), generateFileName(key)), "rw");
            FileChannel fileChannel = file.getChannel();
            while((fileLock = fileChannel.tryLock()) == null) {
                // no op, trying to obtain the lock
            }

            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(Channels.newInputStream(fileChannel), null, options);
            options.inSampleSize = FileUtil.calculateInSampleSize(options, reqWidth, reqHeight);
        } catch (FileNotFoundException e) {
            Log.e(App.TAG, "Failed to obtain file from disk", e);
        } catch (IOException e) {
            Log.e(App.TAG, "Failed to read file from disk");
        } finally {
            try {
                if (fileLock != null) {
                    fileLock.release();
                }
            } catch (IOException e) {
                Log.e(App.TAG, "Failed to release lock", e);
            }
        }
        return options;
    }

    private File getDirectory() {
        return cacheFile;
    }

    private void removeCachedData() {
        final int eightyPercent = (int) (0.8 * CACHE_UP_LIMIT);
        String[] files = getDirectory().list();
        ArrayList list = new ArrayList<>(Arrays.asList(files));
        Collections.sort(list, new CachedFileComparator());
        for (int idx = 0; idx < list.size(); idx++) {
            list.remove(idx);
            if (fileUtil.fileSize(getDirectory()) <= eightyPercent) {
                break;
            }
        }
    }

    private String generateFileName(final String key) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(key.hashCode());
        return buffer.toString();
    }

}