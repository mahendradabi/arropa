package com.arropa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arropa.adapters.ProductListAdapter;
import com.arropa.customviews.AutofitRecyclerView;

public class FragmentProductList extends MyAbstractFragment {
    AutofitRecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void initViews(View view) {
      recyclerView = view.findViewById(R.id.recyclerView);
       recyclerView.setAdapter(new ProductListAdapter(getActivity()));
    }

    @Override
    public void initListeners() {

    }
}
