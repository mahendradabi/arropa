package com.arropa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.arropa.adapters.NotificationAdapter;
import com.arropa.models.MyNotification;
import com.arropa.models.NotificationList;
import com.arropa.servers.Constant;
import com.arropa.servers.Requestor;
import com.arropa.servers.ServerResponse;

import java.util.List;

import butterknife.ButterKnife;

public class ActivityNotification extends MyAbstractActivity implements ServerResponse {
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

        new Requestor(Constant.NOTIFICATIONLIST, this)
                .getNotificationList();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityNotification.this));
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void success(Object o, int code) {
        switch (code) {
            case Constant.NOTIFICATIONLIST:
                NotificationList list = (NotificationList) o;
                if (list != null) {
                    List<MyNotification> details = list.getDetails();
                    if (details != null)
                        recyclerView.setAdapter(new NotificationAdapter(ActivityNotification.this, details));
                }
                break;
        }
    }

    @Override
    public void error(Object o, int code) {

    }
}
