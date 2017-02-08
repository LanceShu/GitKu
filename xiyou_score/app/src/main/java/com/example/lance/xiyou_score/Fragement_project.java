package com.example.lance.xiyou_score;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import static com.example.lance.xiyou_score.Content.*;


/**
 * Created by Lance on 2017/1/24.
 */
public class Fragement_project extends Fragment {
    private View rootView;

    private ListView pro_list;
    private ProjectAdapter proAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view3_project,container,false);
        pro_list = (ListView) rootView.findViewById(R.id.pro_list);

        proAdapter = new ProjectAdapter(context);
        pro_list.setAdapter(proAdapter);

        return rootView;
    }
}
