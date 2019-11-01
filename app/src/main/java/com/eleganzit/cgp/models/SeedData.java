package com.eleganzit.cgp.models;

public class SeedData {

    String id,sr_no,mill_name,rate,weight;

    public SeedData(String id, String sr_no, String mill_name, String rate, String weight) {
        this.id = id;
        this.sr_no = sr_no;
        this.mill_name = mill_name;
        this.rate = rate;
        this.weight = weight;
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

    public String getMill_name() {
        return mill_name;
    }

    public void setMill_name(String mill_name) {
        this.mill_name = mill_name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
