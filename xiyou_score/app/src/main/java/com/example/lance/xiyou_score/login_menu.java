package com.example.lance.xiyou_score;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static com.example.lance.xiyou_score.Content.*;

public class login_menu extends Activity implements View.OnClickListener{

//    private List<String> courseList = new ArrayList<>();
    private EditText login_name;
    private EditText login_password;
    private EditText login_checkCode;
    private ImageView login_checkCode_image;
    private Button login_button;
    private Button login_cancel;
    private ImageView login_school_logo;

    private byte[] imagebytes;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);

        mqueue = Volley.newRequestQueue(this);
        login_name = (EditText) findViewById(R.id.login_name);
        login_password = (EditText) findViewById(R.id.login_password);
        login_checkCode = (EditText) findViewById(R.id.login_checkCode);
        login_checkCode_image = (ImageView) findViewById(R.id.login_checkCode_image);
        login_button = (Button) findViewById(R.id.login_button);
        login_cancel = (Button) findViewById(R.id.login_button_cancel);
        login_school_logo = (ImageView) findViewById(R.id.login_school_logo);

        login_button.setOnClickListener(this);
        login_cancel.setOnClickListener(this);
        login_checkCode_image.setOnClickListener(this);

        schoolLogoImage();
        checkCodeImage();

        //getCookie();
    }

    private void checkCodeImage(){
        StringRequest getcheckcode = new StringRequest(Request.Method.GET, "http://222.24.62.120/CheckCode.aspx", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagebytes, 0, imagebytes.length);
                login_checkCode_image.setImageBitmap(bitmap);
                Log.e("qwe", "success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                imagebytes = response.data;
                Log.e("response_headers", response.headers + "");
                cookies = response.headers.get("Set-Cookie");
                String dataString = null;
                try{
                    dataString = new String(response.data,"UTF-8");
                    return Response.success(dataString, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };
        mqueue.add(getcheckcode);
    }



    private void schoolLogoImage() {
        RequestQueue mQueue1 = Volley.newRequestQueue(this);
        ImageLoader imageLoader1 = new ImageLoader(mQueue1, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                return null;
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {

            }
        });
        ImageLoader.ImageListener listener1 = ImageLoader.getImageListener(login_school_logo, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        String school_logo_url = "http://222.24.62.120/logo/logo_school.png";
        imageLoader1.get(school_logo_url,listener1,0,100);
        Log.e("我是:", school_logo_url);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("信息提示：");

                loginName = login_name.getText().toString();
                loginPassword = login_password.getText().toString();
                loginCheckCode = login_checkCode.getText().toString();

                Log.e("information:", loginName + "-" + loginPassword + "-" + loginCheckCode);
                StringRequest request = new StringRequest(Request.Method.POST, "http://222.24.62.120/default2.aspx", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //repsone_content = s;
                        Log.d("111111111111111111:",s);
                        if(flag == 1){
                            Document document1 = Jsoup.parse(s);
                            student_name = document1.getElementById("xhxm").text();
                            student_name = student_name.substring(0, student_name.length() - 2);
                            Log.e("student_name:", student_name);

                            //get_CourseTable();
                            //get_personInfor();

                            builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(login_menu.this, main_menu.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            builder.setMessage("欢迎您，"+student_name+"同学！").create().show();
                        } else {
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    checkCodeImage();
                                }
                            });
                            builder.setMessage("登录失败，请重新登录").create().show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        builder.setMessage("请检查网络连接");
                        builder.setPositiveButton("确认",null).create().show();
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> header = new HashMap<>();
                        header.put("Cookie",cookies);
                        header.put("Referer","http://222.24.62.120/");
                        header.put("Pragma","no-cache");
                        header.put("Accept-Encoding","gzip, deflate");
                        header.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                        header.put("Accept-Language","zh-Hans-CN,zh-Hans;q=0.8");
                        header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
                        Log.e("cookie",cookies+"1111111");
                        return header;
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        System.out.println(response.headers);
                        Log.e("statusCode",response.statusCode+"");
                        if(response.data.length>=9000) {
                            flag = 1;
                        }
                        return super.parseNetworkResponse(response);
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        return ("__VIEWSTATE=dDwtNTE2MjI4MTQ7Oz5O9kSeYykjfN0r53Yqhqckbvd83A==&txtUserName="+loginName+"&Textbox1=&TextBox2="+loginPassword+"&txtSecretCode="+loginCheckCode+"&RadioButtonList1=%D1%A7%C9%FA&Button1=&lbLanguage=&hidPdrs=&hidsc=").getBytes();
                    }
                };
                mqueue.add(request);
                break;

            case R.id.login_checkCode_image:
                checkCodeImage();
                break;
            case R.id.login_button_cancel:
                finish();
                break;
            default:
                break;
        }
    }





}

