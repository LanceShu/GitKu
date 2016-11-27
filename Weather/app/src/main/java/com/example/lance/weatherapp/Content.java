package com.example.lance.weatherapp;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lance on 2016/10/22.
 */
public class Content  {
    public static JSONObject jSONObjectMain;

    public   static final String MAINACTIVITY = "MANCITITY";
    public static final String SERVICE = "SERVICE";

    public static final int search = 1;
    public static final int renew = 2;
    public static final int scroll = 3;
    public static final int address = 4;

    public static int tag = 1;
    public static List<String> cityl = new ArrayList<>();

}
