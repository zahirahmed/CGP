package com.eleganzit.cgp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastHeapData {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("heap")
    @Expose
    private String heap;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeap() {
        return heap;
    }

    public void setHeap(String heap) {
        this.heap = heap;
    }
}
