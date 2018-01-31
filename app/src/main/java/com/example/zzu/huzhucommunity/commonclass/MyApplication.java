package com.example.zzu.huzhucommunity.commonclass;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import com.example.zzu.huzhucommunity.R;

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
     * 获取用户头像
     * @return 当用户未登录时返回drawable中profile_head文件（调试时用）
     *          否则返回
     */
    public static RoundedBitmapDrawable getUserHeadImageDrawable(){
        if(userId == -1){
            Resources resources = getContext().getResources();
            return RoundedBitmapDrawableFactory.create(resources, BitmapFactory.decodeResource(resources, R.drawable.profile_head));
        }
        else
            return userHeadImage;
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
