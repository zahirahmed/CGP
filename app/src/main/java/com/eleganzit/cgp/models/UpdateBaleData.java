package com.eleganzit.cgp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateBaleData {
    @SerializedName("sell_id")
    @Expose
    private String sellId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("pr_no")
    @Expose
    private String prNo;
    @SerializedName("invoice_no")
    @Expose
    private String invoiceNo;
    @SerializedName("sell_date")
    @Expose
    private String sellDate;
    @SerializedName("heap")
    @Expose
    private String heap;
    @SerializedName("bale_rate")
    @Expose
    private String baleRate;
    @SerializedName("final_weight")
    @Expose
    private String finalWeight;
    @SerializedName("no_of_bales")
    @Expose
    private String noOfBales;
    @SerializedName("mill_id")
    @Expose
    private String millId;
    @SerializedName("trader_id")
    @Expose
    private String traderId;

    public String getSellId() {
        return sellId;
    }

    public void setSellId(String sellId) {
        this.sellId = sellId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPrNo() {
        return prNo;
    }

    public void setPrNo(String prNo) {
        this.prNo = prNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getSellDate() {
        return sellDate;
    }

    public void setSellDate(String sellDate) {
        this.sellDate = sellDate;
    }

    public String getHeap() {
        return heap;
    }

    public void setHeap(String heap) {
        this.heap = heap;
    }

    public String getBaleRate() {
        return baleRate;
    }

    public void setBaleRate(String baleRate) {
        this.baleRate = baleRate;
    }

    public String getFinalWeight() {
        return finalWeight;
    }

    public void setFinalWeight(String finalWeight) {
        this.finalWeight = finalWeight;
    }

    public String getNoOfBales() {
        return noOfBales;
    }

    public void setNoOfBales(String noOfBales) {
        this.noOfBales = noOfBales;
    }

    public String getMillId() {
        return millId;
    }

    public void setMillId(String millId) {
        this.millId = millId;
    }

    public String getTraderId() {
        return traderId;
    }

    public void setTraderId(String traderId) {
        this.traderId = traderId;
    }
}
