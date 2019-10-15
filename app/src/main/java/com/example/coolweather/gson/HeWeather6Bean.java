package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @project: CoolWeather
 * @class name: com.example.coolweather.gson
 * @author: 杨鹏波  QQ: 2858912358
 * @time: 2019/10/15 9:52
 * @describe:
 **/
public class HeWeather6Bean {
    /**
     * basic : {"cid":"CN101010100","location":"北京","parent_city":"北京","admin_area":"北京","cnty":"中国","lat":"39.90498734","lon":"116.4052887","tz":"+8.00"}
     * update : {"loc":"2019-10-14 22:58","utc":"2019-10-14 14:58"}
     * status : ok
     * daily_forecast : [{"cond_code_d":"100","cond_code_n":"100","cond_txt_d":"晴","cond_txt_n":"晴","date":"2019-10-14","hum":"22","mr":"18:11","ms":"06:21","pcpn":"0.0","pop":"0","pres":"1029","sr":"06:24","ss":"17:35","tmp_max":"15","tmp_min":"3","uv_index":"6","vis":"25","wind_deg":"247","wind_dir":"西南风","wind_sc":"1-2","wind_spd":"2"},{"cond_code_d":"100","cond_code_n":"101","cond_txt_d":"晴","cond_txt_n":"多云","date":"2019-10-15","hum":"23","mr":"18:38","ms":"07:20","pcpn":"0.0","pop":"0","pres":"1022","sr":"06:25","ss":"17:33","tmp_max":"17","tmp_min":"5","uv_index":"5","vis":"25","wind_deg":"196","wind_dir":"西南风","wind_sc":"1-2","wind_spd":"10"},{"cond_code_d":"101","cond_code_n":"305","cond_txt_d":"多云","cond_txt_n":"小雨","date":"2019-10-16","hum":"59","mr":"19:08","ms":"08:20","pcpn":"0.0","pop":"21","pres":"1019","sr":"06:26","ss":"17:32","tmp_max":"15","tmp_min":"8","uv_index":"1","vis":"25","wind_deg":"187","wind_dir":"南风","wind_sc":"1-2","wind_spd":"10"}]
     */

    @SerializedName("basic")
    private BasicBean basic;
    @SerializedName("update")
    private UpdateBean update;
    @SerializedName("status")
    private String status;
    @SerializedName("daily_forecast")
    private List<DailyForecastBean> dailyForecast;
    @SerializedName("lifestyle")
    private List<LifestyleBean> lifestyle;
    @SerializedName("now")
    private NowBean now;
    /**
     * air_now_city : {"aqi":"33","qlty":"优","main":"-","pm25":"14","pm10":"33","no2":"29","so2":"3","co":"0.4","o3":"50","pub_time":"2019-10-15 16:00"}
     * air_now_station : [{"air_sta":"万寿西宫","aqi":"34","asid":"CNA1001","co":"0.4","lat":"39.8673","lon":"116.366","main":"-","no2":"27","o3":"51","pm10":"34","pm25":"10","pub_time":"2019-10-15 16:00","qlty":"优","so2":"1"},{"air_sta":"定陵","aqi":"32","asid":"CNA1002","co":"0.3","lat":"40.2865","lon":"116.17","main":"-","no2":"28","o3":"45","pm10":"32","pm25":"12","pub_time":"2019-10-15 16:00","qlty":"优","so2":"2"},{"air_sta":"东四","aqi":"44","asid":"CNA1003","co":"0.3","lat":"39.9522","lon":"116.434","main":"-","no2":"29","o3":"43","pm10":"44","pm25":"16","pub_time":"2019-10-15 16:00","qlty":"优","so2":"2"},{"air_sta":"天坛","aqi":"36","asid":"CNA1004","co":"0.3","lat":"39.8745","lon":"116.434","main":"-","no2":"27","o3":"52","pm10":"36","pm25":"17","pub_time":"2019-10-15 16:00","qlty":"优","so2":"2"},{"air_sta":"农展馆","aqi":"34","asid":"CNA1005","co":"0.3","lat":"39.9716","lon":"116.473","main":"-","no2":"31","o3":"53","pm10":"34","pm25":"16","pub_time":"2019-10-15 16:00","qlty":"优","so2":"2"},{"air_sta":"官园","aqi":"31","asid":"CNA1006","co":"0.2","lat":"39.9425","lon":"116.361","main":"-","no2":"28","o3":"53","pm10":"31","pm25":"15","pub_time":"2019-10-15 16:00","qlty":"优","so2":"3"},{"air_sta":"海淀区万柳","aqi":"18","asid":"CNA1007","co":"0.4","lat":"39.9934","lon":"116.315","main":"-","no2":"36","o3":"47","pm10":"0","pm25":"0","pub_time":"2019-10-15 16:00","qlty":"优","so2":"2"},{"air_sta":"顺义新城","aqi":"18","asid":"CNA1008","co":"0.3","lat":"40.1438","lon":"116.72","main":"-","no2":"30","o3":"57","pm10":"0","pm25":"0","pub_time":"2019-10-15 16:00","qlty":"优","so2":"3"},{"air_sta":"怀柔镇","aqi":"21","asid":"CNA1009","co":"0.2","lat":"40.3937","lon":"116.644","main":"-","no2":"15","o3":"64","pm10":"21","pm25":"5","pub_time":"2019-10-15 16:00","qlty":"优","so2":"2"},{"air_sta":"昌平镇","aqi":"34","asid":"CNA1010","co":"0.4","lat":"40.1952","lon":"116.23","main":"-","no2":"28","o3":"46","pm10":"34","pm25":"12","pub_time":"2019-10-15 16:00","qlty":"优","so2":"4"},{"air_sta":"奥体中心","aqi":"20","asid":"CNA1011","co":"0.4","lat":"40.0031","lon":"116.407","main":"-","no2":"40","o3":"29","pm10":"0","pm25":"0","pub_time":"2019-10-15 16:00","qlty":"优","so2":"1"},{"air_sta":"古城","aqi":"32","asid":"CNA1012","co":"0.3","lat":"39.9279","lon":"116.225","main":"-","no2":"24","o3":"60","pm10":"31","pm25":"22","pub_time":"2019-10-15 16:00","qlty":"优","so2":"1"}]
     */

    @SerializedName("air_now_city")
    private AirNowCityBean airNowCity;
    @SerializedName("air_now_station")
    private List<AirNowStationBean> airNowStation;


    public NowBean getNow() {
        return now;
    }

    public void setNow(NowBean now) {
        this.now = now;
    }

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public UpdateBean getUpdate() {
        return update;
    }

    public void setUpdate(UpdateBean update) {
        this.update = update;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DailyForecastBean> getDailyForecast() {
        return dailyForecast;
    }

    public void setDailyForecast(List<DailyForecastBean> dailyForecast) {
        this.dailyForecast = dailyForecast;
    }

    public List<LifestyleBean> getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(List<LifestyleBean> lifestyle) {
        this.lifestyle = lifestyle;
    }

    public AirNowCityBean getAirNowCity() {
        return airNowCity;
    }

    public void setAirNowCity(AirNowCityBean airNowCity) {
        this.airNowCity = airNowCity;
    }

    public List<AirNowStationBean> getAirNowStation() {
        return airNowStation;
    }

    public void setAirNowStation(List<AirNowStationBean> airNowStation) {
        this.airNowStation = airNowStation;
    }
}
