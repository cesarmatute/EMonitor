package com.cmsoft.e_monitor;

/**
 * Created by cesar on 03/01/18.
 */

public class Earthquake {
    private Double Magnitude;
    private String place;

    public Earthquake(Double magnitude, String place) {
        Magnitude = magnitude;
        this.place = place;
    }

    public Double getMagnitude() {
        return Magnitude;
    }

    public String getPlace() {
        return place;
    }
}
