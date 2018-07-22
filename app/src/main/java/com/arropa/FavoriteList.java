package com.arropa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.arropa.adapters.FavoriteAdapter;
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

    @BindView(R.id.progressBar)
    ContentLoadingProgressBar progressBar;

    @BindView(R.id.ll_empty)
    LinearLayout llempty;

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
        progressBar.setVisibility(View.VISIBLE);
new Requestor(Constant.GET_PRODUCT_LIST,FavoriteList.this)
        .getFavoriteList(PreferenceManger.getPreferenceManger().getString(PrefKeys.USERID));
    }

    @Override
    public void success(Object o, int code) {
        progressBar.setVisibility(View.GONE);
        switch (code)
        {
            case Constant.GET_PRODUCT_LIST:
            ProductList list=(ProductList)o;
            if (list!=null&&list.getList()!=null)
            {
                llempty.setVisibility(View.GONE);
                recyclerView.setAdapter(new FavoriteAdapter(FavoriteList.this,list.getList(),FavoriteList.this));
            }
            else {
                llempty.setVisibility(View.VISIBLE);
            }

            break;
        }
    }

    @Override
    public void error(Object o, int code) {
progressBar.setVisibility(View.GONE);
    }

    @Override
    public void itemRemoved() {
       llempty.setVisibility(View.VISIBLE);
    }

}
