package com.cmsoft.e_monitor;

/**
 * Created by cesar on 03/01/18.
 */

public class Earthquake {
    private String Magnitude;
    private String place;

    public Earthquake(String magnitude, String place) {
        Magnitude = magnitude;
        this.place = place;
    }

    public String getMagnitude() {
        return Magnitude;
    }

    public String getPlace() {
        return place;
    }
}
