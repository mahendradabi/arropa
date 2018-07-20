package com.arropa.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.arropa.R;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder>{

    Context mContext;

    public OrderAdapter(Context mContext) {
        this.mContext=mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        holder.order_details_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView pname, pprice, deliverydate, orderdate, orderid;
        AppCompatImageView image;

        LinearLayout order_details_ll;

        public MyViewHolder(View itemView) {
            super(itemView);
            pname = itemView.findViewById(R.id.product_name);
            deliverydate = itemView.findViewById(R.id.delivery_date);
            pprice = itemView.findViewById(R.id.product_price);
            orderdate = itemView.findViewById(R.id.orderdate);
            orderid = itemView.findViewById(R.id.orderid);
            image = itemView.findViewById(R.id.image);
            order_details_ll = itemView.findViewById(R.id.order_details_ll);
        }
    }
}
