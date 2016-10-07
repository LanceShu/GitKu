package com.example.lance.playertest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by lance on 16-9-29.
 */

//i音乐适配器存放歌曲信息;

public class MusicAdapter extends ArrayAdapter<Music>{

    private int resourceId;                                                         //当前音乐的位置;
    public MusicAdapter(Context context, int resource, List<Music> objects) {
        super(context, resource, objects);
        resourceId = resource;                                                      //获取当前音乐的位置;
    }

    //获取被选择的的音乐的信息;
    public View getView(int position,View convertView,ViewGroup parent){
        Music music = getItem(position);                                            //获取当前被选择的音乐信息;
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);    //
        ImageView musicImage = (ImageView)view.findViewById(R.id.music_image);
        TextView musicName = (TextView)view.findViewById(R.id.music_text);
        TextView musicSinger = (TextView)view.findViewById(R.id.music_singer);
        musicImage.setImageResource(R.mipmap.musicsinger);
        String[] a = music.getName().split("-");
        musicName.setText(a[1]);
        musicSinger.setText(music.getSinger());
        return view;
    }
}
