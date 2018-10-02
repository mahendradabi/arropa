package com.arropa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreditModels extends MyResponse {
    @SerializedName("Details")
    @Expose
    private Credit details;

    public Credit getDetails() {
        return details;
    }

    public void setDetails(Credit details) {
        this.details = details;
    }
}
