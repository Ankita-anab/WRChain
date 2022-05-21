package com.example.wrchain.View.ModalClass;

public class Locationhelper {
    private double Longitude;
    private double Latitude;
    private String address;
    private String locality;


    public Locationhelper(double longitude, double latitude, String address, String locality) {
        Longitude = longitude;
        Latitude = latitude;
        this.address = address;
        this.locality=locality;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }
}
