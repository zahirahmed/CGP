package com.eleganzit.cgp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PurchaseListData {
    @SerializedName("purchase_id")
    @Expose
    private String purchaseId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("add_date")
    @Expose
    private String addDate;
    @SerializedName("heap")
    @Expose
    private String heap;
    @SerializedName("vehicle_no")
    @Expose
    private String vehicleNo;
    @SerializedName("final_weight")
    @Expose
    private String finalWeight;
    @SerializedName("rate_of_kapas")
    @Expose
    private String rateOfKapas;
    @SerializedName("per_of_cotton")
    @Expose
    private String perOfCotton;
    @SerializedName("shortage")
    @Expose
    private String shortage;
    @SerializedName("seed_rate")
    @Expose
    private String seedRate;
    @SerializedName("approx_bale_rate")
    @Expose
    private String approxBaleRate;
    @SerializedName("created_date")
    @Expose
    private String createdDate;

    public PurchaseListData(String purchaseId, String userId, String addDate, String heap, String vehicleNo, String finalWeight, String rateOfKapas, String perOfCotton, String shortage, String seedRate, String approxBaleRate, String createdDate) {
        this.purchaseId = purchaseId;
        this.userId = userId;
        this.addDate = addDate;
        this.heap = heap;
        this.vehicleNo = vehicleNo;
        this.finalWeight = finalWeight;
        this.rateOfKapas = rateOfKapas;
        this.perOfCotton = perOfCotton;
        this.shortage = shortage;
        this.seedRate = seedRate;
        this.approxBaleRate = approxBaleRate;
        this.createdDate = createdDate;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getHeap() {
        return heap;
    }

    public void setHeap(String heap) {
        this.heap = heap;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getFinalWeight() {
        return finalWeight;
    }

    public void setFinalWeight(String finalWeight) {
        this.finalWeight = finalWeight;
    }

    public String getRateOfKapas() {
        return rateOfKapas;
    }

    public void setRateOfKapas(String rateOfKapas) {
        this.rateOfKapas = rateOfKapas;
    }

    public String getPerOfCotton() {
        return perOfCotton;
    }

    public void setPerOfCotton(String perOfCotton) {
        this.perOfCotton = perOfCotton;
    }

    public String getShortage() {
        return shortage;
    }

    public void setShortage(String shortage) {
        this.shortage = shortage;
    }

    public String getSeedRate() {
        return seedRate;
    }

    public void setSeedRate(String seedRate) {
        this.seedRate = seedRate;
    }

    public String getApproxBaleRate() {
        return approxBaleRate;
    }

    public void setApproxBaleRate(String approxBaleRate) {
        this.approxBaleRate = approxBaleRate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
