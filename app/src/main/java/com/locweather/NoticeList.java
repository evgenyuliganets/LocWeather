package com.locweather;

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
}