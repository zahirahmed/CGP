package com.eleganzit.cgp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaleBaleTraderData {
    @SerializedName("trader_id")
    @Expose
    private String traderId;
    @SerializedName("sell_id")
    @Expose
    private String sellId;
    @SerializedName("trader_name")
    @Expose
    private String traderName;
    @SerializedName("user_id")
    @Expose
    private String userId;

    public SaleBaleTraderData(String traderId, String sellId, String traderName) {
        this.traderId = traderId;
        this.sellId = sellId;
        this.traderName = traderName;
    }

    public String getTraderId() {
        return traderId;
    }

    public void setTraderId(String traderId) {
        this.traderId = traderId;
    }

    public String getSellId() {
        return sellId;
    }

    public void setSellId(String sellId) {
        this.sellId = sellId;
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
