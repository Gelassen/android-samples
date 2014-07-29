package com.home.socialnetworksintegration;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;
import com.dropbox.client2.session.TokenPair;

public class DropboxActivity extends Activity {

    private static final String DROPBOX_API_KEY = "srdg16cg6adhgjy";
    
    private static final String DROPBOX_API_SECRET = "j9n1e96avlftihi";

    private static final String DROPBOX_NAME = "dropbox_prefs";
    
    private static final String FILE_DIR = "/MyFiles/";
    
    private DropboxAPI<AndroidAuthSession> dropbox;
    
    private boolean isLoggedIn;
    private Button logIn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dropbox_layout);
    
        logIn = (Button) findViewById(R.id.login);
        logIn.setOnClickListener(listener);
        logIn(false);
    
        AndroidAuthSession session;
        AppKeyPair pair = new AppKeyPair(DROPBOX_API_KEY, DROPBOX_API_SECRET);
        
        SharedPreferences prefs = getSharedPreferences(DROPBOX_NAME, 0);
        String key = prefs.getString(DROPBOX_API_KEY, null);
        String secret = prefs.getString(DROPBOX_API_SECRET, null);
        
        if (key != null && secret != null) {
            AccessTokenPair token = new AccessTokenPair(key, secret);
            session = new AndroidAuthSession(pair, AccessType.APP_FOLDER, token);
        } else {
            session = new AndroidAuthSession(pair, AccessType.APP_FOLDER);
        }
        
        dropbox = new DropboxAPI<AndroidAuthSession>(session);
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        AndroidAuthSession session = dropbox.getSession();
        if (session.authenticationSuccessful()) {
            try {
                session.finishAuthentication();
                
                TokenPair tokens = session.getAccessTokenPair();
                SharedPreferences prefs = getSharedPreferences(DROPBOX_NAME, 0);
                Editor editor = prefs.edit();
                editor.putString(DROPBOX_API_KEY, tokens.key);
                editor.putString(DROPBOX_API_SECRET, tokens.secret);
                editor.commit();
                
                logIn(true);
            } catch(IllegalStateException e) {
                Log.d("TAG", "fail to auth in dropbox");
            }
        }
    }
    
    private void logIn(boolean value) {
        isLoggedIn = value;
        logIn.setText(isLoggedIn ? "Log in" : "Log out");
    }
    
    
    public void onUploadClicked(View v) {
        UploadFileToDropbox upload = new UploadFileToDropbox(this, dropbox, FILE_DIR);
        upload.execute();
    }
    
    public void onListFilesClicked(View v) {
        ListFiles list = new ListFiles(dropbox, FILE_DIR);
        list.execute();
    }
    
    View.OnClickListener listener = new OnClickListener() {
        
        @Override
        public void onClick(View v) {
            if (isLoggedIn) {
                dropbox.getSession().unlink();
                logIn(false);
            } else {
                dropbox.getSession().startAuthentication(getApplicationContext());
            }
        }
    };
    
}
