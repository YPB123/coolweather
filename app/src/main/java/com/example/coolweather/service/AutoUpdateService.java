package com.example.coolweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.example.coolweather.config.StringKey;
import com.example.coolweather.gson.Weather;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @project: CoolWeather
 * @class name: com.example.coolweather.service
 * @author: 杨鹏波  QQ: 2858912358
 * @time: 2019/10/14 16:06
 * @describe:
 **/
public class AutoUpdateService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        updateBingPic();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //这是8小时毫秒数
        int anHour = 8 * 60 * 60 * 1000;
        Long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 更新天气
     */
    private void updateWeather() {
        SharedPreferences prefs = getSharedPreferences(StringKey.NAME_OBJECT, MODE_PRIVATE);
        String weatherString = prefs.getString(StringKey.KEY_WEATHER, null);
        if (weatherString != null) {
            //有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            String weatherId = weather.basic.weatherId;

            String weatherUrl = "http://guolin.tech/api/weather?city="+weatherId ;
            HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(StringKey.KEY_HTTP, "onFailure: "+ e.getMessage());
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responeText = response.body().string();
                    Weather weather = Utility.handleWeatherResponse(responeText);
                    if (weather != null && StringKey.STATUS_OK.equals(weather.status)) {
                        SharedPreferences.Editor editor = getSharedPreferences(StringKey.NAME_OBJECT, MODE_PRIVATE).edit();
                        editor.putString(StringKey.KEY_WEATHER, responeText);
                        editor.apply();
                    }
                }
            });
        }
    }

    /**
     * 更新必应每日一图
     */
    private void updateBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = getSharedPreferences(StringKey.NAME_OBJECT, MODE_PRIVATE).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
            }
        });
    }
}
