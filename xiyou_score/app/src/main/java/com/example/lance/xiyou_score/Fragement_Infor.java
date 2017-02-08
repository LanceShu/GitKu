package com.example.lance.xiyou_score;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import static com.example.lance.xiyou_score.Content.*;
import static com.example.lance.xiyou_score.Content.*;

/**
 * Created by Lance on 2017/2/5.
 */
public class Fragement_Infor extends Fragment {

    private View rootview;
    private TextView stuName;
    private TextView stuID;
    private TextView stuAcademy;
    private TextView stuMajor;
    private TextView stuClass;
    private TextView stuEducation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.person_infor,container,false);
        stuID = (TextView) rootview.findViewById(R.id.stuID);
        stuName = (TextView) rootview.findViewById(R.id.stuName);
        stuAcademy = (TextView) rootview.findViewById(R.id.stuAcademy);
        stuMajor = (TextView) rootview.findViewById(R.id.stuMajor);
        stuClass = (TextView) rootview.findViewById(R.id.stuClass);
        stuEducation = (TextView) rootview.findViewById(R.id.stuEducation);

        stuID.setText(stuid);
        stuName.setText(stuname);
        stuAcademy.setText(stuacademy);
        stuMajor.setText(stumajor);
        stuClass.setText(stuclass);
        stuEducation.setText(stueducation);

        return rootview;
    }
}
