package com.android.mygpstracking.model;

public class UserData {

    String userEmail;
    String userPassword;
    double longitude;

    public UserData(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    double latitude;

    public UserData(String userEmail, String userPassword, double longitude, double latitude) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public UserData() {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


}
