package com.example.lance.weatherapp;

/**
 * Created by Administrator on 2016/10/22 0022.
 */

public class CityInfo {
    private String cityid; //城市气象编号票
    private String citynm;//城市名
    private String cityno;//城市拼音
    private int weait;//城市id
    public void setCityid(String cityid)
    {
        this.cityid = cityid;
    }
    public String getCityid()
    {
        return this.cityid;
    }
    public void setCitynm(String citynm)
    {
        this.citynm = citynm;
    }
    public String getCitynm()
    {
        return this.citynm;
    }
    public void setCityno(String cityno)
    {
        this.cityno = cityno;
    }
    public String getCityno()
    {
        return this.cityno;
    }
    public void setWeait(int weait)
    {
        this.weait = weait;
    }
    public int getWeait()
    {
        return this.weait;
    }
}
