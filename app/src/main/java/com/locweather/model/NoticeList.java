package com.locweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NoticeList {
    @SerializedName("weather")
    private ArrayList<Notice> noticeList;


    public  ArrayList<Notice> getNoticeArrayList() {
        return noticeList;
    }

    public void setNoticeArrayList(ArrayList<Notice> noticeArrayList) {
        this.noticeList = noticeArrayList;
    }
    @SerializedName("main")
    @Expose
    private Main main;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
    @SerializedName("wind")
    @Expose
    private Wind wind;
    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}