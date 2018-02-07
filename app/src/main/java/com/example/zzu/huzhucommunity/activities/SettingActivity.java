package com.example.zzu.huzhucommunity.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.commonclass.ActivitiesCollector;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.customlayout.SettingItemLayout;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_layout);
        Toolbar toolbar = findViewById(R.id.SettingActivity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        addListener(R.id.SettingActivity_new_message_notify_button);
        addListener(R.id.SettingActivity_record_track_button);
        addListener(R.id.SettingActivity_clear_cache_button);
        addListener(R.id.SettingActivity_check_update_button);
        addListener(R.id.SettingActivity_about_us_button);
        addListener(R.id.SettingActivity_help_and_feedback_button);
        addListener(R.id.SettingActivity_exit_button);
    }
    public void addListener(final int res){
        findViewById(res).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingItemLayout itemLayout;
                switch (res){
                    case R.id.SettingActivity_exit_button:
                        ActivitiesCollector.finishAll();
                        break;
                    case R.id.SettingActivity_new_message_notify_button:
                        itemLayout = findViewById(R.id.SettingActivity_new_message_notify_button);
                        itemLayout.changeCheckStatus();
                        break;
                    case R.id.SettingActivity_record_track_button:
                        itemLayout = findViewById(R.id.SettingActivity_record_track_button);
                        itemLayout.changeCheckStatus();
                        break;
                    case R.id.SettingActivity_clear_cache_button:
                    case R.id.SettingActivity_check_update_button:
                    case R.id.SettingActivity_about_us_button:
                    case R.id.SettingActivity_help_and_feedback_button:
                        Toast.makeText(MyApplication.getContext(), "正在全力开发中...", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public static void startMe(Context context){
        context.startActivity(new Intent(context, SettingActivity.class));
    }
}
