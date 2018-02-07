package com.example.zzu.huzhucommunity.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.zzu.huzhucommunity.commonclass.ActivitiesCollector;
import com.example.zzu.huzhucommunity.commonclass.Constants;

/**
 * 基础Activity类
 * 与ActivityCollector类配合
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private boolean swipeToFinish = true;
    private GestureDetector detector;

    /**
     * 启动活动时将活动添加到ActivityCollector中
     * @param savedInstanceState 保存的信息
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitiesCollector.addActivity(this);
        detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                // left to right swipe
                if (e2.getX() - e1.getX() > Constants.SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > Constants.SWIPE_THRESHOLD_VELOCITY
                        && Math.abs(e2.getY() - e1.getY()) < Constants.SWIPE_MAX_Y_DISTANCE) {
                    finish();
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
        Log.d(TAG, "onCreate: " + getLocalClassName());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (swipeToFinish){
            return detector.onTouchEvent(ev) || super.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * BaseActivity默认右滑手势为退出
     * 该方法用于关闭右滑退出活动功能
     */
    protected final void setSwipeToFinishOff(){
        swipeToFinish = false;
    }
    /**
     * 销毁活动时将其从ActivityCollector中删除
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivitiesCollector.removeActivity(this);
        Log.d(TAG, "onDestroy: " + getLocalClassName());
    }
    /**
     * 为这个活动中的每个控件添加监听器
     * @param res 控件ID
     */
    public abstract void addListener(int res);
}
