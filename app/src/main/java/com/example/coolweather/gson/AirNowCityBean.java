package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @project: CoolWeather
 * @class name: com.example.coolweather.gson
 * @author: 杨鹏波  QQ: 2858912358
 * @time: 2019/10/15 17:14
 * @describe:
 **/
public class AirNowCityBean {
    /**
     * aqi : 33
     * qlty : 优
     * main : -
     * pm25 : 14
     * pm10 : 33
     * no2 : 29
     * so2 : 3
     * co : 0.4
     * o3 : 50
     * pub_time : 2019-10-15 16:00
     */

    @SerializedName("aqi")
    private String aqi;
    @SerializedName("qlty")
    private String qlty;
    @SerializedName("main")
    private String main;
    @SerializedName("pm25")
    private String pm25;
    @SerializedName("pm10")
    private String pm10;
    @SerializedName("no2")
    private String no2;
    @SerializedName("so2")
    private String so2;
    @SerializedName("co")
    private String co;
    @SerializedName("o3")
    private String o3;
    @SerializedName("pub_time")
    private String pubTime;

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getQlty() {
        return qlty;
    }

    public void setQlty(String qlty) {
        this.qlty = qlty;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }
}
