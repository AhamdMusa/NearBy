package com.example.nearby.model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Adrasses implements Serializable {
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private LatLng latLng;

    public Adrasses() {
    }

    public Adrasses(String name) {
        this.name = name;
    }

    public Adrasses(LatLng latLng) {
        this.latLng = latLng;
    }

    public Adrasses(String name, String address, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
