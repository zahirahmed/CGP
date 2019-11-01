package com.eleganzit.cgp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSeedMillsData {
    @SerializedName("seed_mill_id")
    @Expose
    private String seedMillId;
    @SerializedName("mill_name")
    @Expose
    private String millName;
    @SerializedName("user_id")
    @Expose
    private String userId;

    public GetSeedMillsData(String seedMillId, String millName) {
        this.seedMillId = seedMillId;
        this.millName = millName;
    }

    public String getSeedMillId() {
        return seedMillId;
    }

    public void setSeedMillId(String seedMillId) {
        this.seedMillId = seedMillId;
    }

    public String getMillName() {
        return millName;
    }

    public void setMillName(String millName) {
        this.millName = millName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return millName;
    }
}
