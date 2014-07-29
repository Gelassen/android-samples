package com.home.socialnetworksintegration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.GooglePlusUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentReference;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.PlusScopes;
import com.google.api.services.plus.model.ActivityFeed;

public class GoogleAuthActivity extends Activity {

    private static final int CHOOSE_ACCOUNT = 1;
    
    private static final int REQUEST_AUTHORIZATION = 2;
    
    private TextView output;
    private GoogleAccountCredential credential;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_layout);
        
        java.util.List<String> scopes = new ArrayList<String>();
        scopes.add(DriveScopes.DRIVE);
        scopes.add(PlusScopes.PLUS_ME);
        credential = GoogleAccountCredential.usingOAuth2(this, scopes);
        startActivityForResult(credential.newChooseAccountIntent(), CHOOSE_ACCOUNT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case CHOOSE_ACCOUNT:
            if (data != null) {
                
                Bundle extras = data.getExtras();
                if (extras != null) {
                    String accountName = extras.getString(AccountManager.KEY_ACCOUNT_NAME);
                    
                    if (accountName != null) {
                        credential.setSelectedAccountName(accountName);
                    }
                
                }
            
            }
            break;
            
        case REQUEST_AUTHORIZATION:
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Click the button again to perform chosen action.", Toast.LENGTH_LONG).show();
            } else {
                startActivityForResult(credential.newChooseAccountIntent(), CHOOSE_ACCOUNT);
            }
            
        default:
            break;
        }
    }
    
    public void onGetList(View v) {
        new FilesFromGoogleDrive().execute();
    }
    
    public void onGetActivities(View v) {
        new GooglePlusActivities().execute();
    }
    
    public void onUploadFile(View v) {
        new UploadFileToGoogleDrive().execute();
    }
    
    public void onPost(View v) {
        postInGooglePlus();
    }
    
    private void postInGooglePlus() {
        final int errorCode = GooglePlusUtil.checkGooglePlusApp(this);
        if (errorCode != GooglePlusUtil.SUCCESS) {
            GooglePlusUtil.getErrorDialog(errorCode, this, 0).show();
        } else {
            Intent shareIntent = ShareCompat.IntentBuilder.from(GoogleAuthActivity.this)
                    .setType("text/plain")
                    .setText("This is text posted from sample application")
                    .getIntent();
            shareIntent.setPackage("com.google.android.apps.plus");
            
            startActivity(shareIntent);
        }
    }
    
    private class FilesFromGoogleDrive extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            Drive drive = new Drive.Builder(
                    AndroidHttp.newCompatibleTransport(), 
                    new GsonFactory(), 
                    credential)
                    .build();
            
            try {
                FileList list = drive.files().list().execute();
                List<File> items = list.getItems();
                for (File f : items) {
                    Log.d("TAG", f.getTitle());
                }
                return true;
            } catch (UserRecoverableAuthIOException e) {
                startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
            } catch (IOException e) {
                Log.e("TAG", "Failed to get list of files", e);
            }

            return false;
        }
        
        
    }
    
    private class UploadFileToGoogleDrive extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Drive drive = new Drive.Builder(
                        AndroidHttp.newCompatibleTransport(), 
                        new GsonFactory(), 
                        credential)
                        .build();
                FileList files = drive.files().list()
                        .setQ("title = 'MyFiles' and trashed = false")
                            .execute();
                String directoryId = "";
                
                if(files.getItems().size()==0)
                {
                    File appDir = new File();
                    appDir.setTitle("MyFiles");
                    appDir.setMimeType("application/vnd.google-apps.folder");
                    appDir.setDescription("Sample app folder");
                    
                    File directory = drive.files().insert(appDir).execute();
                    directoryId = directory.getId();
                } else {
                    directoryId = files.getItems().get(0).getId();
                }
                
                String randomUUID = UUID.randomUUID().toString();
                
                final File sampleFile = new File();
                sampleFile.setTitle("file"+randomUUID);
                sampleFile.setMimeType("text/plain");
                
                List<ParentReference> parents = new ArrayList<ParentReference>();
                ParentReference fileParent = new ParentReference();
                fileParent.setId(directoryId);
                parents.add(fileParent);
                sampleFile.setParents(parents);
                
                File uploadedFile = drive.files().insert(
                        sampleFile, 
                        new ByteArrayContent("text/plain", randomUUID.getBytes()))
                            .execute();
            
                return true;
            } catch (UserRecoverableAuthIOException e) {
                startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
            } catch (IOException e) {
                Log.e("TAG", "Failed to upload file", e);
            }
            
            return false;
        }
        
        
    }
    
    private class GooglePlusActivities extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            Plus plus = new Plus.Builder(
                    AndroidHttp.newCompatibleTransport(), 
                    new GsonFactory(), 
                    credential)
                    .build();
            try {
                ActivityFeed feed = plus.activities().search("android").execute();
                List<com.google.api.services.plus.model.Activity> items = feed.getItems();
                
                final StringBuilder sb = new StringBuilder();
                sb.append("Activities connected to android");
                sb.append("\n\n");
                
                for (com.google.api.services.plus.model.Activity activity : items) {
                    sb.append(activity.getTitle());
                    sb.append("\n");
                    sb.append(activity.getUrl());
                    sb.append("\n");
                } 
                
                Log.d("TAG", sb.toString());
                return true;
            } catch (UserRecoverableAuthIOException e) {
                startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
            } catch (IOException e) {
                Log.e("TAG", "Failed to obtains google activities", e);
            }
            return false;
        }
        
    }
    
}
