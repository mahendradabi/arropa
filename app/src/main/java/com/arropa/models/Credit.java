package com.arropa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Credit {

    @SerializedName("creadit_amount")
    @Expose
    private String creaditAmount;
    @SerializedName("creadit_use")
    @Expose
    private String creaditUse;
    @SerializedName("cstatus")
    @Expose
    private String cstatus;

    public String getCreaditAmount() {
        return creaditAmount;
    }

    public void setCreaditAmount(String creaditAmount) {
        this.creaditAmount = creaditAmount;
    }

    public String getCreaditUse() {
        return creaditUse;
    }

    public void setCreaditUse(String creaditUse) {
        this.creaditUse = creaditUse;
    }

    public String getCstatus() {
        return cstatus;
    }

    public void setCstatus(String cstatus) {
        this.cstatus = cstatus;
    }

}
