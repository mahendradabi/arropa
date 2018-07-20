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
import com.arropa.adapters.FavoriteAdapter;
import com.arropa.adapters.ProductListAdapter;
import com.arropa.customviews.AutofitRecyclerView;
import com.arropa.models.ProductList;
import com.arropa.models.ProductModel;
import com.arropa.servers.Constant;
import com.arropa.servers.Requestor;
import com.arropa.servers.ServerResponse;
import com.arropa.sharedpreference.PrefKeys;
import com.arropa.sharedpreference.PreferenceManger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteList extends MyAbstractActivity implements ServerResponse,FavoriteAdapter.ItemRemoved{
    AutofitRecyclerView recyclerView;
    List<ProductModel> productModelList=new ArrayList<>();
    FavoriteAdapter adapter;

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

        recyclerView = findViewById(R.id.recyclerView);



    }

    @Override
    public void initListeners() {
new Requestor(Constant.GET_PRODUCT_LIST,FavoriteList.this)
        .getFavoriteList(PreferenceManger.getPreferenceManger().getString(PrefKeys.USERID));
    }

    @Override
    public void success(Object o, int code) {
        switch (code)
        {
            case Constant.GET_PRODUCT_LIST:
            ProductList list=(ProductList)o;
            if (list!=null&&list.getList()!=null)
            {
                recyclerView.setAdapter(new FavoriteAdapter(FavoriteList.this,list.getList(),FavoriteList.this));
            }

            break;
        }
    }

    @Override
    public void error(Object o, int code) {

    }

    @Override
    public void itemRemoved() {
        new Requestor(Constant.GET_PRODUCT_LIST,FavoriteList.this)
                .getFavoriteList(PreferenceManger.getPreferenceManger().getString(PrefKeys.USERID));
    }
}
