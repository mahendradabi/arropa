package com.arropa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.arropa.utils.Utility;

public class ActivityProductDetails extends MyAbstractActivity {

    TextView tvShare;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        setTitle("Details");
        showBackButton();

    /*    tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.openShareIntent(ActivityProductDetails.this, "Test message to share ");
            }
        });*/
    }

    @Override
    public void initListeners() {

    }
}
