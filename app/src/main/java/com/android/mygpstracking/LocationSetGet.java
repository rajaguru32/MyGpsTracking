package com.android.mygpstracking;

public class LocationSetGet {
    //getter and setter for lat and lng
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    double lat;
    double lng;
}
