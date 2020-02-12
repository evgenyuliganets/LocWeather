package com.locweather.model;

import com.google.gson.annotations.SerializedName;

public class Notice {

    @SerializedName("description")
    private String weather;


    public Notice( String weather) {

        this.weather = weather;


    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }


}
