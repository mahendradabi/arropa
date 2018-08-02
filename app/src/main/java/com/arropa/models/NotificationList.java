package com.arropa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationList extends MyResponse {
    @SerializedName("Details")
    @Expose
    private List<MyNotification> details = null;

    public List<MyNotification> getDetails() {
        return details;
    }

    public void setDetails(List<MyNotification> details) {
        this.details = details;
    }
}
