package com.example.lance.weatherapp;

import android.os.Handler;

/**
 * Created by Administrator on 2016/10/22 0022.
 */

public interface HttpCityCallBack {
    public void onFinish(String[] cityInfos, Handler handler);
    public void onError(Exception e);
}
