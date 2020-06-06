package com.thecrimsonpizza.tvtracker.ui.otros;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.thecrimsonpizza.tvtracker.R;

import static com.thecrimsonpizza.tvtracker.util.Constants.URL_WEBVIEW;

public class WebViewActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        String url = intent.getStringExtra(URL_WEBVIEW);

        WebView mWebView = findViewById(R.id.web_view);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setUserAgentString("Android");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.loadUrl(url);
    }
}
