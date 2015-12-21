package com.example.duthientan.searchimagetool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.duthientan.searchimagetool.Backend.ApiSocial;
import com.example.duthientan.searchimagetool.Backend.OAuthSocial;
import com.example.duthientan.searchimagetool.Utils.L;


public class WebViewActivity extends Activity {

    OAuthSocial mSocial;
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);

        if (getIntent().getStringExtra("Social").equals("instargram")) {
            String client_id="167d7ebc732548bb9b9b0c33c727d572";
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    if (url.contains("https://www.google.com/#access_token=")) {
                        int start = url.indexOf("#access_token=") + "#access_token=".length();
                        Intent intent = new Intent(WebViewActivity.this, SearchActivity.class);
                        intent.putExtra("access_token", url.substring(start));
                        intent.putExtra("Social", getIntent().getStringExtra("Social"));
                        startActivity(intent);
                        finish();
                    }
                    super.onPageFinished(view, url);
                }
            });
            mWebView.loadUrl("https://api.instagram.com/oauth/authorize/?client_id="+client_id+"&redirect_uri=https://www.google.com&response_type=token");

        } else {
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    if (url.contains("https://www.google.com/?oauth_token=")) {
                        String key = "&oauth_verifier=";
                        int start = url.indexOf(key) + key.length();
                        Intent intent = new Intent(WebViewActivity.this, SearchActivity.class);
                        intent.putExtra("verifier", url.substring(start));
                        intent.putExtra("Social", getIntent().getStringExtra("Social"));
                        startActivity(intent);
                        finish();
                    }
                    super.onPageFinished(view, url);
                }
            });
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    OAuthSocial.startOAuthSocial(getIntent().getStringExtra("Social"));
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mWebView.loadUrl(OAuthSocial.getURL(getIntent().getStringExtra("Social")).toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
