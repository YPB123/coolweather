package com.example.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * @project: CoolWeather
 * @class name: com.example.coolweather.db
 * @author: 杨鹏波  QQ: 2858912358
 * @time: 2019/10/8 15:54
 * @describe:
 **/
public class Country extends DataSupport {
    private int id;

    private String countyName;

    private String weatherId;

    private int cityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
