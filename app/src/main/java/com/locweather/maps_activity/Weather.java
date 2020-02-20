package com.locweather.maps_activity;

import com.google.android.gms.maps.model.LatLng;
import com.locweather.adapter.NoticeAdapter;
import com.locweather.model.Main;
import com.locweather.model.Notice;
import com.locweather.model.Wind;

import java.util.ArrayList;

import static com.locweather.maps_activity.MapsActivity.currentLocation;

public class Weather  {
    private long id=0;
    private String address;
    private Double windSpeed;
    private Integer windDegree;
    private String datalistIcon;
    private String datalistInfo;
    private String datalistWeather;
    private Double mainTemp;
    private Double mainFeel;
    private Integer mainHumidity;
    private Integer mainPressure;
    private String time;
    private Double locLat;
    private Double locLon;
    public Weather(ArrayList<Notice> dataList, Main main, Wind wind)
    {
        this.address=getAddressMap();
        this.windSpeed=wind.getSpeed();
        this.windDegree=wind.getDeg();
        this.datalistIcon=dataList.get(0).getIcon();
        this.datalistInfo= dataList.get(0).getInfo();
        this.datalistWeather=dataList.get(0).getWeather();
        this.mainTemp=main.getTemp();
        this.mainFeel=main.getFeelsLike();
        this.mainHumidity=main.getHumidity();
        this.mainPressure=main.getPressure();
        this.time=getDate();
        this.locLat=getloc().latitude;
        this.locLon=getloc().longitude;

    }
    public String getAddressMap() {
        return MapsActivity.addressMap;
    }
    public String getDate() {
        return NoticeAdapter.date;
    }
    public LatLng getloc(){
        return currentLocation;
    }

    public long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public Integer getWindDegree() {
        return windDegree;
    }

    public String getDatalistIcon() {
        return datalistIcon;
    }

    public String getDatalistInfo() {
        return datalistInfo;
    }

    public String getDatalistWeather() {
        return datalistWeather;
    }

    public Double getMainTemp() {
        return mainTemp;
    }

    public Double getMainFeel() {
        return mainFeel;
    }

    public Integer getMainHumidity() {
        return mainHumidity;
    }

    public Integer getMainPressure() {
        return mainPressure;
    }

    public String getTime() {
        return time;
    }

    public Double getLocLat() {
        return locLat;
    }

    public Double getLocLon() {
        return locLon;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindDegree(Integer windDegree) {
        this.windDegree = windDegree;
    }

    public void setDatalistIcon(String datalistIcon) {
        this.datalistIcon = datalistIcon;
    }

    public void setDatalistInfo(String datalistInfo) {
        this.datalistInfo = datalistInfo;
    }

    public void setDatalistWeather(String datalistWeather) {
        this.datalistWeather = datalistWeather;
    }

    public void setMainTemp(Double mainTemp) {
        this.mainTemp = mainTemp;
    }

    public void setMainFeel(Double mainFeel) {
        this.mainFeel = mainFeel;
    }

    public void setMainHumidity(Integer mainHumidity) {
        this.mainHumidity = mainHumidity;
    }

    public void setMainPressure(Integer mainPressure) {
        this.mainPressure = mainPressure;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLocLat(Double locLat) {
        this.locLat = locLat;
    }

    public void setLocLon(Double locLon) {
        this.locLon = locLon;
    }
}
