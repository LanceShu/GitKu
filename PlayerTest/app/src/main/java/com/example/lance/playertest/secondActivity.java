package com.example.lance.playertest;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.Delayed;


/**
 * Created by lance on 16-10-4.
 */
public class secondActivity extends Activity implements View.OnClickListener,SeekBar.OnSeekBarChangeListener {

    private int mode = 0;
    private int i = 0;
    private boolean play;
    private long currenttime;

    private ImageView about;
    private TextView title;
    private ImageView musicImage;
    private SeekBar musicProgess;
    private ImageView last;
    private ImageView pause;
    private ImageView next;
    private ImageView back;
    private ImageView mod;
    private List<Music> musicList;
    private MusicSecondReciver musicSecondReciver;

    Handler handler = new Handler();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);              //去掉app界面的标题栏;
        setContentView(R.layout.second_menu);
        ActivityFinish.addActivty(this);

        musicList = MusicList.getMusicData(this);
        back = (ImageView) findViewById(R.id.back);
        about = (ImageView) findViewById(R.id.about);
        title = (TextView) findViewById(R.id.music_name_2);
        musicImage = (ImageView) findViewById(R.id.music_image_2);
        last = (ImageView) findViewById(R.id.music_last_2);
        pause = (ImageView) findViewById(R.id.music_pause_2);
        next = (ImageView) findViewById(R.id.music_next_2);
        mod = (ImageView) findViewById(R.id.music_mod);
        musicProgess = (SeekBar)findViewById(R.id.music_seekbar);

        back.setOnClickListener(this);
        last.setOnClickListener(this);
        pause.setOnClickListener(this);
        next.setOnClickListener(this);
        mod.setOnClickListener(this);
        about.setOnClickListener(this);
        musicProgess.setOnSeekBarChangeListener(this);

        musicSecondReciver = new MusicSecondReciver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConstUtil.MUSICSECOND_ACTION);
        registerReceiver(musicSecondReciver, intentFilter);

        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        sendSecondtoService(ConstUtil.STATE_RENEW, i);
    }

    protected void sendSecondtoService(int state, int i) {
        Intent intent = new Intent();
        intent.setAction(ConstUtil.MUSICSERVICE_ACTION);
        intent.putExtra("control", state);
        intent.putExtra("key", i);
        sendBroadcast(intent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser == true){
            Intent intent = new Intent();
            intent.setAction(ConstUtil.MUSICSERVICE_ACTION);
            intent.putExtra("control", ConstUtil.STATE_SEEKBAR);
            intent.putExtra("currenttime", progress);
            sendBroadcast(intent);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        sendSecondtoService(ConstUtil.STATE_PAUSE,i);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        sendSecondtoService(ConstUtil.STATE_PLAY,i);
    }


    class MusicSecondReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int current = intent.getIntExtra("current", -1);
            i = intent.getIntExtra("key", -1);
            play = intent.getBooleanExtra("boolean", false);
            mode = intent.getIntExtra("mode", -1);
            currenttime = intent.getLongExtra("currenttime",-1);
            renew(i, play, mode);
        }
    }

    public void renew(int i, boolean play, int mode) {
        title.setText(musicList.get(i).getName());
        Log.e("tsg--------------", mode + "");
        musicProgess.setMax((int) musicList.get(i).getTime());
        musicProgess.setProgress((int) currenttime);
        if (mode == 0) {
            mod.setImageResource(R.drawable.cicle_play);
        } else if (mode == 1) {
            mod.setImageResource(R.drawable.only_play);
        } else {
            mod.setImageResource(R.drawable.random_play);
        }
        Log.e("tag-----------------", "" + musicList.get(i).getSinger());
        if (play) {
            pause.setImageResource(R.drawable.pause);
        } else {
            pause.setImageResource(R.drawable.play);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.music_last_2:
                sendSecondtoService(ConstUtil.STATE_LAST, i);
                pause.setImageResource(R.drawable.pause);
                play = true;
                break;
            case R.id.music_pause_2:
                if (!play) {
                    sendSecondtoService(ConstUtil.STATE_PLAY, i);
                    pause.setImageResource(R.drawable.play);
                    play = true;
                } else {
                    sendSecondtoService(ConstUtil.STATE_PAUSE, i);
                    pause.setImageResource(R.drawable.pause);
                    play = false;
                }
                break;
            case R.id.music_next_2:
                sendSecondtoService(ConstUtil.STATE_NEXT, i);
                pause.setImageResource(R.drawable.pause);
                play = true;
                break;
            case R.id.music_mod:
                sendSecondtoService(ConstUtil.STATE_MOD, i);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.about:
                openOptionsMenu();  //打开menu的开关;
                break;
            default:
                break;
        }
    }

    //添加menu的item选项;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE,0,0,"退出系统");
        menu.add(Menu.NONE,1,1,"关于ME");
        return super.onCreateOptionsMenu(menu);
    }

    //实现menu的选择;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case 0:
                ActivityFinish.finishAll();
                NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                manager.cancel(100);
                System.exit(0);
                break;
            case 1:
                Toast.makeText(this,"Welcome To Use\n\nPower By Lance",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ActivityFinish.removeActivity(this);
        unregisterReceiver(musicSecondReciver);
    }

}
