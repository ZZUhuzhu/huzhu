package com.example.zzu.huzhucommunity.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.zzu.huzhucommunity.commonclass.ActivitiesCollector;
import com.example.zzu.huzhucommunity.commonclass.Utilities;

/**
 * 基础Activity类
 * 与ActivityCollector类配合
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static boolean AllNOTSwipeToFinish = false;
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
                if (e2.getX() - e1.getX() > Utilities.SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > Utilities.SWIPE_THRESHOLD_VELOCITY
                        && Math.abs(e2.getY() - e1.getY()) < Utilities.SWIPE_MAX_Y_DISTANCE) {
                    finish();
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!AllNOTSwipeToFinish && swipeToFinish && detector != null){
            if (detector.onTouchEvent(ev))
                return true;
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
     * 设置全局不能右滑返回
     */
    public static void SetAllNotSwipeToFinish(boolean allNOTSwipeToFinish){
        AllNOTSwipeToFinish = allNOTSwipeToFinish;
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
