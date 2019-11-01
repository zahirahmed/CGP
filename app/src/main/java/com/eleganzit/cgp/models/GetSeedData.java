package com.eleganzit.cgp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSeedData {
    @SerializedName("seed_id")
    @Expose
    private String seedId;
    @SerializedName("invoice_no")
    @Expose
    private String invoiceNo;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("seed_date")
    @Expose
    private String seedDate;
    @SerializedName("vehicle_no")
    @Expose
    private String vehicleNo;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("seed_rate")
    @Expose
    private String seedRate;
    @SerializedName("seed_mill_id")
    @Expose
    private String seedMillId;
    @SerializedName("seed_trader_id")
    @Expose
    private String seedTraderId;
    @SerializedName("heap")
    @Expose
    private String heap;
    @SerializedName("approx_seed_ammount")
    @Expose
    private String approxSeedAmmount;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("mill_name")
    @Expose
    private String millName;
    @SerializedName("trader_name")
    @Expose
    private String trader_name;

    public String getSeedId() {
        return seedId;
    }

    public void setSeedId(String seedId) {
        this.seedId = seedId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSeedDate() {
        return seedDate;
    }

    public void setSeedDate(String seedDate) {
        this.seedDate = seedDate;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSeedRate() {
        return seedRate;
    }

    public void setSeedRate(String seedRate) {
        this.seedRate = seedRate;
    }

    public String getSeedMillId() {
        return seedMillId;
    }

    public void setSeedMillId(String seedMillId) {
        this.seedMillId = seedMillId;
    }

    public String getSeedTraderId() {
        return seedTraderId;
    }

    public void setSeedTraderId(String seedTraderId) {
        this.seedTraderId = seedTraderId;
    }

    public String getHeap() {
        return heap;
    }

    public void setHeap(String heap) {
        this.heap = heap;
    }

    public String getApproxSeedAmmount() {
        return approxSeedAmmount;
    }

    public void setApproxSeedAmmount(String approxSeedAmmount) {
        this.approxSeedAmmount = approxSeedAmmount;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getMillName() {
        return millName;
    }

    public void setMillName(String millName) {
        this.millName = millName;
    }

    public String getTrader_name() {
        return trader_name;
    }

    public void setTrader_name(String trader_name) {
        this.trader_name = trader_name;
    }
}
