package com.eleganzit.cgp.models;

public class PurchaseData {

    String id,sr_no,heap,veh_no,cotton_rate,approx_rate,cotton_perc,shortage;

    public PurchaseData(String id, String sr_no, String heap, String veh_no, String cotton_rate, String approx_rate, String cotton_perc, String shortage) {
        this.id = id;
        this.sr_no = sr_no;
        this.heap = heap;
        this.veh_no = veh_no;
        this.cotton_rate = cotton_rate;
        this.approx_rate = approx_rate;
        this.cotton_perc = cotton_perc;
        this.shortage = shortage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSr_no() {
        return sr_no;
    }

    public void setSr_no(String sr_no) {
        this.sr_no = sr_no;
    }

    public String getHeap() {
        return heap;
    }

    public void setHeap(String heap) {
        this.heap = heap;
    }

    public String getVeh_no() {
        return veh_no;
    }

    public void setVeh_no(String veh_no) {
        this.veh_no = veh_no;
    }

    public String getCotton_rate() {
        return cotton_rate;
    }

    public void setCotton_rate(String cotton_rate) {
        this.cotton_rate = cotton_rate;
    }

    public String getApprox_rate() {
        return approx_rate;
    }

    public void setApprox_rate(String approx_rate) {
        this.approx_rate = approx_rate;
    }

    public String getCotton_perc() {
        return cotton_perc;
    }

    public void setCotton_perc(String cotton_perc) {
        this.cotton_perc = cotton_perc;
    }

    public String getShortage() {
        return shortage;
    }

    public void setShortage(String shortage) {
        this.shortage = shortage;
    }
}
