package com.arropa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginUserDetails {
    @SerializedName("creaditlimit")
    @Expose
    private List<Object> creaditlimit = null;
    @SerializedName("userdetails")
    @Expose
    private UserDetailsModel userdetails;

    public List<Object> getCreaditlimit() {
        return creaditlimit;
    }

    public void setCreaditlimit(List<Object> creaditlimit) {
        this.creaditlimit = creaditlimit;
    }

    public UserDetailsModel getUserdetails() {
        return userdetails;
    }

    public void setUserdetails(UserDetailsModel userdetails) {
        this.userdetails = userdetails;
    }

}
