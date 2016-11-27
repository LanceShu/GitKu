package com.example.lance.weatherapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;
import static com.example.lance.weatherapp.Content.*;

public class MainActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private LocationManager locationManager;        //位置管理;
    private String provider;                        //使用的位置的服务;
    private String address;                         //当前的地理位置;

    public static MainActivity mainActivity;
    private LinearLayout w_backgroup;               //修改天气界面的背景;
    private  EditText edit_city;                    //输入所要查询的城市；
    private ImageView city_search;                  //查询按钮；
    private TextView w_city;                        //显示城市名称;
    private TextView w_weather;                     //显示天气情况;
    private TextView w_temp;                        //显示天气温度;
    private TextView w_week;                        //显示星期;
    private TextView w_hightemp;                    //显示最高气温;
    private TextView w_lowtemp;                     //显示最低气温；
    private TextView w_wind_direction;              //显示风向;
    private TextView w_high_temp;                   //最高气温;
    private TextView w_low_temp;                    //最低气温;
    private TextView w_high_humi;                   //最高湿度；
    private TextView w_low_humi;                    //最低湿度
    private TextView w_wind_power;                  //显示风力;

    private ListView citymenu;                      //未来天气情况；
    private ArrayAdapter<String> cityadapter;       //城市适配器;
//    private List<String> cityl;

    //左边layout布局；
    private ImageView w_left;                       //
    private ImageView w_right;
    private DrawerLayout drawerLayout;              //DrawerLayout布局;

    private ImageView left;                         //
    private LinearLayout l_care;                    // 温馨小提示;
    private LinearLayout l_set;                     //设置中心；
    private LinearLayout l_about;                   //关于我们；
    private TextView l_exit;                        //退出系统；

    //未来天气的近况;
    private LinearLayout wGrallery;
    private LayoutInflater wInflater;
    private WeatherInfo[] w_feature;

    public static final int UPDATE_SUCCESS = 1;
    public static final int UPDATE_ERROR = 0;

    private int weatid;                             //天气id；
    private String citynm;                          //城市名称;
    private String weather;                         //天气;
    private String temp;                            //温度;
    private String date;                            //日期;
    private String week;                            //星期;
    private String cityname;                        //城市名称;
    private  int i;
    private RemoteViews remoteViews;
    private WeatherBoxReciver reciver;              //广播接受;

    private LinearLayout w_back;
    private AutoCompleteTextView autoCompleteTextView;  //自动填充城市名称;
    private Button button;
    private TextView textView;
    private TextView textView2;
    private TextView textView7;
    private EditText editText;
    private SharedPreferences.Editor editor;
    private SharedPreferences.Editor editor2;
    private SharedPreferences preferences;
    private SharedPreferences preferences2;
    private String []city = {"西安","广东"};

    public android.os.Handler listhandler = new Handler()
    {
        public void handleMessage(Message message)
        {
            switch (message.what)
            {
                case 1:
                    city = (String[])message.obj;
                    Log.e("there", "handler");
                    autoCompleteSetting(city);
                    break;
            }
        }
    };
    ArrayAdapter<String> adapter;

    public  Handler handler = new Handler(){
        public void handleMessage(Message message)
        {
            switch (message.what){
                case UPDATE_SUCCESS:{
                    wInflater = LayoutInflater.from(MainActivity.this);

                    WeatherInfo[] weatherInfos = (WeatherInfo[]) message.obj;

                    weatid = weatherInfos[0].getWeatid();
                    weather = weatherInfos[0].getWeather();
                    temp = weatherInfos[0].getTemperature();
                    i = weatherInfos[0].getTemp_high();

                    if(weatherInfos[0].getWeatid() ==1){
                        w_backgroup.setBackgroundResource(R.drawable.s);
                    }else if(weatherInfos[0].getWeatid() == 2||weatherInfos[0].getWeatid() ==3||weatherInfos[0].getWeatid() == 19){
                        w_backgroup.setBackgroundResource(R.drawable.y);
                    }else if((weatherInfos[0].getWeatid()>=7&&weatherInfos[0].getWeatid()<=13)||weatherInfos[0].getWeatid()==4){
                        w_backgroup.setBackgroundResource(R.drawable.r);
                    }else if(weatherInfos[0].getWeatid()==5||weatherInfos[0].getWeatid()==6){
                        w_backgroup.setBackgroundResource(R.drawable.l);
                    }else if(weatherInfos[0].getWeatid()>=30&&weatherInfos[0].getWeatid()<=33){
                        w_backgroup.setBackgroundResource(R.drawable.sha);
                    }else{
                        w_backgroup.setBackgroundResource(R.drawable.x);
                    }

                    edit_city.setText("");
                    w_city.setText(weatherInfos[0].getCitynm());
                    cityname = weatherInfos[0].getCitynm();
                    w_weather.setText(weatherInfos[0].getWeather());
                    String[] a = weatherInfos[0].getTemperature().split("/");
                    w_temp.setText(a[0]);
                    w_week.setText(weatherInfos[0].getWeek());
                    w_hightemp.setText(a[0]);
                    w_lowtemp.setText(a[1]);
                    w_wind_direction.setText(weatherInfos[0].getWind());
                    w_high_temp.setText(a[0]);
                    w_low_temp.setText(a[1]);
                    w_high_humi.setText(weatherInfos[0].getDays());
                    date = weatherInfos[0].getDays();
                    week = weatherInfos[0].getWeek();
                    w_low_humi.setText(weatherInfos[0].getWeek());
                    w_wind_power.setText(weatherInfos[0].getWinp());
                    wGrallery.removeAllViews();
                    initGrallery(weatherInfos);
                    break;
                }
                case UPDATE_ERROR:
                {
                    String s= "不存在的气象城市编号";
//                    wGrallery.removeAllViews();
//                    initui();
                    Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    };

    private void initGrallery(WeatherInfo[] w_feature){

        for(int i=0;i<7;i++){
            Log.e("weatid",w_feature[i].getWeatid()+"");
        }

        for(int ii = 0;ii<w_feature.length;ii++){
            Log.e("length",""+w_feature.length);

            View view = wInflater.inflate(R.layout.w_grallery,wGrallery,false);
            TextView w_week = (TextView)view.findViewById(R.id.grallery_week);
            ImageView w_image = (ImageView)view.findViewById(R.id.grallery_image);
            TextView w_weather = (TextView)view.findViewById(R.id.grallery_temp);

            w_week.setText(w_feature[ii].getWeek());
          switch (w_feature[ii].getWeatid()){
              case 1:w_image.setImageResource(R.drawable.w0);break;
              case 2:w_image.setImageResource(R.drawable.w1);break;
              case 3:w_image.setImageResource(R.drawable.w2);break;
              case 4:w_image.setImageResource(R.drawable.w3);break;
              case 5:w_image.setImageResource(R.drawable.w4);break;
              case 6:w_image.setImageResource(R.drawable.w5);break;
              case 7:w_image.setImageResource(R.drawable.w6);break;
              case 8:w_image.setImageResource(R.drawable.w7);break;
              case 9:w_image.setImageResource(R.drawable.w8);break;
              case 10:w_image.setImageResource(R.drawable.w9);break;
              case 11:w_image.setImageResource(R.drawable.w10);break;
              case 12:w_image.setImageResource(R.drawable.w11);break;
              case 13:w_image.setImageResource(R.drawable.w12);break;
              case 14:w_image.setImageResource(R.drawable.w13);break;
              case 15:w_image.setImageResource(R.drawable.w14);break;
              case 16:w_image.setImageResource(R.drawable.w15);break;
              case 17:w_image.setImageResource(R.drawable.w16);break;
              case 18:w_image.setImageResource(R.drawable.w17);break;
              case 19:w_image.setImageResource(R.drawable.w18);break;
              case 20:w_image.setImageResource(R.drawable.w19);break;
              case 21:w_image.setImageResource(R.drawable.w20);break;
              case 22:w_image.setImageResource(R.drawable.w21);break;
              case 23:w_image.setImageResource(R.drawable.w22);break;
              case 24:w_image.setImageResource(R.drawable.w23);break;
              case 25:w_image.setImageResource(R.drawable.w24);break;
              case 26:w_image.setImageResource(R.drawable.w25);break;
              case 27:w_image.setImageResource(R.drawable.w26);break;
              case 28:w_image.setImageResource(R.drawable.w27);break;
              case 29:w_image.setImageResource(R.drawable.w28);break;
              case 30:w_image.setImageResource(R.drawable.w29);break;
              case 31:w_image.setImageResource(R.drawable.w30);break;
              case 32:w_image.setImageResource(R.drawable.w31);break;
              case 33:w_image.setImageResource(R.drawable.w53);break;
              default:break;
          }
            w_weather.setText(w_feature[ii].getTemperature());
            wGrallery.addView(view);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, 1);

        locationManager  = (LocationManager)getSystemService(LOCATION_SERVICE);
        List<String> prociderlist = locationManager.getProviders(true);
        if(prociderlist.contains(LocationManager.GPS_PROVIDER)){
            provider = LocationManager.GPS_PROVIDER;
        }else if(prociderlist.contains(LocationManager.NETWORK_PROVIDER)){
            provider = LocationManager.NETWORK_PROVIDER;
        }else {
            Toast.makeText(this, "no location service to use", Toast.LENGTH_SHORT).show();
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if(location!=null){
            showLocation(location);
        }
        locationManager.requestLocationUpdates(provider,5000,100,locationListener);

        citymenu = (ListView) findViewById(R.id.citylist);

        wGrallery = (LinearLayout)findViewById(R.id.w_grallery);
        mainActivity = this;
        w_backgroup = (LinearLayout)findViewById(R.id.w_backgrounp);
        edit_city = (EditText)findViewById(R.id.edit_city);
        city_search = (ImageView)findViewById(R.id.city_search);
        w_city = (TextView)findViewById(R.id.w_city);
        w_weather = (TextView)findViewById(R.id.w_weather);
        w_temp = (TextView)findViewById(R.id.w_temp);
        w_week = (TextView)findViewById(R.id.w_week);
        w_hightemp = (TextView)findViewById(R.id.w_hightemp);
        w_lowtemp = (TextView)findViewById(R.id.w_lowtemp);
        w_wind_direction = (TextView)findViewById(R.id.w_wind_direction);
        w_high_temp = (TextView)findViewById(R.id.w_high_temp);
        w_low_temp = (TextView)findViewById(R.id.w_low_temp);
        w_high_humi = (TextView)findViewById(R.id.w_high_humi);
        w_low_humi =(TextView)findViewById(R.id.w_low_humi);
        w_wind_power = (TextView)findViewById(R.id.w_wind_power);
        w_left = (ImageView)findViewById(R.id.w_left);
        w_right = (ImageView)findViewById(R.id.w_right);
        drawerLayout = (DrawerLayout)findViewById(R.id.w_drawer);

        left = (ImageView)findViewById(R.id.left);
        l_care = (LinearLayout)findViewById(R.id.l_care);
        l_set = (LinearLayout)findViewById(R.id.l_set);
        l_about = (LinearLayout)findViewById(R.id.l_about);
        l_exit = (TextView)findViewById(R.id.l_exit);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView7 = (TextView) findViewById(R.id.textView7);
        editText= (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        preferences = getSharedPreferences("cityname", MODE_PRIVATE);
        preferences2 = getSharedPreferences("mobilenum", MODE_PRIVATE);
        textView2.setText(preferences.getString("city", "null"));
        textView7.setText(preferences2.getString("num", "null"));
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 0 || actionId == 3 && event != null) {
                    textView7.setText(editText.getText());
                    editor2 = getSharedPreferences("mobilenum", MODE_PRIVATE).edit();
                    editor2.putString("num", editText.getText().toString());
                    editor2.commit();
                    editText.setText("");
                    return true;
                }
                return false;
            }
        });
        button.setOnClickListener(this);

        w_head();
        w_left.setOnClickListener(this);
        w_right.setOnClickListener(this);
        city_search.setOnClickListener(this);

        left.setOnClickListener(this);
        l_care.setOnClickListener(this);
        l_set.setOnClickListener(this);
        l_about.setOnClickListener(this);
        l_exit.setOnClickListener(this);

        reciver = new WeatherBoxReciver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Content.MAINACTIVITY);
        registerReceiver(reciver, filter);

        Intent intent = new Intent(this,WeatherService.class);
        startService(intent);

        Intent intent1 = new Intent(Content.SERVICE);
        intent1.putExtra("control", Content.renew);
        sendBroadcast(intent1);

        setNotification();

        cityadapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,cityl);
        citymenu.setAdapter(cityadapter);
        citymenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivityToService(Content.scroll, cityl.get(position));
            }
        });


    }


    private void w_head(){
        HttpUtil.sendHttpRequest(textView2.getText()+"", handler, new HttpCallBackListener() {
            @Override
            public void onFinish(WeatherInfo[] weatherInfos, Handler handler) {
                Message message = new Message();
                if (weatherInfos == null) {
                    message.what = UPDATE_ERROR;
                    handler.sendMessage(message);
                } else {
                    message.what = UPDATE_SUCCESS;
                    message.obj = weatherInfos;
                    w_feature = weatherInfos;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    class WeatherBoxReciver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
//             weatid = intent.getIntExtra("weatid",-1);
             citynm = intent.getStringExtra("citynm");
//            cityl.add(citynm);

            renew(citynm);
        }
    }

    private void renew(String citynm){
            HttpUtil.sendHttpRequest(citynm, handler, new HttpCallBackListener() {
            @Override
            public void onFinish(WeatherInfo[] weatherInfos, Handler handler) {
                Message message = new Message();
                if (weatherInfos == null) {
                    message.what = UPDATE_ERROR;
                    handler.sendMessage(message);
                } else {
                    message.what = UPDATE_SUCCESS;
                    message.obj = weatherInfos;
                    w_feature = weatherInfos;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void setNotification() {
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification();

        remoteViews = new RemoteViews(getPackageName(), R.layout.weathernotify);

        remoteViews.setTextViewText(R.id.n_city, citynm + "  " + temp);
        remoteViews.setTextViewText(R.id.n_temp, weather);

        switch (weatid) {
            case 1:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w0);break;
            case 2:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w1);break;
            case 3:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w2);break;
            case 4:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w3);break;
            case 5:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w4);break;
            case 6:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w5);break;
            case 7:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w6);break;
            case 8:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w7);break;
            case 9:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w8);break;
            case 10:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w9);break;
            case 11:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w10);break;
            case 12:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w11);break;
            case 13:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w12);break;
            case 14:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w13);break;
            case 15:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w14);break;
            case 16:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w15);break;
            case 17:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w16);break;
            case 18:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w17);break;
            case 19:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w18);break;
            case 20:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w19);break;
            case 21:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w20);break;
            case 22:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w21);break;
            case 23:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w22);break;
            case 24:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w23);break;
            case 25:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w24);break;
            case 26:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w25);break;
            case 27:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w26);break;
            case 28:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w27);break;
            case 29:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w28);break;
            case 30:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w29);break;
            case 31:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w30);break;
            case 32:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w31);break;
            case 33:remoteViews.setImageViewResource(R.id.n_image, R.drawable.w53);break;
            default:break;
        }

        if(i>=35){
            remoteViews.setTextViewText(R.id.n_care,"亲,天气炎热，注意防暑！！");
        }else if(i == 27){
            remoteViews.setTextViewText(R.id.n_care,"亲，天气适合出行~");
        }else if(i<=16){
            remoteViews.setTextViewText(R.id.n_care,"亲，天气寒冷，注意保暖~");
        }

        if(weatid ==1){
            remoteViews.setImageViewResource(R.id.notify,R.drawable.s);
        }else if(weatid == 2||weatid ==3||weatid== 19){
            remoteViews.setImageViewResource(R.id.notify, R.drawable.y);
        }else if((weatid>=7&&weatid<=13)||weatid==4){
            remoteViews.setImageViewResource(R.id.notify, R.drawable.r);
        }else if(weatid==5||weatid==6){
            remoteViews.setImageViewResource(R.id.notify, R.drawable.l);
        }else if(weatid>=30&&weatid<=33){
            remoteViews.setImageViewResource(R.id.notify, R.drawable.sha);
        }else{
            remoteViews.setImageViewResource(R.id.notify, R.drawable.x);
        }

        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent w = PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notify,w);

        notification.contentView = remoteViews;
        notification.bigContentView = remoteViews;
        notification.flags = Notification.FLAG_ONGOING_EVENT;

        Log.e("notification","notify");

        notificationManager.notify(100, notification);
        Log.e("notification222", "notif222y");
    }


    public void MainActivityToService(int state,String citynm){
        Log.e("ffffffff", "ffffffffffffffffff");
        Intent intent = new Intent(Content.SERVICE);
        intent.putExtra("control", state);
        intent.putExtra("citynm", citynm);
        sendBroadcast(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.city_search:
                cityl.add(autoCompleteTextView.getText() + "");
                MainActivityToService(Content.search,autoCompleteTextView.getText()+"");
                break;
            case R.id.w_left:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.w_right:
//                if(weatid ==1){
//                    w_back.setBackgroundResource(R.drawable.s);
//                }else if(weatid == 2||weatid ==3||weatid == 19){
//                    w_back.setBackgroundResource(R.drawable.y);
//                }else if((weatid>=7&&weatid<=13)||weatid==4){
//                    w_back.setBackgroundResource(R.drawable.r);
//                }else if(weatid==5||weatid==6){
//                    w_back.setBackgroundResource(R.drawable.l);
//                }else if(weatid>=30&&weatid<=33){
//                    w_back.setBackgroundResource(R.drawable.sha);
//                }else{
//                    w_back.setBackgroundResource(R.drawable.x);
//                }

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, 1);
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_SMS},1);
                HttpCity.cityListRequest(0, listhandler, new HttpCityCallBack() {
                    @Override
                    public void onFinish(String[] cityInfos, android.os.Handler handler) {
                        if (cityInfos != null) {
                            Message message = new Message();
                            message.what = 1;
                            Log.i("onFinish", "onFinish");
                            message.obj = cityInfos;
                            handler.sendMessage(message);
                            Log.i("handleMessage", "finish");
                        } else {
                            Message message = new Message();
                            message.what = 0;
                            Log.i("handMessage", "fail");
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
                drawerLayout.openDrawer(Gravity.RIGHT);
                break;
            case R.id.left:
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.l_care:
                if(i>=35){
                    dialog("亲爱的,天气炎热，注意防暑！！","温馨小提醒~~");
                }else if(i >= 25&&i<=27){
                    dialog("亲爱的，今天的天气，您的皮肤最喜欢了~~~","温馨小提醒~~");
                }else if(i<=16){
                    dialog("亲爱的，天气寒冷，记得添衣，注意保暖~","温馨小提醒~~");
                }else{
                    dialog("亲爱的，今天天气不错，但别忘了多喝水哦~~","温馨小提醒~~");
                }
                break;
            case R.id.l_set:
                drawerLayout.closeDrawer(Gravity.LEFT);
                drawerLayout.openDrawer(Gravity.RIGHT);
                break;
            case  R.id.l_about:
                dialog("Power By \n@西邮移动应用开发兴趣小组~~", "关于我们~~");
                break;
            case R.id.l_exit:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("亲爱的，确定要离开我嘛？");
                builder.setTitle("提示");
                builder.setPositiveButton("心意已决", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                        System.exit(0);
                    }
                });
                builder.setNegativeButton("考虑一下", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();

                break;
            case R.id.button:
                if((textView7.getText()==null)||textView7.getText()=="null") {
                    Toast toast = Toast.makeText(this, "没有目标号码!", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                }
                if(i>=35){
                    sendSMS(String.valueOf(textView7.getText()), date+"\n"+week+"\n"+cityname + "\n" + weather + "\n" + temp+"\n亲爱的,天气炎热，注意防暑！！");
                }else if(i >= 25&&i<=27){
                    sendSMS(String.valueOf(textView7.getText()), date+"\n"+week+"\n"+cityname + "\n" + weather + "\n" + temp+"\n亲爱的，今天的天气，您的皮肤最喜欢了~~~");
                }else if(i<=16){
                    sendSMS(String.valueOf(textView7.getText()), date+"\n"+week+"\n"+cityname + "\n" + weather + "\n" + temp+"\n亲爱的，天气寒冷，记得添衣，注意保暖~");
                }else{
                    sendSMS(String.valueOf(textView7.getText()), date+"\n"+week+"\n"+cityname + "\n" + weather + "\n" + temp+"\n亲爱的，今天天气不错，但别忘了多喝水哦~~");
                }
//                sendSMS(String.valueOf(textView7.getText()), citynm+" "+weather+" "+temp);//需要天气数据
                Toast toast = Toast.makeText(this, "已发送!", Toast.LENGTH_SHORT);
                toast.show();
                break;
            default:
                break;
        }
    }

    public void sendSMS(String phoneNumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
//        if((textView7.getText()==null)) {
//            Toast toast = Toast.makeText(this, "没有目标号码!", Toast.LENGTH_SHORT);
//            toast.show();
//        }else{
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
    }

    protected void dialog(String title,String Title){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(title);
        builder.setTitle(Title);
        builder.setNegativeButton("好的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void initui(){
        edit_city.setText("");
        w_city.setText("- -");
        w_weather.setText("- -");
        w_temp.setText("- -");
        w_week.setText("- -");
        w_hightemp.setText("- -");
        w_lowtemp.setText("- -");
        w_wind_direction.setText("- -");
        w_high_temp.setText("- -");
        w_low_temp.setText("- -");
        w_high_humi.setText("- -");
        w_low_humi.setText("- -");
        w_wind_power.setText("- -");
    }

//    public boolean onKeyDown(int KeyCode,KeyEvent event){
//        if(KeyCode ==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage("亲爱的，确定要离开我嘛？");
//            builder.setTitle("提示");
//            builder.setPositiveButton("心意已决", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                    finish();
//                    System.exit(0);
//                }
//            });
//            builder.setNegativeButton("考虑一下", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            builder.create().show();
//        }
//        return false;
//    }

    public void autoCompleteSetting(String[] city)
    {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, city);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView2.setText(autoCompleteTextView.getText());
                editor = getSharedPreferences("cityname", MODE_PRIVATE).edit();
                editor.putString("city", autoCompleteTextView.getText().toString());
                editor.commit();
            }
        });

        autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 0 || actionId == 3 && event != null) {
                    textView2.setText(autoCompleteTextView.getText());
                    editor = getSharedPreferences("cityname", MODE_PRIVATE).edit();
                    editor.putString("city", autoCompleteTextView.getText().toString());
                    editor.commit();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(locationManager!=null){
            locationManager.removeUpdates(locationListener);
        }

    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void showLocation(Location location){
        String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + location.getLatitude() + "," + location.getLongitude() + "&language=zh-CN" + "&sensor=false";
        GPSHttpUtil.sendHttpRequest(url, new GPSHttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
//                    Log.e("results----------", "" + jsonArray);
                    if (jsonArray.length() > 0) {
                        JSONObject subobject = jsonArray.getJSONObject(0);
                        String address = subobject.getString("address_components");
                        JSONArray jsonArray1 = new JSONArray(address);

                        for(int i =0;i<jsonArray.length();i++){
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                            String ad1 = jsonObject1.getString("long_name");
                            Log.e("ad", ad1);
                        }
                        JSONObject jsonObject1 = jsonArray1.getJSONObject(3);
                        String ad = jsonObject1.getString("long_name");
                        Log.e("ad", ad);

                        Message message = new Message();
                        message.what =Content.address;
                        message.obj = ad;
                        handler8.sendMessage(message);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private Handler handler8 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Content.address:
                    if(((String)msg.obj)!=null){
                        String[] c = ((String)msg.obj).split("市");
                        address = c[0];
                        Log.e("address--------",address);
                        cityl.add(address);
                        MainActivityToService(Content.search, address);
                    }
                    break;
                default:
                    break;
            }
        }
    };

}
