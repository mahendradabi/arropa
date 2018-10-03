package com.arropa.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arropa.ActivityProductDetails;
import com.arropa.R;
import com.arropa.models.ProductModel;
import com.arropa.servers.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {
    private static final StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();

    Context mContex;
    List<ProductModel> list;

    public ProductListAdapter(Context mContex, List<ProductModel> list) {
        this.mContex = mContex;
        this.list = list;
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
        holder.tvPrice.setPaintFlags(holder.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        try {
            int i = Integer.parseInt(productModel.getProductPrice());
            int i1 = Integer.parseInt(productModel.getDiscount_price());
            int i2 = i - i1;
            holder.tvDiscountPrice.setText(Constant.CURRENCY + " " + i2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        holder.tv_discount.setText("Discount "+Constant.CURRENCY + " " + productModel.getDiscount_price());
        // holder.tvDiscountPrice.setPaintFlags(holder.tvDiscountPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        holder.tvName.setText(productModel.getProductName());
        holder.tvDes.setText(Html.fromHtml(productModel.getProductDesc()));

        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContex.startActivity(new Intent(mContex, ActivityProductDetails.class)
                        .putExtra("product", list.get(position))
                );
            }
        });

        try {
            String img = productModel.getImages();
            if (img != null)

            {
                String[] split = img.split(",");
                if (split != null && split.length > 0)
                    Picasso.get()
                            .load(Constant.IMAGEPATH + split[0])
                            .placeholder(R.drawable.shirt)
                            .error(R.drawable.shirt)
                            .into(holder.imageView);
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
        LinearLayout ll_item;
        AppCompatImageView imageView;
        AppCompatTextView tvName, tvPrice, tvDes, tvDiscountPrice, tv_discount;

        public MyViewHolder(View itemView) {
            super(itemView);
            ll_item = itemView.findViewById(R.id.ll_item);
            tvName = itemView.findViewById(R.id.tvname);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDiscountPrice = itemView.findViewById(R.id.tvPriceDiscount);
            tv_discount = itemView.findViewById(R.id.tv_discount);
            tvDes = itemView.findViewById(R.id.tvDes);
            imageView = itemView.findViewById(R.id.post_img);
        }
    }
}
