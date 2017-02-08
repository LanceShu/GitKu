package com.example.lance.xiyou_score;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import static com.example.lance.xiyou_score.Content.*;

/**
 * Created by Lance on 2017/2/6.
 */
public class ProjectAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Project_Infor> pro = new ArrayList<>();

    public ProjectAdapter(Context context){
        inflater = LayoutInflater.from(context);
        pro = project_list;
    }

    @Override
    public int getCount() {
        return project_list.size();
    }

    @Override
    public Object getItem(int position) {
        return project_list.get(position);
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
            convertView = inflater.inflate(R.layout.project_iteam,null);
            viewHolder.proId = (TextView) convertView.findViewById(R.id.p_id);
            viewHolder.proName = (TextView) convertView.findViewById(R.id.p_name);
            viewHolder.proScore = (TextView) convertView.findViewById(R.id.p_score);
            viewHolder.proGpa = (TextView) convertView.findViewById(R.id.p_gpa);
            viewHolder.proTeam = (TextView) convertView.findViewById(R.id.p_team);
            viewHolder.proStatus = (TextView) convertView.findViewById(R.id.p_status);
            viewHolder.pro_layout = (LinearLayout) convertView.findViewById(R.id.project_layout);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.proId.setText(project_list.get(position).course_id);
        viewHolder.proName.setText(project_list.get(position).course_name);
        viewHolder.proScore.setText(project_list.get(position).course_score);
        viewHolder.proGpa.setText(project_list.get(position).course_GPA);
        viewHolder.proTeam.setText(project_list.get(position).course_team);
        viewHolder.proStatus.setText(project_list.get(position).course_status);

        return convertView;
    }

    class ViewHolder{
        public TextView proId;
        public TextView proName;
        public TextView proScore;
        public TextView proGpa;
        public TextView proTeam;
        public TextView proStatus;
        public LinearLayout pro_layout;
    }
}
