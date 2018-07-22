package com.arropa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileImgModel extends MyResponse {
    @SerializedName("Fevdetail")
    @Expose
    private List<PictureModel> fevdetail = null;

    public List<PictureModel> getPicture() {
        return fevdetail;
    }

    public void setFevdetail(List<PictureModel> fevdetail) {
        this.fevdetail = fevdetail;
    }
}
