package com.example.lance.playertest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.List;


/**
 * Created by lance on 16-9-28.
 */
public class MusicService extends Service {

    Handler handler = new Handler();

    private int mod = 0;
    private  static MediaPlayer mediaPlayer;
    private int current = 0;
    private List<Music> musiclist;
    private  int state = ConstUtil.STATE_NON;
    private  boolean isplay = false;

    private int currenttime;

    public void onCreate(){
        super.onCreate();
        musiclist = MusicList.getMusicData(this);

        MusicServiceReceiver receiver = new MusicServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConstUtil.MUSICSERVICE_ACTION);
        registerReceiver(receiver, filter);

        mediaPlayer = new MediaPlayer();
        initMediaPlayer(current);
        musicCompletion();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void ServeiceToBroadcast(int state,int current,boolean isplay){
        Intent intent = new Intent();
        intent.putExtra("current",state);
        intent.putExtra("key",current);
        intent.putExtra("boolean",isplay);
        intent.setAction(ConstUtil.MUSICBOX_ACTION);
        sendBroadcast(intent);
    }

    public void ServeiceToSecond(int state,int current,boolean isplay,int mod,long currenttime){
        Intent intent = new Intent();
        intent.putExtra("current",state);
        intent.putExtra("key",current);
        intent.putExtra("boolean",isplay);
        intent.putExtra("mode",mod);
        intent.putExtra("currenttime",currenttime);
        intent.setAction(ConstUtil.MUSICSECOND_ACTION);
        sendBroadcast(intent);

    }

    protected void initMediaPlayer(int i){
        try{
            mediaPlayer.reset();
            mediaPlayer.setDataSource(musiclist.get(i).getUrl());
            mediaPlayer.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//    10-02 18:52:55.940 2247-2247/? E/art: Failed to send JDWP packet APNM to debugger (-1 of 83): Broken pipe
//    10-02 18:52:55.948 2247-2262/? E/art: Failed sending reply to debugger: Broken pipe
//    10-02 18:52:56.418 2247-2270/? E/GED: Failed to get GED Log Buf, err(0)

    class MusicServiceReceiver extends BroadcastReceiver{
        int len = musiclist.size()-1;
        @Override
        public void onReceive(Context context, Intent intent) {
            int control = intent.getIntExtra("control",-1);
            currenttime = intent.getIntExtra("currenttime",-1);
            switch(control){
                case ConstUtil.STATE_SEEKBAR:
                    mediaPlayer.seekTo(currenttime);
                    break;
                case ConstUtil.STATE_PLAY:
                    mediaPlayer.start();
                    isplay = true;
                    ServeiceToBroadcast(ConstUtil.STATE_PLAY,current,isplay);
                    ServeiceToSecond(ConstUtil.STATE_PLAY,current,isplay,mod,mediaPlayer.getCurrentPosition());
                    break;
                case ConstUtil.STATE_PAUSE:
                    mediaPlayer.pause();
                    isplay = false;
                    ServeiceToBroadcast(ConstUtil.STATE_PAUSE, current,isplay);
                    ServeiceToSecond(ConstUtil.STATE_PAUSE,current,isplay,mod,mediaPlayer.getCurrentPosition());
                    break;
                case ConstUtil.STATE_LAST:
                    if(mod == 2){
                        current = (int)(Math.random()*100)%musiclist.size();
                    }else{
                        if(current == 0){
                            current = len;
                        }else{
                            current--;
                        }
                    }
                    initMediaPlayer(current);
                    mediaPlayer.start();
                    isplay = true;
                    ServeiceToBroadcast(ConstUtil.STATE_LAST,current,isplay);
                    ServeiceToSecond(ConstUtil.STATE_LAST,current,isplay,mod,mediaPlayer.getCurrentPosition());
                    break;
                case ConstUtil.STATE_NEXT:
                    if(mod ==2 ){
                        current = (int)(Math.random()*100)%musiclist.size();
                    }else {
                        if(current==len){
                            current = 0;
                        }else{
                            current++;
                        }
                    }
                    initMediaPlayer(current);
                    mediaPlayer.start();
                    isplay = true;
                    ServeiceToBroadcast(ConstUtil.STATE_NEXT,current,isplay);
                    ServeiceToSecond(ConstUtil.STATE_NEXT,current,isplay,mod,mediaPlayer.getCurrentPosition());
                    break;
                case ConstUtil.STATE_PRESS:
                    current = intent.getIntExtra("key",-1);
                    initMediaPlayer(current);
                    break;
                case ConstUtil.STATE_RENEW:
                    ServeiceToBroadcast(ConstUtil.STATE_RENEW, current, isplay);
                    handler.post(start);
                    break;
                case ConstUtil.STATE_MOD:
                   if(mod == 2){
                       mod = 0;
                   }else{
                       mod ++;
                   }
                    ServeiceToSecond(ConstUtil.STATE_MOD,current,isplay,mod,mediaPlayer.getCurrentPosition());
                    break;
//                case ConstUtil.STATE_AUTO:
//                    musicCompletion();
//                    ServeiceToBroadcast(ConstUtil.STATE_AUTO,current,isplay);
//                    break;
                default:
                    break;
            }

        }

    }

    Runnable start = new Runnable() {
        @Override
        public void run() {
            handler.post(update);
        }
    };

    Runnable update = new Runnable() {
        @Override
        public void run() {
            ServeiceToSecond(ConstUtil.STATE_RENEW, current, isplay, mod, mediaPlayer.getCurrentPosition());
            handler.postDelayed(update,1000);
        }
    };

    public  void musicCompletion(){
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(mod == 0){
                    if(current == musiclist.size()-1){
                        current = 0;
                    }else {
                        current++;
                    }
                }else if(mod == 1){

                }else if(mod == 2){
                    current = ((int)(Math.random()*100))%musiclist.size();
                }
                initMediaPlayer(current);
                mediaPlayer.start();
                isplay = true;
                ServeiceToBroadcast(ConstUtil.STATE_AUTO,current,isplay);
                ServeiceToSecond(ConstUtil.STATE_AUTO, current, isplay, mod,mediaPlayer.getCurrentPosition());
            }
        });
    }

    protected void onDestory(){
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(100);
    }

}
