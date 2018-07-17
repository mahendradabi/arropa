package com.arropa.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.arropa.ActivityProductDetails;
import com.arropa.R;
import com.arropa.models.ProductModel;
import com.arropa.servers.Constant;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {
    Context mContex;
    List<ProductModel> list;
    public ProductListAdapter(Context mContex, List<ProductModel> list) {
        this.mContex = mContex;
        this.list=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itme_product_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductModel productModel = list.get(position);
        holder.tvPrice.setText(Constant.CURRENCY+" "+productModel.getProductPrice());
        holder.tvName.setText(productModel.getProductName());
        holder.tvDes.setText(productModel.getProductDesc());

        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mContex.startActivity(new Intent(mContex, ActivityProductDetails.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_item;
        AppCompatTextView tvName,tvPrice,tvDes;

        public MyViewHolder(View itemView) {
            super(itemView);
            ll_item = itemView.findViewById(R.id.ll_item);
            tvName=itemView.findViewById(R.id.tvname);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvDes=itemView.findViewById(R.id.tvDes);
        }
    }
}
