package com.example.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * @project: CoolWeather
 * @class name: com.example.coolweather.db
 * @author: 杨鹏波  QQ: 2858912358
 * @time: 2019/10/8 15:52
 * @describe:
 **/
public class City extends DataSupport {

    private int id;

    private String cityName;

    private int cityCode;

    private int province;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }
}
