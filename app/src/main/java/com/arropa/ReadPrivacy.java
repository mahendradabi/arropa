package com.arropa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

/**
 * Created by xyz on 24-04-2018.
 */

public class ReadPrivacy extends MyAbstractActivity {
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_read_policy);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        showBackButton();
        webView = findViewById(R.id.webView);

     webView.loadUrl("http://disharajsociety.com/arropa/term.html");

    }

    @Override
    public void initListeners() {

    }
}
