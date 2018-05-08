package com.example.zzu.huzhucommunity.commonclass;

import android.app.Activity;
import android.content.Context;

import com.example.zzu.huzhucommunity.activities.BaseActivity;
import com.example.zzu.huzhucommunity.activities.LoginActivity;

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

    /**
     * 结束所有活动
     */
    public static void finishAll(){
        for (BaseActivity activity: activities){
            if(activity != null)
                activity.finish();
        }
    }

    /**
     * 退出程序并回到登录界面
     */
    public static void exitLogin(Context context){
        LoginActivity.startMe(context);
        for (int i = 0; i < activities.size(); i++){
            activities.get(i).finish();
        }
    }
}
