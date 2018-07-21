package com.arropa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.arropa.adapters.CartAdapter;
import com.arropa.adapters.NotificationAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityNotification extends MyAbstractActivity {
    RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        setTitle("Notifications");
        showBackButton();

        ButterKnife.bind(this);



        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityNotification.this));
        recyclerView.setAdapter(new NotificationAdapter(ActivityNotification.this));
    }

    @Override
    public void initListeners() {

    }
}
