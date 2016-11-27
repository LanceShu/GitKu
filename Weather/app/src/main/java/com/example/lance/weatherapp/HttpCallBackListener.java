package com.example.lance.weatherapp;

import android.os.Handler;

/**
 * Created by Administrator on 2016/10/21 0021.
 */

public interface HttpCallBackListener {
    public void onFinish(WeatherInfo[] weatherInfos, Handler handler);

    public void onError(Exception e);

}
