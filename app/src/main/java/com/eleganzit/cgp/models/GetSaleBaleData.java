package com.eleganzit.cgp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSaleBaleData {
    @SerializedName("sell_id")
    @Expose
    private String sellId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("invoice_no")
    @Expose
    private String invoiceNo;
    @SerializedName("pr_no")
    @Expose
    private String prNo;
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
    @SerializedName("approx_bale_ammount")
    @Expose
    private String approxBaleAmmount;
    @SerializedName("created_date")
    @Expose
    private String createdDate;

    public GetSaleBaleData(String sellId, String userId, String invoiceNo, String prNo, String sellDate, String heap, String baleRate, String finalWeight, String noOfBales, String millId, String traderId, String approxBaleAmmount, String createdDate) {
        this.sellId = sellId;
        this.userId = userId;
        this.invoiceNo = invoiceNo;
        this.prNo = prNo;
        this.sellDate = sellDate;
        this.heap = heap;
        this.baleRate = baleRate;
        this.finalWeight = finalWeight;
        this.noOfBales = noOfBales;
        this.millId = millId;
        this.traderId = traderId;
        this.approxBaleAmmount = approxBaleAmmount;
        this.createdDate = createdDate;
    }

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

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getPrNo() {
        return prNo;
    }

    public void setPrNo(String prNo) {
        this.prNo = prNo;
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

    public String getApproxBaleAmmount() {
        return approxBaleAmmount;
    }

    public void setApproxBaleAmmount(String approxBaleAmmount) {
        this.approxBaleAmmount = approxBaleAmmount;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}