package com.arropa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.arropa.models.MyResponse;
import com.arropa.models.ProductModel;
import com.arropa.servers.Constant;
import com.arropa.servers.Requestor;
import com.arropa.servers.ServerResponse;
import com.arropa.sharedpreference.PrefKeys;
import com.arropa.sharedpreference.PreferenceManger;
import com.arropa.utils.Utility;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityProductDetails extends MyAbstractActivity implements ServerResponse {

    @BindView(R.id.llShare)
    LinearLayout tvShare;

    @BindView(R.id.llFavorite)
    LinearLayout tvFavorite;

    @BindView(R.id.producttvname)
    AppCompatTextView tvName;
    @BindView(R.id.producttvPrice)
    AppCompatTextView tvPrice;
    @BindView(R.id.producttvDes)
    AppCompatTextView tvDesc;

    @BindView(R.id.post_img)
    AppCompatImageView imageView;

    @BindView(R.id.btnBuyNow)
    AppCompatButton buyNow;
    @BindView(R.id.btnAddCart)
    AppCompatButton addcart;
    String id;

    boolean isBuyNowClicked;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        setTitle("Details");
        showBackButton();

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("product")) {
            ProductModel productModel = (ProductModel) intent.getSerializableExtra("product");
            if (productModel != null) {
                id = productModel.getProdId();
                tvName.setText(productModel.getProductName());
                tvPrice.setText(Constant.CURRENCY + " " + productModel.getProductPrice());
                tvDesc.setText(productModel.getProductDesc());
                Picasso.get()
                        .load(Constant.IMAGEPATH + productModel.getImages())
                        .error(R.drawable.shirt)
                        .placeholder(R.drawable.shirt)
                        .into(imageView);
            }
        }


        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.openShareIntent(ActivityProductDetails.this, "Arropa \n" + tvName.getText().toString() + "\n" + tvPrice.getText().toString() + "\n" + tvDesc.getText().toString()
                        + "\nVisit now at: http://www.arropa.in/"
                );
            }
        });

        tvFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id != null)
                    new Requestor(Constant.ADD_FAVORITE, ActivityProductDetails.this)
                            .addFavorite(PreferenceManger.getPreferenceManger().getString(PrefKeys.USERID), id);
                // Utility.showToast(ActivityProductDetails.this, "Added to favorite");
            }
        });

        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Requestor(Constant.ADD_FAVORITE, ActivityProductDetails.this)
                        .addCartProduct(PreferenceManger.getPreferenceManger().getString(PrefKeys.USERID), id);

            }
        });

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBuyNowClicked = true;
                addcart.performClick();
                // Utility.showToast(ActivityProductDetails.this,"");
            }
        });
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void success(Object o, int code) {
        switch (code) {
            case Constant.ADD_FAVORITE:
                MyResponse response = (MyResponse) o;
                if (response != null) {
                    if (response.isStatus() && isBuyNowClicked) {
                        return;
                    }
                    if (isBuyNowClicked)
                        startActivity(new Intent(ActivityProductDetails.this, ActivityCart.class));
                    isBuyNowClicked = false;
                    if (response.getMessage() != null&&!isBuyNowClicked)
                        Utility.showToast(ActivityProductDetails.this, response.getMessage());

                }
                break;
        }
    }

    @Override
    public void error(Object o, int code) {

    }
}
