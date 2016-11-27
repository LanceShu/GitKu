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
 * Created by Administrator on 2016/10/17 0017.
 */

public class HttpUtil{
    private static final String appKey = "21288";
    private static final String appSign = "26292f335d20835590c01d489b9a3358";
    private static final String part1 = "http://api.k780.com:88/?app=weather.future&weaid=";
    private static final String part2 = "&appkey="+appKey+"&sign="+appSign+"&format=json";
    //private static final String jsonRequest = "http://api.k780.com:88/?app=weather.future&weaid=1&appkey="+appKey+"&sign="+appSign+"&format=json";
    //http://api.k780.com:88/?app=weather.future&weaid=1&appkey=你申请的AppKey&sign=你申请的Sign&format=json
    public static void sendHttpRequest(final String weaId, final Handler handler, final HttpCallBackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("ThreadStart","Start");
                    String jsonRequest = part1+weaId+part2;
                    HttpURLConnection connection = null;

                try{
                    URL url = new URL(jsonRequest);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = reader.readLine())!=null)
                    {
                        response.append(line);
                    }
                    Log.e("Weather",response.toString());
                    Log.e("info","GETString");
                    WeatherInfo[] weatherInfos = parseJSONWithJSONObject(response.toString());
                    if(listener!=null)
                    {
                        listener.onFinish(weatherInfos,handler);
                    }
                }catch (Exception e)
                {
                    if(listener!=null)
                    {
                        listener.onError(e);
                    }
                }
                finally {
                    if(connection != null)
                        connection.disconnect();
                }
            }
        }).start();
    }

    private  static WeatherInfo[] parseJSONWithJSONObject(String jsonData)
    {
        WeatherInfo[] weatherInfos = new WeatherInfo[7];
        JSONObject jsonObject;
        try{
            jsonObject = new JSONObject(jsonData);
            Integer answer = jsonObject.getInt("success");
            Log.e("success",answer.toString());
            if(answer==0)
                return null;
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            if(jsonArray!=null)
            for(int i=0;i<jsonArray.length();i++)
            {
                WeatherInfo weatherInfo = new WeatherInfo();
                weatherInfos[i] = weatherInfo;
                if(jsonArray.getJSONObject(i)==null)
                    System.out.println("JSON  is   null");
                weatherInfos[i].setDays(jsonArray.getJSONObject(i).getString("days"));
                weatherInfos[i].setCityid(jsonArray.getJSONObject(i).optString("cityid"));
                weatherInfos[i].setCitynm(jsonArray.getJSONObject(i).getString("citynm"));
                weatherInfos[i].setHumi_high(jsonArray.getJSONObject(i).getInt("humi_high"));
                weatherInfos[i].setHumi_low(jsonArray.getJSONObject(i).getInt("humi_low"));
                weatherInfos[i].setTemperature(jsonArray.getJSONObject(i).getString("temperature"));
                weatherInfos[i].setTemp_high(jsonArray.getJSONObject(i).getInt("temp_high"));
                weatherInfos[i].setTemp_low(jsonArray.getJSONObject(i).getInt("temp_low"));
                weatherInfos[i].setWind(jsonArray.getJSONObject(i).getString("wind"));
                weatherInfos[i].setWinp(jsonArray.getJSONObject(i).getString("winp"));
                weatherInfos[i].setHumidity(jsonArray.getJSONObject(i).getString("humidity"));
                weatherInfos[i].setWeatid(jsonArray.getJSONObject(i).getInt("weatid"));
                weatherInfos[i].setWeek(jsonArray.getJSONObject(i).getString("week"));
                weatherInfos[i].setWeather(jsonArray.getJSONObject(i).getString("weather"));
                weatherInfos[i].setWeatid(jsonArray.getJSONObject(i).getInt("weatid"));
                //Log.e("test",weatherInfos[i].getWeek());
                //Log.e("weather",jsonArray.getJSONObject(i).getString("weather"));
                //Log.e("weather",jsonArray.getJSONObject(i).getString("humi_high"));
                //Log.e("weather",jsonArray.getJSONObject(i).getString("humi_low"));
            }
            return weatherInfos;
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return weatherInfos;
    }

    public static void sendHttpRequest(String url, GPSHttpCallbackListener gpsHttpCallbackListener) {
    }
}
