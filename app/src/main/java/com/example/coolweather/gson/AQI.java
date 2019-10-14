package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @project: CoolWeather
 * @class name: com.example.coolweather.gson
 * @author: 杨鹏波  QQ: 2858912358
 * @time: 2019/10/11 11:31
 * @describe:
 **/
public class AQI {

    public AQICity city;

    public class AQICity{

        public String aqi;
        public String pm25;
    }
}
