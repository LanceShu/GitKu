package com.example.lance.xiyou_score;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.lance.xiyou_score.Content.*;

/**
 * Created by Lance on 2017/1/24.
 */
public class Fragement_course extends Fragment {
    private View rootView;
    private List<Map<String,Object>> datalist;
    private MyGridView gv;
    private SimpleAdapter adapter;
    private LinearLayout tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view1_course,container,false);
        gv = (MyGridView) rootView.findViewById(R.id.gv);
        tv = (LinearLayout) rootView.findViewById(R.id.course_t);

        datalist = new ArrayList<>();
        adapter = new SimpleAdapter(context,getData(),R.layout.course_item,new String[]{"text"},new int[]{R.id.course_text});
        gv.setAdapter(adapter);

        return rootView;
    }

    private List<Map<String,Object>> getData(){
        for(int i =0;i<courseList.size()-1;i++){
            Map<String,Object> map = new HashMap<>();
            if(courseList.get(i).length()==1){
                map.put("text"," ");
            }else{
//                tv.setBackgroundColor(Color.BLUE);
                map.put("text",courseList.get(i));
            }
            datalist.add(map);
        }
        return datalist;
    }


}
