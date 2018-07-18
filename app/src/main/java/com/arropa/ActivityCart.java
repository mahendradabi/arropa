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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityCart extends MyAbstractActivity {
    RecyclerView recyclerView;
    @BindView(R.id.checkout)
    AppCompatButton checkout;

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

        ButterKnife.bind(this);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityCart.this, AddAddress.class));
            }
        });

        recyclerView = findViewById(R.id.cartList);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityCart.this));
        recyclerView.setAdapter(new CartAdapter(ActivityCart.this));
    }

    @Override
    public void initListeners() {

    }
}
