package com.eleganzit.cgp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddSeedMillsData {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("mill_name")
    @Expose
    private String millName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMillName() {
        return millName;
    }

    public void setMillName(String millName) {
        this.millName = millName;
    }
}
