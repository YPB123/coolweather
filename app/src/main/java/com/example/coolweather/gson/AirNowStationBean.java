package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @project: CoolWeather
 * @class name: com.example.coolweather.gson
 * @author: 杨鹏波  QQ: 2858912358
 * @time: 2019/10/15 17:14
 * @describe:
 **/
class AirNowStationBean {
    /**
     * air_sta : 万寿西宫
     * aqi : 34
     * asid : CNA1001
     * co : 0.4
     * lat : 39.8673
     * lon : 116.366
     * main : -
     * no2 : 27
     * o3 : 51
     * pm10 : 34
     * pm25 : 10
     * pub_time : 2019-10-15 16:00
     * qlty : 优
     * so2 : 1
     */

    @SerializedName("air_sta")
    private String airSta;
    @SerializedName("aqi")
    private String aqi;
    @SerializedName("asid")
    private String asid;
    @SerializedName("co")
    private String co;
    @SerializedName("lat")
    private String lat;
    @SerializedName("lon")
    private String lon;
    @SerializedName("main")
    private String main;
    @SerializedName("no2")
    private String no2;
    @SerializedName("o3")
    private String o3;
    @SerializedName("pm10")
    private String pm10;
    @SerializedName("pm25")
    private String pm25;
    @SerializedName("pub_time")
    private String pubTime;
    @SerializedName("qlty")
    private String qlty;
    @SerializedName("so2")
    private String so2;

    public String getAirSta() {
        return airSta;
    }

    public void setAirSta(String airSta) {
        this.airSta = airSta;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getAsid() {
        return asid;
    }

    public void setAsid(String asid) {
        this.asid = asid;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getQlty() {
        return qlty;
    }

    public void setQlty(String qlty) {
        this.qlty = qlty;
    }

    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2;
    }
}
