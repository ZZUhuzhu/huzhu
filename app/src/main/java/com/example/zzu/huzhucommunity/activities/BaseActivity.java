package com.example.zzu.huzhucommunity.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.zzu.huzhucommunity.commonclass.ActivitiesCollector;

/**
 * 基础Activity类
 * 与ActivityCollector类配合
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 启动活动时将活动添加到ActivityCollector中
     * @param savedInstanceState 保存的信息
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitiesCollector.addActivity(this);
    }
    /**
     * 销毁活动时将其从ActivityCollector中删除
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivitiesCollector.removeActivity(this);
    }
    /**
     * 为这个活动中的每个控件添加监听器
     * @param res 控件ID
     */
    public abstract void addListener(int res);
}
