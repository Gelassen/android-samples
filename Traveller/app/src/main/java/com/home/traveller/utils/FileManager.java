package com.home.traveller.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.home.traveller.App;
import com.home.traveller.model.Card;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by dmitry.kazakov on 10/25/2015.
 */
public class FileManager {

    private static final String EXTRA_PREFIX = App.TAG + "_";

    private ContentResolver cr;

    public FileManager(Context context) {
        cr = context.getContentResolver();
    }

    public static String extractNameFromPath(final String path) {
        String[] tokens = path.split("/");
        String[] partsOfName = tokens[tokens.length - 1].split("\\.");
        return partsOfName[0];
    }

    public static String extractPrefixFromPath(final String path) {
        String[] tokens = path.split("\\.");
        return ".".concat(tokens[tokens.length-1]);
    }

    public File copyFile(String uri, File directory) {
        File tempFile = null;
        InputStream in = null;
        FileOutputStream out = null;
        try {
            final String str = "file://" + uri;
            Uri.Builder builder = Uri.parse(str).buildUpon();
            builder.scheme("file");
            Log.d(App.TAG, "Path to file: " + builder.toString());
            in = cr.openInputStream(builder.build());
            tempFile = File.createTempFile(
                    EXTRA_PREFIX.concat(FileManager.extractNameFromPath(uri)),
                    FileManager.extractPrefixFromPath(uri),
                    directory
            );
            out = new FileOutputStream(tempFile, false);
            byte[] buffer = new byte[4 * 1024];
            while (in.read(buffer) != -1) {
                out.write(buffer);
            }
        } catch (IOException e) {
            Log.d(App.TAG, "Failed to copy file", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e(App.TAG, "Failed to close stream", e);
                }
            }
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    Log.e(App.TAG, "Failed to close stream", e);
                }
            }
        }
        return tempFile;
    }

    public File addHiddenFolderToPath(File file) {
        StringBuilder directory = new StringBuilder();
        directory.append(file.getAbsolutePath());
        directory.append(File.separator);
        directory.append(".nomedia");
        File newFile = new File(directory.toString());
        newFile.mkdirs();
        return newFile;
    }

    // calculate total memory required for copy
    public boolean isEnoughMemory(final Card card) {
        // TODO complete me
        return true;
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

}