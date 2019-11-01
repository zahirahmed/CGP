package com.eleganzit.cgp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockData {
    @SerializedName("approx_bale_stock_kg")
    @Expose
    private Integer approxBaleStockKg;
    @SerializedName("approx_bale_stock_bales")
    @Expose
    private Integer approxBaleStockBales;
    @SerializedName("approx_seed_stock_truck")
    @Expose
    private Integer approxSeedStockTruck;
    @SerializedName("approx_seed_stock_kg")
    @Expose
    private Integer approxSeedStockKg;

    public Integer getApproxBaleStockKg() {
        return approxBaleStockKg;
    }

    public void setApproxBaleStockKg(Integer approxBaleStockKg) {
        this.approxBaleStockKg = approxBaleStockKg;
    }

    public Integer getApproxBaleStockBales() {
        return approxBaleStockBales;
    }

    public void setApproxBaleStockBales(Integer approxBaleStockBales) {
        this.approxBaleStockBales = approxBaleStockBales;
    }

    public Integer getApproxSeedStockTruck() {
        return approxSeedStockTruck;
    }

    public void setApproxSeedStockTruck(Integer approxSeedStockTruck) {
        this.approxSeedStockTruck = approxSeedStockTruck;
    }

    public Integer getApproxSeedStockKg() {
        return approxSeedStockKg;
    }

    public void setApproxSeedStockKg(Integer approxSeedStockKg) {
        this.approxSeedStockKg = approxSeedStockKg;
    }
}
