package com.example.zzu.huzhucommunity.commonclass;

import android.app.Application;
import android.content.Context;

/**
 * Created by FEI on 2018/1/26.
 * 全局Context
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
