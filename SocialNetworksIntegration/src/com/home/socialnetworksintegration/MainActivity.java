package com.home.socialnetworksintegration;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;

public class MainActivity extends Activity {

    private TextView userName;
    
    private LoginButton facebookLoginButton;
    private UiLifecycleHelper helper;
    private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
    
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            if(state.isOpened()) {
                Log.d("FacebookSampleActivity", "Facebook session opened");
            } else if(state.isClosed()) {
                Log.d("FacebookSampleActivity", "Facebook session closed");
            }
        }
    };
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
//       helper = new UiLifecycleHelper(this, callback);
//       helper.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
//       
       
       Intent intent = new Intent(this, TwitterActivity.class);
       startActivity(intent);
//       userName = (TextView) findViewById(R.id.user_name);
//       facebookLoginButton = (LoginButton) findViewById(R.id.fb_login_button);
//       facebookLoginButton.setUserInfoChangedCallback(new UserInfoChangedCallback() {
//            @Override
//            public void onUserInfoFetched(GraphUser user) {
//                if(user!=null) {
//                    userName.setText("Logged as: " + user.getName());
//                }
//                else {
//                    userName.setText("User not logged");
//                }
//            }
//       });    
    }
        
    public void requestPermissions() {
        Session s = Session.getActiveSession();
        if (s!=null)
            s.requestNewPublishPermissions(new Session.NewPermissionsRequest(this, PERMISSIONS));
    }
    
    @Override
    public void onResume() {
        super.onResume();
//        helper.onResume();
    }
    
    @Override
    public void onPause() {
        super.onPause();
//        helper.onPause();
    }   
}

