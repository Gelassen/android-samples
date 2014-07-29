package com.home.socialnetworksintegration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;

public class UploadFileToDropbox extends AsyncTask<Void, Void, Boolean> {

    private DropboxAPI<?> dropbox;
    private String path;
    private Context context;
    private static int fileNo = 0;
    
    
    public UploadFileToDropbox(Context context, DropboxAPI<?> dropbox, String path) {
        this.context = context;
        this.dropbox = dropbox;
        this.path = path;
    }
    
    @Override
    protected Boolean doInBackground(Void... params) {
        File tempDir = context.getCacheDir();
        File tempFile;
        FileWriter fr;
        try {
            fileNo++;
            tempFile = File.createTempFile("file", ".txt", tempDir);
            fr = new FileWriter(tempFile);
            fr.write("Simple content " + fileNo);
            fr.close();
            
            FileInputStream is = new FileInputStream(tempFile);
            dropbox.putFile(path + "file" + fileNo + ".txt", is, tempFile.length(), null, null);
            tempFile.delete();
            return true;
        } catch(IOException e) {
            Log.e("TAG", "failed to read file", e);
        } catch (DropboxException e) {
            Log.e("TAG", "failed to upload file", e);
        }
        return false;
    }
    
    @Override
    protected void onPostExecute(Boolean result) {
        if(result) {
            Toast.makeText(context, "File successfully uploaded", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "File not uploaded", Toast.LENGTH_LONG).show();
        }
    }

}
