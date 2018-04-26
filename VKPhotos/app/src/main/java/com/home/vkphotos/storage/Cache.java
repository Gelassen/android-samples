package com.home.vkphotos.storage;


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

public class Cache {

    public void add(final String key, InputStream is) {
        if (!isExternalStorageWritable()) return;

        // TODO check if the size is equal
        // TODO check if disk is mounted
        // TODO check if you can create file
        // TODO check if the key exists
        FileLock fileLock = null;
        File dir = getDirectory();
        File file = new File(dir, generateHash(key));
        if (file.exists()) {
            file.delete();
        }

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

    public Bitmap get(final String key) {
        Bitmap bitmap = null;
//        File file = new File(getDirectory(), generateHash(key));
        FileLock fileLock = null;
        try {
            RandomAccessFile file = new RandomAccessFile(new File(getDirectory(), generateHash(key)), "rw");
//            FileInputStream fos = new FileInputStream(file);
            FileChannel fileChannel = file.getChannel();
            while((fileLock = fileChannel.tryLock()) == null) {
                // no op, trying to obtain the lock
            }

            bitmap = BitmapFactory.decodeStream(Channels.newInputStream(fileChannel));
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

    private String generateHash(final String key) {
        return key;
    }

    private File getDirectory() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
