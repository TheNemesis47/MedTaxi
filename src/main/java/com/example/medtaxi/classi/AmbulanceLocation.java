package com.example.medtaxi.classi;

import java.io.Serializable;

public class AmbulanceLocation implements Serializable {
    private double latitude;
    private double longitude;

    public AmbulanceLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
