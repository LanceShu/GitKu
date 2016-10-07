package com.example.lance.playertest;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener{

    public  int i = 0;                              //歌曲的位�?;
    private List<Music> musiclist;                  //歌单;
    private ImageView lastsong;                     //上一首按�?;
    private ImageView pause;                        //暂停与播放按�?;
    private ImageView nextsong;                     //下一首按�?;
    private boolean play;                           //播放的状�?;
    private TextView text;                          //歌曲名字;
    private ListView listView;                      //用俩存取歌曲信息;
    private MusicBoxReceiver musicBoxReceiver;      //activity的广播接收器;
    private ImageView musicimage;

//    private NotificationManager notificationManager;
    private RemoteViews remoteViews;
    private Handler handler = new Handler();

    private static boolean isExit= false;

    Handler mhandler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            isExit = false;
        }
    };

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);              //去掉app界面的标题栏;
        setContentView(R.layout.activity_main);
        ActivityFinish.addActivty(this);

//        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        musiclist = MusicList.getMusicData(MainActivity.this);      //获取歌曲信息;
        MusicAdapter musicAdapter = new MusicAdapter(MainActivity.this,R.layout.musiclistview,musiclist);   //制作适配�?;
        listView = (ListView)findViewById(R.id.music_listview);     //实现listview的点�?;
        listView.setAdapter(musicAdapter);                          //将�?�配器存于listview�?;
        pressMenu(listView);                                        //实现点击功能;

        lastsong = (ImageView)findViewById(R.id.lastsong);          //按钮实例�?;
        pause = (ImageView)findViewById(R.id.pause);
        nextsong = (ImageView)findViewById(R.id.nextsong);
        text = (TextView)findViewById(R.id.music_name_1);
        musicimage = (ImageView)findViewById(R.id.imagetext);

        lastsong.setOnClickListener(this);                          //点击事件;
        pause.setOnClickListener(this);
        nextsong.setOnClickListener(this);
        musicimage.setOnClickListener(this);

        musicBoxReceiver = new MusicBoxReceiver();                  //activity 的广播接受�??;
        IntentFilter filter = new IntentFilter();                   //动�?�注�?;
        filter.addAction(ConstUtil.MUSICBOX_ACTION);                //字符串匹�?
        registerReceiver(musicBoxReceiver, filter);                 //注册广播接受�?;


        Intent intent = new Intent(this,MusicService.class);        //intent 意图;
        startService(intent);                                       //启动服务;

        sendBroastcastToService(ConstUtil.STATE_RENEW, i);          //发�?�更新UI的广�?;
//        setNotification();
        handler.post(start);
    }

    private void setNotification(){
        Notification notification = new Notification();
        remoteViews = new RemoteViews(getPackageName(),R.layout.musicnotify);

        remoteViews.setTextViewText(R.id.music_text_3, musiclist.get(i).getName());
        remoteViews.setImageViewResource(R.id.music_image_3, R.drawable.musicsinger);
        if(play){
            remoteViews.setImageViewResource(R.id.music_pause_3,R.drawable.pause);
        }else{
            remoteViews.setImageViewResource(R.id.music_pause_3,R.drawable.play);
        }

        Intent intent_notify = new Intent(this,MainActivity.class);
        PendingIntent intent_go_1 = PendingIntent.getActivity(this, 1, intent_notify, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notify, intent_go_1);

        Intent intent_image = new Intent(this,MainActivity.class);
        PendingIntent intent_go_2 = PendingIntent.getActivity(this,2,intent_image,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.music_image_3, intent_go_2);

        Intent intent_last = new Intent();
        intent_last.setAction(ConstUtil.MUSICSERVICE_ACTION);
        intent_last.putExtra("control", ConstUtil.STATE_LAST);
        intent_last.putExtra("key", i);
        PendingIntent intent_go_last = PendingIntent.getBroadcast(this, 3, intent_last, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.music_last_3, intent_go_last);

        Intent intent_next = new Intent();
        intent_next.setAction(ConstUtil.MUSICSERVICE_ACTION);
        intent_next.putExtra("control", ConstUtil.STATE_NEXT);
        intent_next.putExtra("key", i);
        PendingIntent intent_go_next = PendingIntent.getBroadcast(this, 4, intent_next, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.music_next_3, intent_go_next);

        if(play){
            Intent intent_pause = new Intent();
            intent_pause.setAction(ConstUtil.MUSICSERVICE_ACTION);
            intent_pause.putExtra("control", ConstUtil.STATE_PAUSE);
            intent_pause.putExtra("key", i);
            PendingIntent intent_go_pause = PendingIntent.getBroadcast(this, 5, intent_pause, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.music_pause_3,intent_go_pause);
        }else{
            Intent intent_play = new Intent();
            intent_play.setAction(ConstUtil.MUSICSERVICE_ACTION);
            intent_play.putExtra("control", ConstUtil.STATE_PLAY);
            intent_play.putExtra("key", i);
            PendingIntent intent_go_play = PendingIntent.getBroadcast(this, 6, intent_play, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.music_pause_3,intent_go_play);
        }

        notification.contentView = remoteViews;
        notification.bigContentView = remoteViews;
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        notification.icon = R.drawable.music;

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(100,notification);
    }

    Runnable start = new Runnable() {
        @Override
        public void run() {
            handler.post(updatenotify);
        }
    };

    Runnable updatenotify  = new Runnable() {
        @Override
        public void run() {
            setNotification();
            handler.postDelayed(updatenotify,500);
        }
    };

    //实现点击歌曲菜单的方�?;
    private void pressMenu(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                play = mediaPlayer.isPlaying();
                Music music = musiclist.get(position);
                if (i == position) {

                } else {
                    sendBroastcastToService(ConstUtil.STATE_PRESS, position);
                }
                if (i == position) {
                    if (!play) {
                        sendBroastcastToService(ConstUtil.STATE_PLAY, position);
                        pause.setImageResource(R.drawable.pause);
                    } else {
                        sendBroastcastToService(ConstUtil.STATE_PAUSE, position);
                        pause.setImageResource(R.drawable.play);
                    }
                } else {
                    sendBroastcastToService(ConstUtil.STATE_PLAY, position);
                    pause.setImageResource(R.drawable.pause);
                }
                i = position;
            }
        });
    }

    //发�?�广播到service;
    protected void sendBroastcastToService(int state,int i){
        Intent intent = new Intent(ConstUtil.MUSICSERVICE_ACTION);
        intent.putExtra("control", state);
        intent.putExtra("key",i);
        sendBroadcast(intent);
    }

    //实现点击事件;
    @Override
    public void onClick(View v) {
        text.setText(musiclist.get(i).getName() + "---" + musiclist.get(i).getSinger());
        switch (v.getId()){
            case R.id.lastsong:
                sendBroastcastToService(ConstUtil.STATE_LAST,i);
                break;
            case R.id.pause:
                if(!play){
                    sendBroastcastToService(ConstUtil.STATE_PLAY,i);
                }else{
                    sendBroastcastToService(ConstUtil.STATE_PAUSE,i);
                }
                break;
            case R.id.nextsong:
                sendBroastcastToService(ConstUtil.STATE_NEXT,i);
                break;
            case R.id.imagetext:
                Intent intent = new Intent(this,secondActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    //广播接受的方�?;
    class MusicBoxReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            int current = intent.getIntExtra("current",-1);
            i = intent.getIntExtra("key", -1);
            play = intent.getBooleanExtra("boolean", false);
            renew(i,play);

            switch (current){
                case ConstUtil.STATE_PLAY:
                    pause.setImageResource(R.drawable.pause);
                    break;
                case ConstUtil.STATE_PAUSE:
                    pause.setImageResource(R.drawable.play);
                    break;
                case ConstUtil.STATE_LAST:
                    pause.setImageResource(R.drawable.pause);
                    break;
                case ConstUtil.STATE_NEXT:
                    pause.setImageResource(R.drawable.pause);
                    break;
                case ConstUtil.STATE_RENEW:
                    break;
                default:
                    break;
            }
        }
    }

    //实现更新界面的方�?;
    public void renew(int i,boolean play){
        text.setText(musiclist.get(i).getName() + "---" + musiclist.get(i).getSinger());
        Log.e("tag-----------------",""+musiclist.get(i).getName());
        if(play){
            pause.setImageResource(R.drawable.pause);
        }else{
            pause.setImageResource(R.drawable.play);
        }
    }

    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode,event);
    }

    private void exit(){
        if(!isExit){
            isExit = true;
            Toast.makeText(getApplicationContext(),"再按一次退出程序",Toast.LENGTH_SHORT).show();
            mhandler.sendEmptyMessageDelayed(0,2000);
        }else{
            ActivityFinish.finishAll();
            NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            manager.cancel(100);
        }
    }

    //�?毁活动页�?;
    public void onDestroy(){
        super.onDestroy();
        ActivityFinish.removeActivity(this);
        unregisterReceiver(musicBoxReceiver);
    }

}
