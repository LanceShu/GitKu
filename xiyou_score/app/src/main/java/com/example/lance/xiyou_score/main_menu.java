package com.example.lance.xiyou_score;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.example.lance.xiyou_score.Content.*;

/**
 * Created by Lance on 2017/1/24.
 */
public class main_menu extends FragmentActivity implements ViewPager.OnPageChangeListener {

    private List<View> viewList;
    private ViewPager pager;
    private PagerTabStrip tabStrip;
    private List<String> titlelist;
    private List<Fragment> fragmentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        get_CourseTable();
        get_Score();
        get_Project();
        get_personInfor();

        context = main_menu.this;

        viewList = new ArrayList<>();

        View view_course = View.inflate(this,R.layout.view1_course,null);
        View view_score = View.inflate(this,R.layout.view2_score,null);
        View view_project = View.inflate(this,R.layout.view3_project,null);
        View view_Infor = View.inflate(this,R.layout.person_infor,null);

        viewList.add(view_course);
        viewList.add(view_score);
        viewList.add(view_project);
        viewList.add(view_Infor);

        fragmentList = new ArrayList<>();
        fragmentList.add(new Fragement_course());
        fragmentList.add(new Fragement_score());
        fragmentList.add(new Fragement_project());
        fragmentList.add(new Fragement_Infor());

        titlelist = new ArrayList<>();
        titlelist.add("课程表");
        titlelist.add("成绩单");
        titlelist.add("培养计划");
        titlelist.add("个人信息");

        tabStrip = (PagerTabStrip) findViewById(R.id.tab);
        tabStrip.setBackgroundResource(R.color.color1);
        tabStrip.setFocusableInTouchMode(true);
        tabStrip.setTextColor(Color.DKGRAY);
        tabStrip.setDrawFullUnderline(false);
        tabStrip.setTabIndicatorColorResource(R.color.UnderColor);

        pager = (ViewPager) findViewById(R.id.pager);

        FragementPagerAdapter fragementPagerAdapter = new FragementPagerAdapter(getSupportFragmentManager(), fragmentList,titlelist);
        pager.setAdapter(fragementPagerAdapter);
        pager.setOnPageChangeListener(this);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position == 1){
            tabStrip.setBackgroundResource(R.color.color2);
        }else if(position == 2){
            tabStrip.setBackgroundResource(R.color.color3);
        }else if(position == 3){
            tabStrip.setBackgroundResource(R.color.color1);
        }else{
            tabStrip.setBackgroundResource(R.color.color4);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void get_Project() {
        project_list.clear();
        String url = "http://222.24.62.120/pyjh.aspx?xh="+loginName+"&xm="+student_name+"&gnmkdm=N121607";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //repsone_content = s;
                Document document = Jsoup.parse(s);
                Elements tr = document.getElementsByTag("tr");
//                Log.e("tr.size",tr.size()+"");
                for(int i =4;i<tr.size()-25;i++){
                    Elements td = tr.get(i).getElementsByTag("td");
                    String id = td.get(0).text();
                    String name = td.get(1).text();
                    String score = td.get(2).text();
                    String gpa = td.get(3).text();
                    String team = td.get(4).text();
                    String status = td.get(5).text();
                    Project_Infor project_infor = new Project_Infor(id,name,score,gpa,team,status);
                    project_list.add(project_infor);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("failure555555",volleyError+"");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header = new HashMap<>();
                header.put("Cookie",cookies);
                header.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                header.put("Referer","http://222.24.62.120/pyjh.aspx?xh="+loginName+"&xm="+student_name+"&gnmkdm=N121607");
                header.put("Accept-Encoding","gzip, deflate");
                header.put("Accept-Language", "zh-Hans-CN,zh-Hans;q=0.8");
                header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
                return header;
            }

        };
        mqueue.add(stringRequest);

    }

    private void get_CourseTable(){
        final String[] aaa = new String[1];
        String url ="http://222.24.62.120/xskbcx.aspx?xh="+loginName+"&xm="+student_name+"&gnmkdm=N121603";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Document document3 = Jsoup.parse(s);
                Log.e("sucess","66666666666666666666");
                Log.e("content555555555555",document3.select("input[name=__VIEWSTATE]").val());
                aaa[0] = document3.select("input[name=__VIEWSTATE]").val();
                try {
                    aaa[0] = URLEncoder.encode(aaa[0],"GBK");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header = new HashMap<>();
                header.put("Cookie",cookies);
                header.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                header.put("Referer","http://222.24.62.120/xskbcx.aspx?xh="+loginName+"&xm="+student_name+"&gnmkdm=N121603");
                header.put("Accept-Encoding","gzip, deflate");
                header.put("Accept-Language","zh-Hans-CN,zh-Hans;q=0.8");
                header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
                return header;
            }
        };
        mqueue.add(stringRequest);

        StringRequest request = new StringRequest(Request.Method.POST,url , new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                courseList.clear();
                // repsone_content = s;
                Document document = Jsoup.parse(s);
                Elements tr = document.getElementsByTag("tr");
//                Log.e("size1234560",tr.size()+"");
                for(int i = 4;i<tr.size()-16;i=i+2){
                    Elements td = tr.get(i).getElementsByTag("td");
                    if(i==4||i==8||i==12){
                        for(int j = 2;j<td.size()-2;j++){
                            Elements td1 = td.get(j).getElementsByTag("td");


//                            Log.e("content----------"+j,td1.get(0)+"");
//                            Log.e("length", (td1.get(0)+"").length() + "");
                            if((td1.get(0)+"").length()>200){
//                                Log.e("view", (td1.get(0) + "").substring(25, (td1.get(0) + "").length() / 4+30));
                                String a = (td1.get(0) + "").substring(25,(td1.get(0)+"").length());
                                String[] b = a.split(">");
                                String[] c = b[1].split("<");
                                String course_name = c[0];
                                String[] d = b[3].split("<");
                                String course_teacher ="@"+  d[0];
                                String[] e = b[4].split("<");
                                String course_place = e[0];
                                String infor = course_name+"\n"+course_teacher+"\n"+course_place;
                                Log.e("view66666666666666",infor);
                                courseList.add(infor);
                            } else if((td1.get(0)+"").length()>80){
//                                Log.e("view",(td1.get(0)+"").substring(25, (td1.get(0) + "").length() / 2+15));
                                String a = (td1.get(0) + "").substring(25,(td1.get(0)+"").length());
                                String[] b = a.split(">");
                                String[] c = b[1].split("<");
                                String course_name = c[0];
                                String[] d = b[3].split("<");
                                String course_teacher ="@"+ d[0];
                                String[] e = b[4].split("<");
                                String course_place = e[0];
                                String infor = course_name+"\n"+course_teacher+"\n"+course_place;
                                Log.e("view66666666666666",infor);
                                courseList.add(infor);
                            }else if((td1.get(0)+"").length() > 50){
//                                Log.e("view",(td1.get(0) + "").substring(25,(td1.get(0)+"").length()));
                                String a = (td1.get(0) + "").substring(25,(td1.get(0)+"").length());
                                String[] b = a.split(">");
                                String[] c = b[1].split("<");
                                String course_name = c[0];
                                String[] d = b[3].split("<");
                                String course_teacher = "@"+ d[0];
                                String course_place = " ";
                                String infor = course_name+"\n"+course_teacher+"\n"+course_place;
                                Log.e("view66666666666666",infor);
                                courseList.add(infor);
                            }else{
                                courseList.add(" ");
                            }

                        }
                    }else{
                        for(int j = 1;j<td.size()-2;j++){
                            Elements td1 = td.get(j).getElementsByTag("td");

//                            Log.e("content----------"+j,td1.get(0)+"");
//                            Log.e("length", (td1.get(0)+"").length() + "");
                            if((td1.get(0)+"").length()>200){
//                                Log.e("view", (td1.get(0) + "").substring(25, (td1.get(0) + "").length() / 4+30));
                                String a = (td1.get(0) + "").substring(25,(td1.get(0)+"").length());
                                String[] b = a.split(">");
                                String[] c = b[1].split("<");
                                String course_name = c[0];
                                String[] d = b[3].split("<");
                                String course_teacher ="@"+  d[0];
                                String[] e = b[4].split("<");
                                String course_place = e[0];
                                String infor = course_name+"\n"+course_teacher+"\n"+course_place;
                                Log.e("view66666666666666",infor);
                                courseList.add(infor);
                            } else if((td1.get(0)+"").length()>80){
//                                Log.e("view",(td1.get(0)+"").substring(25, (td1.get(0) + "").length() / 2+15));
                                String a = (td1.get(0) + "").substring(25,(td1.get(0)+"").length());
                                String[] b = a.split(">");
                                String[] c = b[1].split("<");
                                String course_name = c[0];
                                String[] d = b[3].split("<");
                                String course_teacher ="@"+ d[0];
                                String[] e = b[4].split("<");
                                String course_place = e[0];
                                String infor = course_name+"\n"+course_teacher+"\n"+course_place;
                                Log.e("view66666666666666",infor);
                                courseList.add(infor);
                            }else if((td1.get(0)+"").length() > 50){
//                                Log.e("view",(td1.get(0) + "").substring(25,(td1.get(0)+"").length()));
                                String a = (td1.get(0) + "").substring(25,(td1.get(0)+"").length());
                                String[] b = a.split(">");
                                String[] c = b[1].split("<");
                                String course_name = c[0];
                                String[] d = b[3].split("<");
                                String course_teacher = "@"+ d[0];
                                String course_place = " ";
                                String infor = course_name+"\n"+course_teacher+"\n"+course_place;
                                Log.e("view66666666666666",infor);
                                courseList.add(infor);
                            }else{
                                courseList.add(" ");
                            }

                        }
                    }
                }

                course_content = document.getElementsByTag("tr").text();
                Log.e("Content:",course_content);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header = new HashMap<>();
                header.put("Cookie",cookies);
                header.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                header.put("Referer","http://222.24.62.120/xskbcx.aspx?xh="+loginName+"&xm="+student_name+"&gnmkdm=N121603");
                header.put("Accept-Encoding","gzip, deflate");
                header.put("Accept-Language","zh-Hans-CN,zh-Hans;q=0.8");
                header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
                return header;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return ("__EVENTTARGET=xqd&__EVENTARGUMENT=&__VIEWSTATE="+aaa[0]+"&xnd=2016-2017&xqd=1").getBytes();
            }
        };
        mqueue.add(request);
    }

    private void get_personInfor() {
        String url = "http://222.24.62.120/xsgrxx.aspx?xh="+loginName+"&xm="+student_name+"&gnmkdm=N121501";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Document document = Jsoup.parse(s);
                stuname = document.getElementById("xm").text();
                stuid = document.getElementById("xh").text();
                stuacademy = document.getElementById("lbl_xy").text();
                stumajor = document.getElementById("lbl_zymc").text();
                stuclass = document.getElementById("lbl_xzb").text();
                stueducation = document.getElementById("lbl_CC").text();
                Log.e("person2222222",stuname+"--"+stuid+"--"+stuacademy+"--"+stumajor+"--"+stuclass+"--"+stueducation);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("pererror333333",volleyError+"");
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header = new HashMap<>();
                header.put("Cookie",cookies);
                header.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                header.put("Referer","http://222.24.62.120/xsgrxx.aspx?xh="+loginName+"&xm="+student_name+"&gnmkdm=N121501");
                header.put("Accept-Encoding","gzip, deflate");
                header.put("Accept-Language","zh-Hans-CN,zh-Hans;q=0.8");
                header.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
                return header;
            }
        };
        mqueue.add(stringRequest);
    }

    private void get_Score(){
        score_inforList.clear();
        final String[] __viewstate = new String[1];
        String url = "http://222.24.62.120/xscjcx.aspx?xh="+loginName+"&xm="+student_name+"&gnmkdm=N121605";
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //repsone_content = s;
                Document document1 = Jsoup.parse(s);
                Log.e("success","111111111111111111111");
                __viewstate[0] = document1.select("input[name=__VIEWSTATE]").val();
                try {
                    __viewstate[0] = URLEncoder.encode(__viewstate[0],"GBK");
                } catch (UnsupportedEncodingException e) {
                    Log.e("error","123456789");
                }
                Log.e("viewstate",__viewstate[0]);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("failure",volleyError+"");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header = new HashMap<>();
                header.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                header.put("Accept-Encoding","gzip, deflate");
                header.put("Accept-Language","zh-Hans-CN,zh-Hans;q=0.8");
                header.put("Referer","http://222.24.62.120/xscjcx.aspx?xh="+loginName+"&xm="+stuname+"&gnmkdm=N121605");
                header.put("Cookie",cookies);
                header.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
                return header;
            }

        };
        mqueue.add(stringRequest1);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                repsone_content = s;
                Document document = Jsoup.parse(s);
                Elements tr = document.getElementsByTag("tr");
                for(int i =5;i<tr.size()-7;i++){
//                    Log.e("tr.content",tr.get(i)+"");
                    Elements td = tr.get(i).getElementsByTag("td");
                    String name = td.get(3).text();
                    String team = td.get(4).text();
                    String gpa = td.get(6).text();
                    String xf = td.get(7).text();
                    String score = td.get(8).text();
                    String xy = td.get(12).text();
                    Score_Infor infor = new Score_Infor(name,team,gpa,xf,score, xy);
                    score_inforList.add(infor);
//                    Log.e("chengji","--"+name+"--"+team+"--"+xf+"--"+gpa+"--"+score+"--"+xy);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("failure",""+volleyError);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header = new HashMap<>();
                header.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                header.put("Accept-Encoding","gzip, deflate");
                header.put("Accept-Language","zh-Hans-CN,zh-Hans;q=0.8");
                header.put("Referer","http://222.24.62.120/xscjcx.aspx?xh="+loginName+"&xm="+stuname+"&gnmkdm=N121605");
                header.put("Cookie",cookies);
                header.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
                return header;
            }


            @Override
            public byte[] getBody() throws AuthFailureError {
                String body = "__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE="+__viewstate[0]+"&hidLanguage=&ddlXN=2016-2017&ddlXQ=&ddl_kcxz=&btn_xn=%D1%A7%C4%EA%B3%C9%BC%A8";
                return body.getBytes();
            }
        };
        mqueue.add(stringRequest);
    }
}
