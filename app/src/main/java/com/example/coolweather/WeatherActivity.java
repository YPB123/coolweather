package com.example.coolweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.coolweather.config.StringKey;
import com.example.coolweather.gson.DailyForecastBean;
import com.example.coolweather.gson.HeWeather6Bean;
import com.example.coolweather.service.AutoUpdateService;
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
    public SwipeRefreshLayout mSwipeRefreshLayout;
    private String mWeatherId;
    public DrawerLayout mDrawerLayout;
    private Button mNavBt;

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
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout_weather);
        mDrawerLayout = findViewById(R.id.drawer_parent);
        mNavBt = findViewById(R.id.bt_nav);

        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary));
        SharedPreferences prefs = getSharedPreferences("com.example.coolweather", MODE_PRIVATE);
        String weatherString = prefs.getString(StringKey.KEY_WEATHER_NOW, null);
        String forecastString = prefs.getString(StringKey.KEY_WEATHER_FORECAST, null);
        String lifestyleString = prefs.getString(StringKey.KEY_WEATHER_LIFESTYLE, null);
        String aqiString = prefs.getString(StringKey.KEY_AQI, null);
        if (weatherString != null && forecastString != null && lifestyleString != null) {
            //有缓存时直接解析天气数据
            HeWeather6Bean weather = Utility.handleWeatherResponse(weatherString);
            HeWeather6Bean forecast = Utility.handleWeatherResponse(forecastString);
            HeWeather6Bean lifestyle = Utility.handleWeatherResponse(lifestyleString);
            HeWeather6Bean aqi = Utility.handleWeatherResponse(aqiString);
            mWeatherId = weather.getBasic().getCid();
            showWeatherInfo(weather);
            showWeatherInfo(forecast);
            showWeatherInfo(aqi);
            showWeatherInfo(lifestyle);
        } else {
            //无缓存时去服务器查询天气
            mWeatherId = getIntent().getStringExtra("weather_id");
            mWeatherSv.setVisibility(View.INVISIBLE);
            requestWeather(mWeatherId);
        }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(mWeatherId);
            }
        });
        String bingPic = prefs.getString(StringKey.KEY_PING, null);
        if (bingPic != null) {
            Glide.with(this).load(bingPic).into(mBgBingIv);
        } else {
            loadBingPic();
        }

        mNavBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    /**
     * 根据天气id请求城市天气信息
     */
    public void requestWeather(final String weatherId) {
        nowRequest(weatherId);
        forecastRequest(weatherId);
        aqiRequest(weatherId);
        lifestyleRequest(weatherId);
        loadBingPic();
    }

    /**
     * 请求实况天气信息
     */
    private void nowRequest(final String weatherId) {

        String nowWeatherUrl = StringKey.WEATHER_URL + "now?location="+ weatherId+"&key="+StringKey.KEY;

        HttpUtil.sendOkHttpRequest(nowWeatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "http "+ e.getMessage());
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final HeWeather6Bean weather = Utility.handleWeatherResponse(responseText);
                Log.d(StringKey.KEY_HTTP, "onResponse: "+ responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && StringKey.STATUS_OK.equals(weather.getStatus())) {
                            SharedPreferences.Editor editor = getSharedPreferences(StringKey.NAME_OBJECT, MODE_PRIVATE).edit();
                            editor.putString(StringKey.KEY_WEATHER_NOW, responseText);
                            editor.apply();
                            mWeatherId = weather.getBasic().getCid();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    /**
     *请求天气预报
     */
    private void forecastRequest(final String weatherId) {

        String forecastUrl = StringKey.WEATHER_URL + "forecast?location="+ weatherId+"&key="+StringKey.KEY;

        HttpUtil.sendOkHttpRequest(forecastUrl, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "http "+ e.getMessage());
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final HeWeather6Bean weather = Utility.handleWeatherResponse(responseText);
                Log.d(StringKey.KEY_HTTP, "onResponse: "+ responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && StringKey.STATUS_OK.equals(weather.getStatus())) {
                            SharedPreferences.Editor editor = getSharedPreferences(StringKey.NAME_OBJECT, MODE_PRIVATE).edit();
                            editor.putString(StringKey.KEY_WEATHER_FORECAST, responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    /**
     * 请求空气状况
     */
    private void aqiRequest(final String weatherId) {
        String aqiWeatherUrl = StringKey.AQI_URL + "now?location="+ weatherId+"&key="+StringKey.KEY;

        HttpUtil.sendOkHttpRequest(aqiWeatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "http "+ e.getMessage());
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final HeWeather6Bean weather = Utility.handleWeatherResponse(responseText);
                Log.d(StringKey.KEY_HTTP, "onResponse: "+ responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && StringKey.STATUS_OK.equals(weather.getStatus())) {
                            SharedPreferences.Editor editor = getSharedPreferences(StringKey.NAME_OBJECT, MODE_PRIVATE).edit();
                            editor.putString(StringKey.KEY_AQI, responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    /**
     * 请求生活指数
     */
    private void lifestyleRequest(final String weatherId) {

        String lifestyleWeatherUrl = StringKey.WEATHER_URL + "lifestyle?location="+ weatherId+"&key="+StringKey.KEY;

        HttpUtil.sendOkHttpRequest(lifestyleWeatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "http "+ e.getMessage());
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final HeWeather6Bean weather = Utility.handleWeatherResponse(responseText);
                Log.d(StringKey.KEY_HTTP, "onResponse: "+ responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && StringKey.STATUS_OK.equals(weather.getStatus())) {
                            SharedPreferences.Editor editor = getSharedPreferences(StringKey.NAME_OBJECT, MODE_PRIVATE).edit();
                            editor.putString(StringKey.KEY_WEATHER_LIFESTYLE, responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    /**
     * 处理并展示Weather实体类中的数据
     */
    private void showWeatherInfo(HeWeather6Bean weather) {
        String cityName = weather.getBasic().getLocation();
        String updateTime = weather.getUpdate().getLoc();
        mTitleCityTv.setText(cityName);
        mTitleUpdateTimeTv.setText(updateTime);

        if (weather.getNow() != null) {
            String degree = weather.getNow().getTmp() + "℃";
            String weatherInfo = weather.getNow().getCondTxt();
            mDegreeTv.setText(degree);
            mWeatherInfoTv.setText(weatherInfo);
        }

        if (weather.getDailyForecast() != null) {
            mForecastLl.removeAllViews();
            for (DailyForecastBean forecast : weather.getDailyForecast()) {
                View view = LayoutInflater.from(this).inflate(R.layout.item_forecast, mForecastLl, false);
                TextView dateText = view.findViewById(R.id.tv_date);
                TextView infoText = view.findViewById(R.id.tv_info);
                TextView maxText = view.findViewById(R.id.tv_max);
                TextView minText = view.findViewById(R.id.tv_min);

                dateText.setText(forecast.getDate());
                infoText.setText(forecast.getCondTxtD());
                maxText.setText(forecast.getTmpMax());
                minText.setText(forecast.getTmpMin());
                mForecastLl.addView(view);
            }
        }

        if (weather.getAirNowCity() != null) {
            mAqiTv.setText(weather.getAirNowCity().getAqi());
            mPm25Tv.setText(weather.getAirNowCity().getPm25());
        }

        if (weather.getLifestyle() != null) {
            String comfort = "舒适度：" + weather.getLifestyle().get(0).getTxt();
            String carWash = "穿衣推荐：" + weather.getLifestyle().get(1).getTxt();
            String sport = "运动建议："  + weather.getLifestyle().get(3).getTxt();
            mComfortTv.setText(comfort);
            mSportTv.setText(sport);
            mCarWashTv.setText(carWash);
        }

        mWeatherSv.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
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
