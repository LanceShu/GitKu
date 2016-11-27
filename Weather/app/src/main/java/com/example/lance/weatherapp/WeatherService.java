package com.example.lance.weatherapp;

import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import static com.example.lance.weatherapp.Content.*;

/**
 * Created by Lance on 2016/10/24.
 */
public class WeatherService extends Service {

    private Handler handler = new Handler();

    private String citynm = "西安";

    private List<String> citylist;
//    private String[] citylist2;

    public void onCreate(){
        super.onCreate();
        citylist = new ArrayList<>();
//        citylist.add(citynm);
        WeatehrServiceReciver reciver = new WeatehrServiceReciver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Content.SERVICE);
        registerReceiver(reciver,filter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void ServiceSendToMainActicity(int weatid,String citynm,String weather,String temp,int t){
        Intent intent = new Intent();
        intent.setAction(Content.MAINACTIVITY);
        intent.putExtra("weatid", weatid);
        intent.putExtra("citynm", citynm);
        intent.putExtra("weather", weather);
        intent.putExtra("temp",temp);
        intent.putExtra("t",t);
        sendBroadcast(intent);
    }

    public void sendBroad(String city){
        Intent intent = new Intent();
        intent.setAction(Content.MAINACTIVITY);
        intent.putExtra("citynm", city);
        sendBroadcast(intent);
    }

    class WeatehrServiceReciver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, final Intent intent) {
            Log.e("大家好,","我是SErvice");

            int control = intent.getIntExtra("control", -1);
            switch (control){
                case Content.search:
                    citynm = intent.getStringExtra("citynm");
                    citylist.add(citynm);
                    sendBroad(citynm);
//                    HttpUtil.sendHttpRequest(citynm+"", handler, new HttpCallBackListener() {
//                        @Override
//                        public void onFinish(WeatherInfo[] weatherInfos, Handler handler) {
//                            if (weatherInfos == null) {
//                                Intent intent1 = new Intent(Content.MAINACTIVITY);
//                                intent1.putExtra("citynm","");
//                                sendBroadcast(intent1);
//
//                            } else {
//                                ServiceSendToMainActicity(weatherInfos[0].getWeatid(),weatherInfos[0].getCitynm(),weatherInfos[0].getWeather(),weatherInfos[0].getTemperature(),weatherInfos[0].getTemp_high());
//                            }
//                        }
//                        @Override
//                        public void onError(Exception e) {
//                            e.printStackTrace();
//                        }
//                    });
                    break;
                case Content.scroll:
                    citynm = intent.getStringExtra("citynm");
                    sendBroad(citynm);
                    break;
                case Content.renew:
                    Log.e("44444444444",citynm);
                    cityl = citylist;
                    sendBroad(citynm);
//                    HttpUtil.sendHttpRequest(citynm, handler, new HttpCallBackListener() {
//                        @Override
//                        public void onFinish(WeatherInfo[] weatherInfos, Handler handler) {
//                            if (weatherInfos == null) {
//                                Intent intent1 = new Intent(Content.MAINACTIVITY);
//                                intent1.putExtra("citynm","");
//                                sendBroadcast(intent1);
//                            } else {
//                                Intent intent1 = new Intent(Content.MAINACTIVITY);
//                                intent1.putExtra("citynm",citynm);
//                                sendBroadcast(intent1);
//                            }
//                        }
//                        @Override
//                        public void onError(Exception e) {
//                            e.printStackTrace();
//                        }
//                    });
                    break;
                default:
                    break;
            }
        }
    }
}
