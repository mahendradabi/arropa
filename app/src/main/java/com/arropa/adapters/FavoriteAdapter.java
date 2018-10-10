package com.arropa.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arropa.ActivityProductDetails;
import com.arropa.R;
import com.arropa.models.MyResponse;
import com.arropa.models.ProductModel;
import com.arropa.servers.Constant;
import com.arropa.servers.Requestor;
import com.arropa.servers.ServerResponse;
import com.arropa.sharedpreference.PrefKeys;
import com.arropa.sharedpreference.PreferenceManger;
import com.arropa.utils.DialogWindow;
import com.arropa.utils.Utility;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> implements ServerResponse {

    Context mContex;
    List<ProductModel> list;
    ItemRemoved removed;

    ProgressDialog dialog;
    int removedPosition;

    public interface ItemRemoved {
        public void itemRemoved();
    }

    public FavoriteAdapter(Context mContex, List<ProductModel> list, ItemRemoved removed) {
        this.mContex = mContex;
        this.list = list;
        this.removed = removed;
        dialog= DialogWindow.showProgressDialog(mContex,"Favorite","Removing Product from favorite list.");
        dialog.setCancelable(false);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itme_product_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        ProductModel productModel = list.get(position);
        holder.tvPrice.setText(Constant.CURRENCY + " " + productModel.getProductPrice());
        holder.tvName.setText(productModel.getProductName());
        holder.tvDes.setText(productModel.getProductDesc());

        try {
            String img = productModel.getImages();
            if (img != null) {
                String[] split = img.split(",");
                if (split != null && split.length > 0) {
                    Picasso.get().load(Constant.IMAGEPATH + split[0])
                            .error(R.drawable.shirt)
                            .placeholder(R.drawable.shirt)
                            .into(holder.imageView);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mContex.startActivity(new Intent(mContex, ActivityProductDetails.class)
                        .putExtra("product", list.get(position))
                );
            }
        });
        holder.favorite.setVisibility(View.VISIBLE);
        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size()>0)
                {
                    dialog.show();
                    removedPosition=position;
                    new Requestor(Constant.REMOVE_FAVORITE, FavoriteAdapter.this)
                            .removeFavorite(list.get(position).getFevId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void success(Object o, int code) {
        dialog.dismiss();
        if (code == Constant.REMOVE_FAVORITE) {
            MyResponse response = (MyResponse) o;
            if (response != null) {
                if (response.isStatus()) {
                    list.remove(removedPosition);
                    notifyItemRemoved(removedPosition);
                    notifyItemRangeChanged(removedPosition,list.size());
                    if (list.size()==0)
                    removed.itemRemoved();
                }
                Utility.showToast(mContex, response.getMessage());
            }
        }
    }

    @Override
    public void error(Object o, int code) {
        dialog.dismiss();
        Utility.showToast(mContex, o.toString());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_item;
        AppCompatTextView tvName, tvPrice, tvDes;
        AppCompatImageView favorite,imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ll_item = itemView.findViewById(R.id.ll_item);
            tvName = itemView.findViewById(R.id.tvname);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDes = itemView.findViewById(R.id.tvDes);
            favorite = itemView.findViewById(R.id.removeFavorite);
            imageView = itemView.findViewById(R.id.post_img);
        }
    }
}
