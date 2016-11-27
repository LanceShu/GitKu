package com.example.lance.weatherapp;

/**
 * Created by Lance on 2016/11/27.
 */
public interface GPSHttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
