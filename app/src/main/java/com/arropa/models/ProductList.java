package com.arropa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductList extends MyResponse {

    @SerializedName("Details")
    @Expose
    private List<ProductModel> details = null;


    public List<ProductModel> getList() {
        return details;
    }

    public void setDetails(List<ProductModel> details) {
        this.details = details;
    }
}
