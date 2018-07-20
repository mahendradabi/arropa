package com.arropa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityList extends MyResponse {
    @SerializedName("citydetail")
    @Expose
    private List<Citydetail> citydetail = null;

    public List<Citydetail> getCitydetail() {
        return citydetail;
    }

    public void setCitydetail(List<Citydetail> citydetail) {
        this.citydetail = citydetail;
    }
}
