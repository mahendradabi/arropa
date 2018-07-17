package com.arropa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel extends MyResponse {
    @SerializedName("Userdetail")
    @Expose
    private LoginUserDetails userdetail;

    public LoginUserDetails getUserdetail() {
        return userdetail;
    }

    public void setUserdetail(LoginUserDetails userdetail) {
        this.userdetail = userdetail;
    }
}
