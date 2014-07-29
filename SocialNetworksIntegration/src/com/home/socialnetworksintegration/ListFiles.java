package com.home.socialnetworksintegration;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;

public class ListFiles extends AsyncTask<Void, Void, List<String>> {

    private DropboxAPI<?> dropbox;
    private String path;
    
    public ListFiles(DropboxAPI<?> dropbox, String path) {
        this.dropbox = dropbox;
        this.path = path;
    }
    
    
    @Override
    protected List<String> doInBackground(Void... params) {
        List<String> files = new ArrayList<String>();
        try {
            Entry directory = dropbox.metadata(path, 1000, null, true, null);
            for (Entry entry : directory.contents) {
                files.add(entry.fileName());
            }
        } catch (DropboxException e) {
            Log.e("TAG", "Failed to obtain the list of dropbox files", e);
        }
        return files;
    }
    
    @Override
    protected void onPostExecute(List<String> result) {
        for(String fileName : result) {
            Log.i("ListFiles", fileName);
        }
    }

}
