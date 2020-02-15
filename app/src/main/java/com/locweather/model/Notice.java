package com.locweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notice {

    @SerializedName("main")
    @Expose
    private String weather;
    @SerializedName("description")
    @Expose
    private String info;
    @SerializedName("icon")
    @Expose
    private String icon;
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
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
