package com.tracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebViewClient;

import com.tracker.R;

import static com.tracker.util.Constants.URL_WEBVIEW;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();

        String url = intent.getStringExtra(URL_WEBVIEW);

        android.webkit.WebView mWebView = findViewById(R.id.webviewtransporte);

        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setUserAgentString("Android");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);

        mWebView.loadUrl(url);

    }
}
