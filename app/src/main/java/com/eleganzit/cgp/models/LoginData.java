package com.eleganzit.cgp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("ginning_name")
    @Expose
    private String ginningName;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("expance")
    @Expose
    private String expance;
    @SerializedName("register_date")
    @Expose
    private String registerDate;
    @SerializedName("status")
    @Expose
    private String status;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGinningName() {
        return ginningName;
    }

    public void setGinningName(String ginningName) {
        this.ginningName = ginningName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getExpance() {
        return expance;
    }

    public void setExpance(String expance) {
        this.expance = expance;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
