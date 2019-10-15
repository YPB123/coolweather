package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @project: CoolWeather
 * @class name: com.example.coolweather.gson
 * @author: 杨鹏波  QQ: 2858912358
 * @time: 2019/10/15 10:16
 * @describe:
 **/
public class LifestyleBean {
    /**
     * type : comf
     * brf : 较舒适
     * txt : 白天天气晴好，早晚会感觉偏凉，午后舒适、宜人。
     */

    @SerializedName("type")
    private String type;
    @SerializedName("brf")
    private String brf;
    @SerializedName("txt")
    private String txt;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrf() {
        return brf;
    }

    public void setBrf(String brf) {
        this.brf = brf;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
