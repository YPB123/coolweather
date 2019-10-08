package com.example.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * @project: CoolWeather
 * @class name: com.example.coolweather.db
 * @author: 杨鹏波  QQ: 2858912358
 * @time: 2019/10/8 15:43
 * @describe:
 **/
public class Province extends DataSupport {
    private int id;
    private String provinceName;
    private int provinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
