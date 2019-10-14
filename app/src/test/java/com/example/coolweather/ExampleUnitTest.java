package com.example.coolweather;

import android.util.Log;

import com.example.coolweather.util.HttpUtil;

import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void sendRequest() {
//        HttpURLConnection
        //okhttp3  socket
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://guolin.tech/aqi/china").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("-------> Fail!");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("------->"  +response.body().string());
            }
        });
    }

    @Test
    public void send() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://guolin.tech/aqi/china").build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void newThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("data");
            }
        }).start();
    }
}