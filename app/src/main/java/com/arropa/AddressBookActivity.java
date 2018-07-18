package com.arropa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

/**
 * Created by xyz on 24-04-2018.
 */

public class AddressBookActivity extends MyAbstractActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_address_book);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        showBackButton();


    }

    @Override
    public void initListeners() {

    }
}
