package com.locweather;

import com.google.gson.annotations.SerializedName;

public class Notice {
    @SerializedName("lat")
    private Double lat;
    @SerializedName("lon")
    private Double lon;


    public Notice(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;

    }

    public Double getlat() {
        return lat;
    }

    public void setlat(Double lat) {
        this.lat = lat;
    }

    public Double getlon() {
        return lon;
    }

    public void setlan(Double lon) {
        this.lon = lon;
    }
}
