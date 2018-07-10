package com.arropa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.arropa.adapters.CartAdapter;

public class ActivityCart extends MyAbstractActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_cart_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        setTitle("My Cart");
        showBackButton();

        recyclerView=findViewById(R.id.cartList);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityCart.this));
        recyclerView.setAdapter(new CartAdapter(ActivityCart.this));
    }

    @Override
    public void initListeners() {

    }
}
