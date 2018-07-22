package com.arropa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartList extends MyResponse {
    @SerializedName("Details")
    @Expose
    private List<CartModel> details = null;

    public List<CartModel> getDetails() {
        return details;
    }

    public void setDetails(List<CartModel> details) {
        this.details = details;
    }

}
