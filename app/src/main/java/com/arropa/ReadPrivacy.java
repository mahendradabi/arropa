package com.arropa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.arropa.servers.Constant;

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

        switch (getIntent().getIntExtra("url",0))
        {
            case 1:
                webView.loadUrl("http://disharajsociety.com/arropa/term.html");
                setTitle("Terms & Conditons");
                break;
            case 2:
                webView.loadUrl("http://disharajsociety.com/arropa/faq.html");
                setTitle("FAQ");
                break;
            case 3:
                webView.loadUrl("http://disharajsociety.com/arropa/return.html");
                setTitle("Return");
                break;
        }

    }

    @Override
    public void initListeners() {

    }
}
