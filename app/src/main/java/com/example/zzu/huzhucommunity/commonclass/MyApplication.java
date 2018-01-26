package com.example.zzu.huzhucommunity.commonclass;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by FEI on 2018/1/26.
 * 全局Context
 */

public class MyApplication extends Application {
    private static Context context;
    public static int userId = -1;
    public static String userName, userHead;
    public static RoundedBitmapDrawable userHeadImage;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }

    public static void setUser(int userId, String userName, String userHead){
        MyApplication.userId = userId;
        MyApplication.userName = userName;
        MyApplication.userHead = userHead;
        downloadUserHeadImage(null);
    }

    /**
     * 下载用户的头像图片
     */
    public static void downloadUserHeadImage(final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL userHeadUrl = new URL(MyApplication.userHead);
                    HttpURLConnection connection = (HttpURLConnection)userHeadUrl.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setDoInput(true);
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        userHeadImage = RoundedBitmapDrawableFactory.create(getContext().getResources(), connection.getInputStream());
                        userHeadImage.setCircular(true);
                        if(handler != null){
                            Message msg = new Message();
                            msg.what = Constants.UserProfileUserHeadImageGot;
                            handler.sendMessage(msg);
                        }
                    }
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
