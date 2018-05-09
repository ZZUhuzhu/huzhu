package com.example.zzu.huzhucommunity.activities;

import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;

import com.example.zzu.huzhucommunity.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity_layout);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        CountDownTimer countDownTimer = new CountDownTimer(1000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                LoginActivity.startMe(SplashActivity.this);
                finish();
            }
        };
        countDownTimer.start();
    }

    @Override
    public void addListener(final int res) {}
}
