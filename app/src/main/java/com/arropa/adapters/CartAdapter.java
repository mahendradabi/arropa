package com.arropa.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.arropa.ActivityProductDetails;
import com.arropa.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context mContex;

    public CartAdapter(Context mContex) {
        this.mContex = mContex;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_item;

        public MyViewHolder(View itemView) {
            super(itemView);
           // ll_item = itemView.findViewById(R.id.ll_item);
        }
    }
}
