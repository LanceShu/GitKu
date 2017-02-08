package com.example.lance.xiyou_score;

import android.content.Context;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lance on 2017/1/24.
 */
public class Content {

    static String course_content = null;
    static String repsone_content = null;
    static String cookies;
    static RequestQueue mqueue;
    static String loginName;
    static String loginPassword;
    static String loginCheckCode;
    static String student_name;
    static Context context;
    static List<String> courseList = new ArrayList<>();

    static String stuname;
    static String stuid;
    static String stuacademy;
    static String stumajor;
    static String stuclass;
    static String stueducation;

    static List<Project_Infor> project_list = new ArrayList<>();
    static List<Score_Infor> score_inforList = new ArrayList<>();
}
