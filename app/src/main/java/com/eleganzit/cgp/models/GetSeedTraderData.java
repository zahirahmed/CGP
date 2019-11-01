package com.eleganzit.cgp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSeedTraderData {
    @SerializedName("seed_trader_id")
    @Expose
    private String seedTraderId;
    @SerializedName("trader_name")
    @Expose
    private String traderName;
    @SerializedName("user_id")
    @Expose
    private String userId;

    public GetSeedTraderData(String seedTraderId, String traderName) {
        this.seedTraderId = seedTraderId;
        this.traderName = traderName;
    }

    public String getSeedTraderId() {
        return seedTraderId;
    }

    public void setSeedTraderId(String seedTraderId) {
        this.seedTraderId = seedTraderId;
    }

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return traderName;
    }
}
