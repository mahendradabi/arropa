package com.arropa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityContactUs extends MyAbstractActivity {
@BindView(R.id.toolbar)
Toolbar toolbar;
@BindView(R.id.submit)
    Button submit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        initViews();
        initListeners();

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        setToolbar(toolbar);
        showBackButton();
        setTitle("Contact Us");
    }

    @Override
    public void initListeners() {

    }
}
