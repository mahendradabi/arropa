package com.arropa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.arropa.adapters.OrderAdapter;
import com.arropa.adapters.OrderDetailsAdapter;
import com.arropa.models.MyResponse;
import com.arropa.models.OrderListModel;
import com.arropa.models.OrderModel;
import com.arropa.servers.Constant;
import com.arropa.servers.Requestor;
import com.arropa.servers.ServerResponse;
import com.arropa.sharedpreference.PrefKeys;
import com.arropa.sharedpreference.PreferenceManger;

import java.util.List;

import butterknife.ButterKnife;

public class MyOrderDetails extends MyAbstractActivity implements ServerResponse {
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


    }

    @Override
    public void initListeners() {
        new Requestor(Constant.GET_CART_LIST, MyOrderDetails.this)
                .orderDetails(getIntent().getStringExtra("id"));
    }

    @Override
    public void success(Object o, int code) {
        switch (code) {
            case Constant.GET_CART_LIST:
                OrderListModel model = (OrderListModel) o;
                if (model != null) {
                    List<OrderModel> details = model.getDetails();
                    if (details!=null)
                    {
                        recyclerView.setAdapter(new OrderDetailsAdapter(MyOrderDetails.this,details));
                    }

                }
                break;

        }
    }

    @Override
    public void error(Object o, int code) {

    }

}
