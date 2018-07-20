package com.arropa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateList extends MyResponse {
    @SerializedName("Statedetail")
    @Expose
    private List<Statedetail> statedetail = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Statedetail> getStatedetail() {
        return statedetail;
    }

    public void setStatedetail(List<Statedetail> statedetail) {
        this.statedetail = statedetail;
    }

}
