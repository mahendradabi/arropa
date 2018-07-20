package com.arropa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.arropa.adapters.OrderAdapter;
import com.arropa.servers.Constant;
import com.arropa.servers.Requestor;
import com.arropa.servers.ServerResponse;
import com.arropa.sharedpreference.PrefKeys;
import com.arropa.sharedpreference.PreferenceManger;

import butterknife.ButterKnife;

public class MyOrderList extends MyAbstractActivity implements ServerResponse{
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_order_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        setTitle("My Order");
        showBackButton();

        ButterKnife.bind(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new OrderAdapter(MyOrderList.this));


    }

    @Override
    public void initListeners() {
new Requestor(Constant.GET_PRODUCT_LIST,MyOrderList.this)
        .getFavoriteList(PreferenceManger.getPreferenceManger().getString(PrefKeys.USERID));
    }

    @Override
    public void success(Object o, int code) {
        switch (code)
        {


        }
    }

    @Override
    public void error(Object o, int code) {

    }

}
