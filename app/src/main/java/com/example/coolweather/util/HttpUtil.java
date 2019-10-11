package com.example.coolweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @project: CoolWeather
 * @class name: com.example.coolweather.util
 * @author: 杨鹏波  QQ: 2858912358
 * @time: 2019/10/8 16:30
 * @describe:
 **/
public class HttpUtil {

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);

    }
}
