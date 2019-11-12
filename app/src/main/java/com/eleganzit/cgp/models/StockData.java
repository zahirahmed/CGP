package com.eleganzit.cgp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;

public class StockData {
    @SerializedName("approx_bale_stock_kg")
    @Expose
    private BigInteger approxBaleStockKg;
    @SerializedName("approx_bale_stock_bales")
    @Expose
    private BigInteger approxBaleStockBales;
    @SerializedName("approx_seed_stock_truck")
    @Expose
    private BigInteger approxSeedStockTruck;
    @SerializedName("approx_seed_stock_kg")
    @Expose
    private BigInteger approxSeedStockKg;

    public BigInteger getApproxBaleStockKg() {
        return approxBaleStockKg;
    }

    public void setApproxBaleStockKg(BigInteger approxBaleStockKg) {
        this.approxBaleStockKg = approxBaleStockKg;
    }

    public BigInteger getApproxBaleStockBales() {
        return approxBaleStockBales;
    }

    public void setApproxBaleStockBales(BigInteger approxBaleStockBales) {
        this.approxBaleStockBales = approxBaleStockBales;
    }

    public BigInteger getApproxSeedStockTruck() {
        return approxSeedStockTruck;
    }

    public void setApproxSeedStockTruck(BigInteger approxSeedStockTruck) {
        this.approxSeedStockTruck = approxSeedStockTruck;
    }

    public BigInteger getApproxSeedStockKg() {
        return approxSeedStockKg;
    }

    public void setApproxSeedStockKg(BigInteger approxSeedStockKg) {
        this.approxSeedStockKg = approxSeedStockKg;
    }
}
