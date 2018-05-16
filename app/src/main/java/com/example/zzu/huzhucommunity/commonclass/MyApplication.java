package com.example.zzu.huzhucommunity.commonclass;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.example.zzu.huzhucommunity.activities.BaseActivity;

/**
 * Created by FEI on 2018/1/26.
 * 全局Context
 */

public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        if (!Utilities.GetSettingOption(Utilities.RIGHT_SWIPE_BACK_KEY))
            BaseActivity.SetAllNotSwipeToFinish(true);
        else
            BaseActivity.SetAllNotSwipeToFinish(false);
    }
    public static Context getContext(){
        return context;
    }
}
