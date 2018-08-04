package com.arropa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayModel {

    @SerializedName("total_payamount")
    @Expose
    private String totalPayamount;


    @SerializedName("total_orderamount")
    @Expose
    private String totalOrderttlamount;
    @SerializedName("total_creaditamount")
    @Expose
    private String totalCreaditamount;

    public String getTotalPayamount() {
        return totalPayamount;
    }

    public void setTotalPayamount(String totalPayamount) {
        this.totalPayamount = totalPayamount;
    }

    public String getTotalOrderttlamount() {
        return totalOrderttlamount;
    }

    public void setTotalOrderttlamount(String totalOrderttlamount) {
        this.totalOrderttlamount = totalOrderttlamount;
    }

    public String getTotalCreaditamount() {
        return totalCreaditamount;
    }

    public void setTotalCreaditamount(String totalCreaditamount) {
        this.totalCreaditamount = totalCreaditamount;
    }

}
