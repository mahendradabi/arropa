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

public class FavoriteList extends MyAbstractActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_favorite_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        setTitle("My Favorite");
        showBackButton();

        ButterKnife.bind(this);

      /*

        recyclerView = findViewById(R.id.cartList);
        recyclerView.setLayoutManager(new LinearLayoutManager(FavoriteList.this));
        recyclerView.setAdapter(new CartAdapter(FavoriteList.this));*/
    }

    @Override
    public void initListeners() {

    }
}
