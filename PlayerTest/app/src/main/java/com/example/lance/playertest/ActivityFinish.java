package com.example.lance.playertest;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lance on 16-10-5.
 */
public class ActivityFinish {

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivty(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        for(Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
