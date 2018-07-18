package com.arropa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetailsModel {
    @SerializedName("ven_id")
    @Expose
    private String venId;
    @SerializedName("ven_name")
    @Expose
    private String venName;
    @SerializedName("ven_email")
    @Expose
    private String venEmail;
    @SerializedName("ven_shopname")
    @Expose
    private String venShopname;
    @SerializedName("ven_adhaar_no")
    @Expose
    private String venAdhaarNo;
    @SerializedName("shop_address")
    @Expose
    private String shopAddress;
    @SerializedName("residential_address")
    @Expose
    private String residentialAddress;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("vendermobile")
    @Expose
    private String vendermobile;
    @SerializedName("status")
    @Expose
    private int status;

    public String getVenName() {
        return venName;
    }

    public void setVenName(String venName) {
        this.venName = venName;
    }

    public String getVenEmail() {
        return venEmail;
    }

    public void setVenEmail(String venEmail) {
        this.venEmail = venEmail;
    }

    public String getVenShopname() {
        return venShopname;
    }

    public void setVenShopname(String venShopname) {
        this.venShopname = venShopname;
    }

    public String getVenAdhaarNo() {
        return venAdhaarNo;
    }

    public void setVenAdhaarNo(String venAdhaarNo) {
        this.venAdhaarNo = venAdhaarNo;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVendermobile() {
        return vendermobile;
    }

    public void setVendermobile(String vendermobile) {
        this.vendermobile = vendermobile;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getVenId() {
        return venId;
    }

    public void setVenId(String venId) {
        this.venId = venId;
    }

}
