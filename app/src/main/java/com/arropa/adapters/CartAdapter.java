package com.arropa.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.arropa.R;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context mContex;
List<String> test=new ArrayList<>();
    public CartAdapter(Context mContex) {
        this.mContex = mContex;
        for (int i=0;i<10;i++)
            test.add(""+i);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
            holder.ll_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    test.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,test.size());
                }
            });
    }

    @Override
    public int getItemCount() {
        return test.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_remove;

        public MyViewHolder(View itemView) {
            super(itemView);
           ll_remove = itemView.findViewById(R.id.ll_remove);
        }
    }

}
