package com.arropa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderListModel extends MyResponse {

    @SerializedName("Details")
    @Expose
    private List<OrderModel> details = null;


    public List<OrderModel> getDetails() {
        return details;
    }

    public void setDetails(List<OrderModel> details) {
        this.details = details;
    }
}
