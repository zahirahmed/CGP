package com.eleganzit.cgp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvgPurchaseData {
    @SerializedName("avg_kapas_rate")
    @Expose
    private String avgKapasRate;
    @SerializedName("avg_of_per_cotton")
    @Expose
    private String avgOfPerCotton;
    @SerializedName("avg_shortage")
    @Expose
    private String avgShortage;
    @SerializedName("approx_bale_rate")
    @Expose
    private String approxBaleRate;
    @SerializedName("avg_pure_cotton_weight")
    @Expose
    private String avgPureCottonWeight;
    @SerializedName("bales")
    @Expose
    private String bales;
    @SerializedName("avg_seed_rate")
    @Expose
    private String avgSeedRate;
    @SerializedName("avg_per_seed_rate")
    @Expose
    private String avgPerSeedRate;
    @SerializedName("total_seed_weight")
    @Expose
    private String totalSeedWeight;
    @SerializedName("purchase_cotton")
    @Expose
    private String purchaseCotton;

    @SerializedName("avg_label")
    @Expose
    private String avg_label;
    @SerializedName("getseed_rate")
    @Expose
    private String getseed_rate;
    @SerializedName("getcotton_rate")
    @Expose
    private String getcotton_rate;
    @SerializedName("getbales_weight")
    @Expose
    private String getbales_weight;

    public String getAvg_label() {
        return avg_label;
    }

    public void setAvg_label(String avg_label) {
        this.avg_label = avg_label;
    }

    public String getAvgKapasRate() {
        return avgKapasRate;
    }

    public void setAvgKapasRate(String avgKapasRate) {
        this.avgKapasRate = avgKapasRate;
    }

    public String getAvgOfPerCotton() {
        return avgOfPerCotton;
    }

    public void setAvgOfPerCotton(String avgOfPerCotton) {
        this.avgOfPerCotton = avgOfPerCotton;
    }

    public String getAvgShortage() {
        return avgShortage;
    }

    public void setAvgShortage(String avgShortage) {
        this.avgShortage = avgShortage;
    }

    public String getApproxBaleRate() {
        return approxBaleRate;
    }

    public void setApproxBaleRate(String approxBaleRate) {
        this.approxBaleRate = approxBaleRate;
    }

    public String getAvgPureCottonWeight() {
        return avgPureCottonWeight;
    }

    public void setAvgPureCottonWeight(String avgPureCottonWeight) {
        this.avgPureCottonWeight = avgPureCottonWeight;
    }

    public String getBales() {
        return bales;
    }

    public void setBales(String bales) {
        this.bales = bales;
    }

    public String getAvgSeedRate() {
        return avgSeedRate;
    }

    public void setAvgSeedRate(String avgSeedRate) {
        this.avgSeedRate = avgSeedRate;
    }

    public String getAvgPerSeedRate() {
        return avgPerSeedRate;
    }

    public void setAvgPerSeedRate(String avgPerSeedRate) {
        this.avgPerSeedRate = avgPerSeedRate;
    }

    public String getTotalSeedWeight() {
        return totalSeedWeight;
    }

    public void setTotalSeedWeight(String totalSeedWeight) {
        this.totalSeedWeight = totalSeedWeight;
    }

    public String getPurchaseCotton() {
        return purchaseCotton;
    }

    public void setPurchaseCotton(String purchaseCotton) {
        this.purchaseCotton = purchaseCotton;
    }

    public String getGetseed_rate() {
        return getseed_rate;
    }

    public void setGetseed_rate(String getseed_rate) {
        this.getseed_rate = getseed_rate;
    }

    public String getGetcotton_rate() {
        return getcotton_rate;
    }

    public void setGetcotton_rate(String getcotton_rate) {
        this.getcotton_rate = getcotton_rate;
    }

    public String getGetbales_weight() {
        return getbales_weight;
    }

    public void setGetbales_weight(String getbales_weight) {
        this.getbales_weight = getbales_weight;
    }
}
