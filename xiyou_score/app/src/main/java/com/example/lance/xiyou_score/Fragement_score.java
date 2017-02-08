package com.example.lance.xiyou_score;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import static com.example.lance.xiyou_score.Content.*;


/**
 * Created by Lance on 2017/1/24.
 */
public class Fragement_score extends Fragment {
    private View rootView;
    private ListView listView;
    private ScoreAdapter scoreAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view2_score,container,false);
        listView = (ListView) rootView.findViewById(R.id.score_list);
        scoreAdapter = new ScoreAdapter(context);
        listView.setAdapter(scoreAdapter);
        return rootView;
    }
}
