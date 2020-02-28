package com.locweather.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
@Entity
public class WeatherData {
    @PrimaryKey(autoGenerate = true)
    private long id;
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
    public WeatherData(){}
    @Ignore
    public WeatherData(String address, Double windSpeed, Integer windDegree, String datalistIcon,String datalistInfo,String datalistWeather, Double mainTemp,Double mainFeel,Integer mainHumidity,Integer mainPressure,String time ,Double locLat,Double locLon) {
        this.address = address;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
        this.datalistIcon=datalistIcon;
        this.datalistInfo=datalistInfo;
        this.datalistWeather=datalistWeather;
        this.mainTemp=mainTemp;
        this.mainFeel=mainFeel;
        this.mainHumidity=mainHumidity;
        this.mainPressure=mainPressure;
        this.time=time;
        this.locLat=locLat;
        this.locLon=locLon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

     Double getLocLat() {
        return locLat;
    }

     Double getLocLon() {
        return locLon;
    }

    void setAddress(String address) {
        this.address = address;
    }

    void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    void setWindDegree(Integer windDegree) {
        this.windDegree = windDegree;
    }

    void setDatalistIcon(String datalistIcon) {
        this.datalistIcon = datalistIcon;
    }

    void setDatalistInfo(String datalistInfo) {
        this.datalistInfo = datalistInfo;
    }

    void setDatalistWeather(String datalistWeather) {
        this.datalistWeather = datalistWeather;
    }

    void setMainTemp(Double mainTemp) {
        this.mainTemp = mainTemp;
    }

    void setMainFeel(Double mainFeel) {
        this.mainFeel = mainFeel;
    }

    void setMainHumidity(Integer mainHumidity) {
        this.mainHumidity = mainHumidity;
    }

    void setMainPressure(Integer mainPressure) {
        this.mainPressure = mainPressure;
    }

    void setTime(String time) {
        this.time = time;
    }

    void setLocLat(Double locLat) {
        this.locLat = locLat;
    }

    void setLocLon(Double locLon) {
        this.locLon = locLon;
    }
}
