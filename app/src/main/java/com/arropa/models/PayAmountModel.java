package com.arropa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayAmountModel extends MyResponse {
    @SerializedName("Details")
    @Expose
    private PayModel details;

    public PayModel getDetails() {
        return details;
    }

    public void setDetails(PayModel details) {
        this.details = details;
    }
}
