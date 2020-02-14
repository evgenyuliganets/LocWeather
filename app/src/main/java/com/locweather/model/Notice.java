package com.locweather.model;

import com.google.gson.annotations.SerializedName;

public class Notice {

    @SerializedName("main")
    private String weather;
    @SerializedName("description")
    private String info;
    public Notice( String weather,String info) {

        this.weather = weather;
        this.info = info;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getInfo() {
        return info;
    }

    public void setTemp(String info) {
        this.info = info;
    }
}
