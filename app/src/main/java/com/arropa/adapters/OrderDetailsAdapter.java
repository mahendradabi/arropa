package com.arropa.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arropa.R;
import com.arropa.models.OrderModel;
import com.arropa.servers.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.MyViewHolder> {

    Context mContext;
    List<OrderModel> list;

    public OrderDetailsAdapter(Context mContext, List<OrderModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_order_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        OrderModel orderModel = list.get(position);
        if (orderModel != null) {
            holder.total.setText(Constant.CURRENCY + " " + orderModel.getAmount());
            holder.orderid.setText("#" + orderModel.getOrderNo());
            holder.p_qty.setText("Qty: "+orderModel.getAlotNo());
            holder.p_title.setText(orderModel.getProductName());
        }


        try {
            String img = orderModel.getImages();
            if (img != null) {
                String[] split = img.split(",");
                if (split != null && split.length > 0) {
                    Picasso.get().load(Constant.IMAGEPATH + split[0])
                            .error(R.drawable.shirt)
                            .placeholder(R.drawable.shirt)
                            .into(holder.image);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView orderid, total,p_qty,p_title;
        AppCompatImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            orderid = itemView.findViewById(R.id.orderid);
            total = itemView.findViewById(R.id.total_amount);
            p_qty = itemView.findViewById(R.id.p_qty);
            p_title = itemView.findViewById(R.id.p_title);
            image=itemView.findViewById(R.id.image);


        }
    }
}
