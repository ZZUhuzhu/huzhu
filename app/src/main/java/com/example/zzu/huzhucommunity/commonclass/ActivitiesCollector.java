package com.example.zzu.huzhucommunity.commonclass;

import android.app.Activity;

import com.example.zzu.huzhucommunity.activities.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FEI on 2018/2/1.
 * 活动管理器
 */

public class ActivitiesCollector {
    public static ArrayList<BaseActivity> activities = new ArrayList<>();

    public static void addActivity(BaseActivity activity){
        activities.add(activity);
    }
    public static void removeActivity(BaseActivity activity){
        activities.remove(activity);
    }
    public static void finishAll(){
        for (BaseActivity activity: activities){
            if(activity != null)
                activity.finish();
        }
    }
    public static void exitLogin(){
        for (int i = 0; i < activities.size(); i++){
            activities.get(i).finish();
        }
    }
}
