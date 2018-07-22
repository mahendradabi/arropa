package com.arropa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.arropa.adapters.CartAdapter;
import com.arropa.models.CartList;
import com.arropa.models.CartModel;
import com.arropa.servers.Constant;
import com.arropa.servers.Requestor;
import com.arropa.servers.ServerResponse;
import com.arropa.sharedpreference.PrefKeys;
import com.arropa.sharedpreference.PreferenceManger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityCart extends MyAbstractActivity implements ServerResponse,CartAdapter.OnCartEmpty{
    RecyclerView recyclerView;
    @BindView(R.id.checkout)
    AppCompatButton checkout;
    @BindView(R.id.ll_bottom)
    LinearLayout llbottom;
    @BindView(R.id.ll_empty)
    LinearLayout llempty;
    @BindView(R.id.shopNow)
    Button shopnow;
    @BindView(R.id.progressBar)
    ContentLoadingProgressBar progressBar;

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

        shopnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.cartList);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityCart.this));

        progressBar.setVisibility(View.VISIBLE);
        new Requestor(Constant.GET_CART_LIST,this).getCartList(
                PreferenceManger.getPreferenceManger().getString(PrefKeys.USERID)
        );
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void success(Object o, int code) {
        progressBar.setVisibility(View.GONE);
        switch (code)
        {
            case Constant.GET_CART_LIST:
                CartList cartList=(CartList)o;
                if (cartList!=null&&cartList.isStatus())
                {
                    List<CartModel> details = cartList.getDetails();
                    if (details!=null)
                    {
                        recyclerView.setAdapter(new CartAdapter(ActivityCart.this,details,ActivityCart.this));
                            llbottom.setVisibility(View.VISIBLE);
                            llempty.setVisibility(View.GONE);
                    }

                }
                else {
                    llempty.setVisibility(View.VISIBLE);
                    llbottom.setVisibility(View.GONE);
                }
                break;

        }
    }

    @Override
    public void error(Object o, int code) {
        llempty.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onCartEmpty() {
        llbottom.setVisibility(View.GONE);
        llempty.setVisibility(View.VISIBLE);

    }


}
