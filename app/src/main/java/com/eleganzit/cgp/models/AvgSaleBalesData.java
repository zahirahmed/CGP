package com.eleganzit.cgp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvgSaleBalesData {
    @SerializedName("total_bale_ammount")
    @Expose
    private String totalBaleAmmount;
    @SerializedName("total_bale_weight")
    @Expose
    private String totalBaleWeight;
    @SerializedName("total_bale_sold")
    @Expose
    private String totalBaleSold;
    @SerializedName("avg_bale_rate")
    @Expose
    private String avgBaleRate;
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

    public String getTotalBaleAmmount() {
        return totalBaleAmmount;
    }

    public void setTotalBaleAmmount(String totalBaleAmmount) {
        this.totalBaleAmmount = totalBaleAmmount;
    }

    public String getTotalBaleWeight() {
        return totalBaleWeight;
    }

    public void setTotalBaleWeight(String totalBaleWeight) {
        this.totalBaleWeight = totalBaleWeight;
    }

    public String getTotalBaleSold() {
        return totalBaleSold;
    }

    public void setTotalBaleSold(String totalBaleSold) {
        this.totalBaleSold = totalBaleSold;
    }

    public String getAvgBaleRate() {
        return avgBaleRate;
    }

    public void setAvgBaleRate(String avgBaleRate) {
        this.avgBaleRate = avgBaleRate;
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
