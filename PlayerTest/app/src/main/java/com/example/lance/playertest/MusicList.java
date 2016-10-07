package com.example.lance.playertest;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.lance.playertest.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lance on 16-9-24.
 */
public class MusicList {
    public static List<Music> getMusicData(Context context){
        List<Music> musicList = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        if(cr !=null){
            Cursor cursor = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
            if(cursor==null){
                return null;
            }
            if(cursor.moveToFirst()){
                do{
                    Music m = new Music();
                    String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    if(singer.equals("<unknown>")){
                        singer="位置艺术家";
                    }
                    String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    long time = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                    long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));

                    m.setAlbum(album);
                    m.setUrl(url);
                    m.setName(name);
                    m.setSinger(singer);
                    m.setSize(size);
                    m.setTime(time);
                    m.setTitle(title);
                    musicList.add(m);
                }while(cursor.moveToNext());
            }
        }
        return musicList;
    }
}
