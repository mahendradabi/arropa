package com.arropa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductModel implements Serializable{
    @SerializedName("fev_id")
    @Expose
    private String fevId;

    @SerializedName("prod_id")
    @Expose
    private String prodId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_price")
    @Expose
    private String productPrice;
    @SerializedName("Alot_no")
    @Expose
    private String alotNo;
    @SerializedName("product_desc")
    @Expose
    private String productDesc;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("images")
    @Expose
    private String images;
    @SerializedName("status")
    @Expose
    private String status;

    public String getFevId() {
        return fevId;
    }

    public void setFevId(String fevId) {
        this.fevId = fevId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getAlotNo() {
        return alotNo;
    }

    public void setAlotNo(String alotNo) {
        this.alotNo = alotNo;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


