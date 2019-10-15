package com.example.coolweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.coolweather.config.StringKey;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences(StringKey.NAME_OBJECT, MODE_PRIVATE);
        String weatherString = preferences.getString(StringKey.KEY_WEATHER_NOW, null);
        String forecastString = preferences.getString(StringKey.KEY_WEATHER_FORECAST, null);
        String lifestyleString = preferences.getString(StringKey.KEY_WEATHER_LIFESTYLE, null);
        if (weatherString != null && forecastString != null && lifestyleString != null) {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
