package com.eleganzit.cgp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateListData {
    @SerializedName("state_id")
    @Expose
    private String stateId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("cotton_rate")
    @Expose
    private String cottonRate;
    @SerializedName("seed_rate")
    @Expose
    private String seedRate;
    @SerializedName("bales_weight")
    @Expose
    private String balesWeight;

    public StateListData(String stateId, String name) {
        this.stateId = stateId;
        this.name = name;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCottonRate() {
        return cottonRate;
    }

    public void setCottonRate(String cottonRate) {
        this.cottonRate = cottonRate;
    }

    public String getSeedRate() {
        return seedRate;
    }

    public void setSeedRate(String seedRate) {
        this.seedRate = seedRate;
    }

    public String getBalesWeight() {
        return balesWeight;
    }

    public void setBalesWeight(String balesWeight) {
        this.balesWeight = balesWeight;
    }

    @Override
    public String toString() {
        return name;
    }
}
