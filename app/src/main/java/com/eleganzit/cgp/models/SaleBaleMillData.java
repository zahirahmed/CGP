package com.eleganzit.cgp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaleBaleMillData {
    @SerializedName("mill_id")
    @Expose
    private String millId;
    @SerializedName("mill_name")
    @Expose
    private String millName;
    @SerializedName("user_id")
    @Expose
    private String userId;

    public SaleBaleMillData(String millId, String millName, String userId) {
        this.millId = millId;
        this.millName = millName;
        this.userId = userId;
    }

    public String getMillId() {
        return millId;
    }

    public void setMillId(String millId) {
        this.millId = millId;
    }

    public String getMillName() {
        return millName;
    }

    public void setMillName(String millName) {
        this.millName = millName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return millName;
    }
}
