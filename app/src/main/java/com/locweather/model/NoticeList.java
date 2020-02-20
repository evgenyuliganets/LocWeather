package com.locweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NoticeList  {
    @SerializedName("weather")
    private ArrayList<Notice> noticeList;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("wind")
    @Expose
    private Wind wind;

    public NoticeList(ArrayList<Notice> noticeList, Main main, Wind wind) {
        this.noticeList = noticeList;
        this.main = main;
        this.wind = wind;
    }

    public  ArrayList<Notice> getNoticeArrayList() {
        return noticeList;
    }
    public void setNoticeArrayList(ArrayList<Notice> noticeArrayList) {
        this.noticeList = noticeArrayList;
    }

    public Main getMain() {
        return main;
    }
    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}