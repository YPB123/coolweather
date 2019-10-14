package com.example.coolweather;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.coolweather.config.StringKey;
import com.example.coolweather.gson.Forecast;
import com.example.coolweather.gson.Weather;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private static final String TAG = "WeatherActivity";
    private TextView mTitleCityTv;
    private TextView mTitleUpdateTimeTv;
    private TextView mDegreeTv;
    private TextView mWeatherInfoTv;
    private LinearLayout mForecastLl;
    private TextView mAqiTv;
    private TextView mPm25Tv;
    private TextView mComfortTv;
    private TextView mCarWashTv;
    private TextView mSportTv;
    private ScrollView mWeatherSv;
    private ImageView mBgBingIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int flag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            getWindow().getDecorView().setSystemUiVisibility(flag);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        mTitleCityTv = findViewById(R.id.tv_title_city);
        mTitleUpdateTimeTv = findViewById(R.id.tv_title_update_time);
        mDegreeTv = findViewById(R.id.tv_degree);
        mWeatherInfoTv = findViewById(R.id.tv_weather_info);
        mForecastLl = findViewById(R.id.ll_forecast);
        mAqiTv = findViewById(R.id.tv_aqi);
        mPm25Tv = findViewById(R.id.tv_pm25);
        mComfortTv = findViewById(R.id.tv_comfort);
        mCarWashTv = findViewById(R.id.tv_car_wash);
        mSportTv = findViewById(R.id.tv_sport);
        mWeatherSv = findViewById(R.id.sv_weather);
        mBgBingIv = findViewById(R.id.iv_bg_bing);

        SharedPreferences prefs = getSharedPreferences("com.example.coolweather", MODE_PRIVATE);
        String weatherString = prefs.getString(StringKey.KEY_WEATHER, null);
        if (weatherString != null) {
            //有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            showWeatherInfo(weather);
        } else {
            //无缓存时去服务器查询天气
            String weatherId = getIntent().getStringExtra("weather_id");
            mWeatherSv.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }
        String bingPic = prefs.getString(StringKey.KEY_PING, null);
        if (bingPic != null) {
            Glide.with(this).load(bingPic).into(mBgBingIv);
        } else {
            loadBingPic();
        }

    }

    /**
     * 根据天气id请求城市天气信息
     */
    public void requestWeather(final String weatherId) {

        String weatherUrl = "http://guolin.tech/api/weather?cityid=" +
                weatherId + "&key=5303909e97934cd39112e57b66aecd58";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "http "+ e.getMessage());
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                Log.d(StringKey.KEY_HTTP, "onResponse: "+ responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && StringKey.STATUS_OK.equals(weather.status)) {
                            SharedPreferences.Editor editor = getSharedPreferences(StringKey.NAME_OBJECT, MODE_PRIVATE).edit();
                            editor.putString(StringKey.KEY_WEATHER, responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        loadBingPic();
    }

    /**
     * 处理并展示Weather实体类中的数据
     */
    private void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;

        mTitleCityTv.setText(cityName);
        mTitleUpdateTimeTv.setText(updateTime);
        mDegreeTv.setText(degree);
        mWeatherInfoTv.setText(weatherInfo);
        mForecastLl.removeAllViews();

        for (Forecast forecast : weather.forecastsList) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_forecast, mForecastLl, false);
            TextView dateText = view.findViewById(R.id.tv_date);
            TextView infoText = view.findViewById(R.id.tv_info);
            TextView maxText = view.findViewById(R.id.tv_max);
            TextView minText = view.findViewById(R.id.tv_min);

            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.max);
            mForecastLl.addView(view);
        }
        if (weather.aqi != null) {
            mAqiTv.setText(weather.aqi.city.aqi);
            mPm25Tv.setText(weather.aqi.city.pm25);
        }
        String comfort = "舒适度：" + weather.suggestion.comfort.info;
        String carWash = "洗车指数" + weather.suggestion.carWash.info;
        String sport = "运动建议" + weather.suggestion.sport.info;
        mComfortTv.setText(comfort);
        mSportTv.setText(sport);
        mCarWashTv.setText(carWash);
        mWeatherSv.setVisibility(View.VISIBLE);
    }

    /**
     * 加载必应每日一张图
     */
    private void loadBingPic() {
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(mBgBingIv);
                    }
                });
            }
        });
    }
}
