package com.locweather.model;

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
    private static final String TAG = "NoticeList";
    @SerializedName("temp")
    private ArrayList<Notice> tempList;


    public  ArrayList<Notice> getTempList() {
        return tempList;
    }

    public void setTempList(ArrayList<Notice> tempArrayList) {
        this.tempList = tempArrayList;
    }
}