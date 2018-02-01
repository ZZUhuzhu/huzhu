package com.example.zzu.huzhucommunity.commonclass;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FEI on 2018/2/1.
 * 活动管理器
 */

public class ActivitiesCollector {
    public static ArrayList<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public static void finishAll(){
        for (Activity activity: activities){
            if(activity != null)
                activity.finish();
        }
    }
}
