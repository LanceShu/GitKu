package com.example.lance.xiyou_score;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import static com.example.lance.xiyou_score.Content.*;

/**
 * Created by Lance on 2017/2/7.
 */
public class ScoreAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Score_Infor> score_infors;

    public ScoreAdapter(Context context){
        inflater = LayoutInflater.from(context);
        score_infors = score_inforList;
    }

    @Override
    public int getCount() {
        return score_inforList.size();
    }

    @Override
    public Object getItem(int position) {
        return score_inforList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.score_item,null);
            viewHolder.s_Name = (TextView) convertView.findViewById(R.id.s_name);
            viewHolder.s_Team = (TextView) convertView.findViewById(R.id.s_team);
            viewHolder.s_Gpa = (TextView) convertView.findViewById(R.id.s_gpa);
            viewHolder.s_Score = (TextView) convertView.findViewById(R.id.s_score);
            viewHolder.s_Chengji = (TextView) convertView.findViewById(R.id.s_chengji);
            viewHolder.s_Place = (TextView) convertView.findViewById(R.id.s_place);
            viewHolder.score_Layout = (LinearLayout) convertView.findViewById(R.id.score_layout);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.s_Name.setText(score_inforList.get(position).sName);
        viewHolder.s_Team.setText(score_inforList.get(position).sTeam);
        viewHolder.s_Gpa.setText(score_inforList.get(position).sGpa);
        viewHolder.s_Score.setText(score_inforList.get(position).sScore);
        viewHolder.s_Chengji.setText(score_inforList.get(position).sChengji);
        viewHolder.s_Place.setText(score_inforList.get(position).sPlace);
        return convertView;
    }

    class ViewHolder{
        public TextView s_Name;
        public TextView s_Team;
        public TextView s_Gpa;
        public TextView s_Score;
        public TextView s_Chengji;
        public  TextView s_Place;
        public LinearLayout score_Layout;
    }
}
