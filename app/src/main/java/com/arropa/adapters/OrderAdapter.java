package com.arropa.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.arropa.MyOrderDetails;
import com.arropa.R;
import com.arropa.models.OrderModel;
import com.arropa.servers.Constant;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    Context mContext;
    List<OrderModel> list;

    public OrderAdapter(Context mContext, List<OrderModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        OrderModel orderModel = list.get(position);
        if (orderModel != null) {
            holder.total.setText(Constant.CURRENCY + " " + orderModel.getTotalAmount());
            holder.orderid.setText("#" + orderModel.getOrderNo());
        }


        holder.order_details_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, MyOrderDetails.class).putExtra("id",list.get(position).getInvoiceNo()));
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView orderid, total;
    LinearLayout order_details_ll;
        public MyViewHolder(View itemView) {
            super(itemView);
            orderid = itemView.findViewById(R.id.orderid);
            total = itemView.findViewById(R.id.total_amount);
            order_details_ll=itemView.findViewById(R.id.order_details_ll);

        }
    }
}
