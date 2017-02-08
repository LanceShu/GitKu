package com.example.lance.xiyou_score;

/**
 * Created by Lance on 2017/2/6.
 */
public class Project_Infor {
    public String course_id;
    public String course_name;
    public String course_score;
    public String course_GPA;
    public String course_team;
    public String course_status;

    public Project_Infor(String id,String name,String score,String GPA,String team,String status){
        course_id = id;
        course_name = name;
        course_score = score;
        course_GPA = GPA;
        course_team = team;
        course_status = status;
    }
}
