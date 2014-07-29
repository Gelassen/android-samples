package com.home.socialnetworksintegration;

import com.github.gelassen.clientservercore.Info;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TwitterActivity extends Activity {
    
    private Twitter twitterConnection = null;
    private RequestToken requestToken = null;
    private AccessToken accessToken = null;
    
    private static final String TOKEN = "TOKEN"; 
    private static final String TOKEN_SECRET = "TOKEN_SECRET"; 
    
    private static final String CONSUMER_KEY = "8HBHwKJoKt2BexoFak9QpQ";
    private static final String CONSUMER_SECRET = "hYO7P3Hle7WmSFxKdWdSgPCrKb86G5sN9bh8aD9cV8";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitter_layout);

        twitterConnection = new TwitterFactory().getInstance();
        twitterConnection.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        
        autorize();
    }
    
    private void autorize() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        if (pref.contains(TOKEN)){
            String token = pref.getString(TOKEN, TOKEN);
            String secret = pref.getString(TOKEN_SECRET, TOKEN_SECRET);

            AccessToken at = new AccessToken(token, secret);
            twitterConnection.setOAuthAccessToken(at);
        }else{
            try {
                WebView webview = new WebView(this);
                webview.getSettings().setJavaScriptEnabled(true);
                webview.setVisibility(View.VISIBLE);
                setContentView(webview);

                initTwitter(webview);
            } catch (Exception e) {
                Log.e(Info.TAG, "Failed to wuth into twitter account", e);
            }
        }
    }
    

    private void initTwitter(WebView webview2) throws Exception {
        twitterConnection = new TwitterFactory().getInstance();
        twitterConnection.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        requestToken = twitterConnection.getOAuthRequestToken("");
        webview2.setWebViewClient(new HideWebViewClient(requestToken
                .getAuthorizationURL()));
        webview2.loadUrl(requestToken.getAuthorizationURL());
    }

    private class HideWebViewClient extends WebViewClient {

        private static final String OAUTH_CALLBACK_URL = "http://www.cnn.com";
        private String mUrl;

        public HideWebViewClient(String url) {
            mUrl = url;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // super.onPageStarted(view, url, favicon);
            if (url.startsWith(OAUTH_CALLBACK_URL)) {
                if (url.indexOf("oauth_token=") != -1) {
                    Uri uri = Uri.parse(url);
                    String verifier = uri.getQueryParameter("oauth_verifier");
                    try {
                        accessToken = twitterConnection.getOAuthAccessToken(
                                requestToken, verifier);
                        saveInPreferences(accessToken);
                        twitterConnection.setOAuthAccessToken(accessToken);
//                      twitterConnection.updateStatus("Tweeting");
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }
                    Log.i("MINE", accessToken.getToken());
                    Log.i("MINE", accessToken.getTokenSecret());
                    view.setVisibility(View.INVISIBLE);
                } else if (url.indexOf("error=") != -1) {
                    view.setVisibility(View.INVISIBLE);
                }
            }
        }

        private void saveInPreferences(final AccessToken accessToken) {
            SharedPreferences pref = PreferenceManager
                    .getDefaultSharedPreferences(getApplicationContext());
            Editor editor = pref.edit();
            editor.putString(TOKEN, accessToken.getToken());
            editor.putString(TOKEN_SECRET, accessToken.getTokenSecret());
            editor.commit();
        }

    }
    

}
