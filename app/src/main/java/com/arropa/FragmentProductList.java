package com.arropa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arropa.adapters.ProductListAdapter;
import com.arropa.customviews.AutofitRecyclerView;
import com.arropa.models.ProductList;
import com.arropa.servers.Constant;
import com.arropa.servers.Requestor;
import com.arropa.servers.ServerResponse;

public class FragmentProductList extends MyAbstractFragment implements ServerResponse {
    AutofitRecyclerView recyclerView;
    AppCompatTextView msg;

    public static Fragment intantiateList(String endPoint) {
        Bundle bundle = new Bundle();
        bundle.putString("url", endPoint);
        Fragment fragment = new FragmentProductList();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        initViews(view);


        return view;
    }

    @Override
    public void initViews(View view) {
        msg=view.findViewById(R.id.empty_list);
        Bundle arguments = getArguments();
        if (arguments != null) {
            String url = Constant.HOST_URL + Constant.PATH + arguments.getString("url");
            new Requestor(Constant.GET_PRODUCT_LIST, FragmentProductList.this)
                    .getProductList(url);
        }
        recyclerView = view.findViewById(R.id.recyclerView);

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void success(Object o, int code) {
        if (code == Constant.GET_PRODUCT_LIST) {
            ProductList list=(ProductList)o;
            if (list!=null&&list.getList()!=null)
            {
                msg.setVisibility(View.GONE);
                recyclerView.setAdapter(new ProductListAdapter(getActivity(),list.getList()));
            }
            else msg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void error(Object o, int code) {

    }
}
