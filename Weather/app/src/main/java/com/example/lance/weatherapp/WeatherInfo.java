package com.example.lance.weatherapp;

import java.util.Date;

/**
 * Created by Administrator on 2016/10/17 0017.
 */

public class WeatherInfo {
    private String days;//日期
    private String week;//星期
    private String citynm;//城市名字
    private String temperature;//温度
    private String cityid;//气象编号
    private String humidity;//湿度
    private String weather;//天气
    private String weatherIcon;  //天气图标
    private String weatherIcon1;  //天气图标1
    private String wind;  //风向
    private String winp;   //风力
    private int temp_high; //最高温度
    private int temp_low;  //最低温度
    private int humi_high; //最高湿度
    private int humi_low; //最低湿度
    private int weatid; //天气id
    private int weatid1;
    private int winpid;  //风力id

    public void setWeatid1(int weatid1)
    {
        this.weatid1 = weatid1;
    }
    public int getWeatid1()
    {
        return this.weatid1;
    }
    public void setDays(String days)
    {
        this.days =days;
    }
    public String getDays()
    {
        return this.days;
    }
    public void setWeek(String week)
    {
        this.week = week;
    }
    public String getWeek()
    {
        return this.week;
    }
    public void setCitynm(String citynm)
    {
        this.citynm = citynm;
    }
    public String getCitynm()
    {
        return this.citynm;
    }
    public void setTemperature(String temperature)
    {
        this.temperature = temperature;
    }
    public String getTemperature()
    {
        return this.temperature;
    }
    public void setCityid(String cityid)
    {
        this.cityid = cityid;
    }
    public String getCityid()
    {
        return this.cityid;
    }
    public void setHumidity(String humidity)
    {
        this.humidity = humidity;
    }
    public String getHumidity()
    {
        return this.humidity;
    }
    public void setWeather(String weather)
    {
        this.weather = weather;
    }
    public String getWeather()
    {
        return this.weather;
    }
    public void setWeatherIcon(String weatherIcon)
    {
        this.weatherIcon = weatherIcon;
    }
    public String getWeatherIcon()
    {
        return this.weatherIcon;
    }
    public void setWeatherIcon1(String weatherIcon1)
    {
        this.weatherIcon1 = weatherIcon1;
    }
    public String getWeatherIcon1()
    {
        return this.weatherIcon1;
    }
    public void setWind(String wind)
    {
        this.wind = wind;
    }
    public String getWind()
    {
        return this.wind;
    }
    public void setWinp(String winp)
    {
        this.winp = winp;
    }
    public String getWinp()
    {
        return this.winp;
    }
    public void setTemp_high(int temp_high)
    {
        this.temp_high = temp_high;
    }
    public int getTemp_high()
    {
        return this.temp_high;
    }
    public void setTemp_low(int temp_low)
    {
        this.temp_low = temp_low;
    }
    public int getTemp_low()
    {
        return this.temp_low;
    }
    public void setHumi_high(int humi_high)
    {
        this.humi_high = humi_high;
    }
    public int getHumi_high()
    {
        return this.humi_high;
    }
    public void setHumi_low(int humi_low)
    {
        this.humi_low = humi_low;
    }
    public int getHumi_low()
    {
        return this.humi_low;
    }
    public void setWeatid(int weatid)
    {
        this.weatid = weatid;
    }
    public int getWeatid()
    {
        return this.weatid;
    }
    public void setWinpid(int winpid)
    {
        this.winpid = winpid;
    }
    public int getWinpid()
    {
        return this.winpid;
    }
}
