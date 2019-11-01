package com.eleganzit.cgp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddBaleTraderData {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("trader_name")
    @Expose
    private String traderName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }

}
