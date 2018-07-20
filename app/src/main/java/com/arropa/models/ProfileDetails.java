package com.arropa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileDetails extends MyResponse {
    @SerializedName("Fevdetail")
    @Expose
    private List<UserDetailsModel> fevdetail = null;

    public List<UserDetailsModel> getFevdetail() {
        return fevdetail;
    }

    public void setFevdetail(List<UserDetailsModel> fevdetail) {
        this.fevdetail = fevdetail;
    }
}
