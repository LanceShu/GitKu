package com.example.lance.weatherapp;

import android.os.Handler;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/10/22 0022.
 */

public class HttpCity {
    //http://api.k780.com:88/?app=weather.city&appkey=你申请的AppKey&sign=你申请的Sign&format=json
    private static String innerRequest = "http://api.k780.com:88/?app=weather.city&cou=1&appkey=21288&sign=26292f335d20835590c01d489b9a3358&format=json";
    private static String outerRequest = "http://api.k780.com:88/?app=weather.city&cou=2&appkey=21288&sign=26292f335d20835590c01d489b9a3358&format=json";
    private static String allRequest   = "http://api.k780.com:88/?app=weather.city&&appkey=21288&sign=26292f335d20835590c01d489b9a3358&format=json";
    public static void cityListRequest(final int choice, final Handler handler, final HttpCityCallBack listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try{
                    URL url = null;
                    if(choice==0)
                    {
                        url = new URL(allRequest);
                    }
                    else if(choice==1)
                    {
                        url = new URL(innerRequest);
                    }
                    else if(choice==2)
                        url = new URL(outerRequest);

                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line=reader.readLine())!=null)
                    {
                        response.append(line);
                    }
                    Log.e("city",response.toString());
                    String[] cityInfos = parseJsonWithJsonObject(response.toString());
                    if(listener!=null)
                    {
                        listener.onFinish(cityInfos,handler);
                    }
                }catch (Exception e)
                {
                    if(listener!=null)
                    {
                        listener.onError(e);
                    }
                }
                finally {
                    if(connection!=null)
                        connection.disconnect();
                }
            }
        }).start();
    }

    private static String[] parseJsonWithJsonObject(String jsondata)
    {
        Log.e("flag","  flag1");
        try{
            JSONObject jsonObject = new JSONObject(jsondata);
            JSONObject getdata = jsonObject.getJSONObject("result");
            String[] city = new String[getdata.length()];
            for(int i = 0,j = 1; i < getdata.length();i++,j++)
            {
                if(getdata.isNull(Integer.toString(j)))
                {
                    i--;
                    continue;
                }
                JSONObject job = getdata.getJSONObject(Integer.toString(j));
                String s = job.getString("citynm");
                city[i] = s;
            }
            return city;
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
