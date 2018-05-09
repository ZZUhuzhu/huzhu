package com.example.zzu.huzhucommunity.activities;

import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.asynchttp.AshncHttpCallbackImplemnet;
import com.example.zzu.huzhucommunity.asynchttp.Main;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity_layout);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        //wjsay modify
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Main.getOurInstance().getRequest("1", new AshncHttpCallbackImplemnet());
//            }
//        }).start();

        CountDownTimer countDownTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                jumpToNextActivity();
            }
        };
        countDownTimer.start();
    }

    /**
     * 登录或者启动主活动（如果已经保存登录信息）
     */
    private void jumpToNextActivity(){
        int tmp = ((int) (Math.random() * 100));
        if (tmp > 50)
            LoginActivity.startMe(this);
        else
            MainActivity.startMe(this);
        finish();
    }

    @Override
    public void addListener(final int res) {}

}
