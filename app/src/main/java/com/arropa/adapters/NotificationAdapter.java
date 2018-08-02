package com.arropa.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.arropa.R;
import com.arropa.models.MyNotification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    Context mContex;
    List<MyNotification> list;

    public NotificationAdapter(Context mContex, List<MyNotification> list) {
        this.mContex = mContex;
        this.list=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyNotification myNotification = list.get(position);
        holder.post_title.setText(myNotification.getTitle());
        holder.tv_msg.setText(myNotification.getMessage());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
      AppCompatTextView post_title,tv_msg;

        public MyViewHolder(View itemView) {
            super(itemView);
           // ll_item = itemView.findViewById(R.id.ll_item);
            post_title=itemView.findViewById(R.id.post_title);
            tv_msg=itemView.findViewById(R.id.tv_msg);
        }
    }

}
