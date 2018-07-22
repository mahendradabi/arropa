package com.arropa.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.arropa.R;
import com.arropa.models.CartList;
import com.arropa.models.CartModel;
import com.arropa.models.MyResponse;
import com.arropa.servers.Constant;
import com.arropa.servers.Requestor;
import com.arropa.servers.ServerResponse;
import com.arropa.utils.DialogWindow;
import com.arropa.utils.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> implements ServerResponse {
    Context mContex;
    List<String> test = new ArrayList<>();
    List<CartModel> list;

    OnCartEmpty onCartEmpty;
    ProgressDialog progressDialog;
    int removedPosition;


    @Override
    public void success(Object o, int code) {
        progressDialog.dismiss();
        switch (code) {
            case Constant.REMOVE_FAVORITE:
                MyResponse response = (MyResponse) o;
                if (response != null) {
                    if (response.isStatus()) {
                        list.remove(removedPosition);
                        notifyItemRemoved(removedPosition);
                        notifyItemRangeChanged(removedPosition, list.size());

                        if (list.size() == 0)
                            onCartEmpty.onCartEmpty();

                    }
                    Utility.showToast(mContex, response.getMessage());
                }
                break;

            case Constant.UPDATE_QTY:
                CartList cartList = (CartList) o;
                if (cartList != null) {
                    if (cartList.isStatus() && cartList.getDetails() != null) {
                        try {
                            CartModel cartModel = cartList.getDetails().get(0);
                            if (cartModel != null) {
                                CartModel local = list.get(removedPosition);
                                local.setQty(cartModel.getQty());
                                local.setAmount(cartModel.getAmount());
                                notifyItemChanged(removedPosition);
                                if (cartModel.getQty().equals("0")) {
                                    list.remove(removedPosition);
                                    notifyItemRemoved(removedPosition);
                                    notifyItemRangeChanged(removedPosition, list.size());
                                    if (list.size()==0)
                                        onCartEmpty.onCartEmpty();
                                }

                            }
                        } catch (IndexOutOfBoundsException ex) {
                            ex.printStackTrace();
                        }

                    }
                    Utility.showToast(mContex, cartList.getMessage());
                }
                break;
        }


    }

    @Override
    public void error(Object o, int code) {
        progressDialog.dismiss();
    }


    public interface OnCartEmpty {
        void onCartEmpty();
    }

    public CartAdapter(Context mContex, List<CartModel> list, OnCartEmpty onCartEmpty) {
        this.mContex = mContex;
        this.list = list;
        this.onCartEmpty = onCartEmpty;
        this.progressDialog = DialogWindow.showProgressDialog(mContex, "Cart", "Removing product from cart.");
        progressDialog.setCancelable(false);


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
        final CartModel model = list.get(position);

        holder.tvqty.setText(model.getQty());
        holder.pname.setText(model.getProductName());
        holder.tvPrice.setText(Constant.CURRENCY + model.getAmount());

        Picasso.get().load(Constant.IMAGEPATH + model.getImages())
                .error(R.drawable.shirt)
                .placeholder(R.drawable.shirt)
                .into(holder.imageView);

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removedPosition = position;
                plusQty(list.get(position).getOrderNo());

            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removedPosition = position;
                minusQty(list.get(position).getOrderNo());
            }
        });
        holder.ll_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() > 0) {
                    progressDialog.setMessage("Removing product from cart.");
                    progressDialog.show();
                    removedPosition = position;
                    new Requestor(Constant.REMOVE_FAVORITE, CartAdapter.this)
                            .removeProduct(list.get(position).getOrderNo());
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_remove;
        AppCompatImageView imageView;
        AppCompatTextView pname, tvPrice, tvqty;
        AppCompatButton plus, minus;


        public MyViewHolder(View itemView) {
            super(itemView);
            ll_remove = itemView.findViewById(R.id.ll_remove);

            imageView = itemView.findViewById(R.id.post_img);
            pname = itemView.findViewById(R.id.post_title);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvqty = itemView.findViewById(R.id.tvqty);
            plus = itemView.findViewById(R.id.btnPlus);
            minus = itemView.findViewById(R.id.btnMinus);

        }
    }

    public void plusQty(String orderNo) {
        progressDialog.setMessage("Updating cart.");
        progressDialog.show();
        new Requestor(Constant.UPDATE_QTY, CartAdapter.this)
                .plusQty(orderNo);

    }

    public void minusQty(String orderNo) {
        progressDialog.setMessage("Updating cart.");
        progressDialog.show();
        new Requestor(Constant.UPDATE_QTY, CartAdapter.this)
                .minusQty(orderNo);

    }

}
