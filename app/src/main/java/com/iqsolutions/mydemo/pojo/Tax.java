package com.iqsolutions.mydemo.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tax implements Serializable {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("value")
    @Expose
    private double value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
