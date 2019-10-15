package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @project: CoolWeather
 * @class name: com.example.coolweather.gson
 * @author: 杨鹏波  QQ: 2858912358
 * @time: 2019/10/15 9:52
 * @describe:
 **/
public class UpdateBean {
    /**
     * loc : 2019-10-14 22:58
     * utc : 2019-10-14 14:58
     */

    @SerializedName("loc")
    private String loc;
    @SerializedName("utc")
    private String utc;

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getUtc() {
        return utc;
    }

    public void setUtc(String utc) {
        this.utc = utc;
    }
}
