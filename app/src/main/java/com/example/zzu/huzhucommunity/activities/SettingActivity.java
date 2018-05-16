package com.example.zzu.huzhucommunity.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.asynchttp.AsyncHttpCallback;
import com.example.zzu.huzhucommunity.asynchttp.Setting;
import com.example.zzu.huzhucommunity.commonclass.ActivitiesCollector;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.commonclass.Utilities;
import com.example.zzu.huzhucommunity.customlayout.SettingItemLayout;

import java.util.HashMap;

public class SettingActivity extends BaseActivity implements AsyncHttpCallback {

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
        addListener(R.id.SettingActivity_swipe_to_finish_on);

        SettingItemLayout itemLayout = findViewById(R.id.SettingActivity_new_message_notify_button);
        if (Utilities.GetSettingOption(Utilities.NEW_MESSAGE_NOTIFY_KEY))
            itemLayout.changeCheckStatus();
        itemLayout = findViewById(R.id.SettingActivity_record_track_button);
        if (Utilities.GetSettingOption(Utilities.RECORD_TRACK_KEY))
            itemLayout.changeCheckStatus();
        itemLayout = findViewById(R.id.SettingActivity_swipe_to_finish_on);
        if (Utilities.GetSettingOption(Utilities.RIGHT_SWIPE_BACK_KEY))
            itemLayout.changeCheckStatus();
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
                        itemLayout = findViewById(res);
                        Utilities.SaveSettingOption(Utilities.NEW_MESSAGE_NOTIFY_KEY, itemLayout.changeCheckStatus());
                        break;
                    case R.id.SettingActivity_record_track_button:
                        itemLayout = findViewById(res);
                        Utilities.SaveSettingOption(Utilities.RECORD_TRACK_KEY, itemLayout.changeCheckStatus());
                        break;
                    case R.id.SettingActivity_swipe_to_finish_on:
                        itemLayout = findViewById(res);
                        boolean tmp = itemLayout.changeCheckStatus();
                        Utilities.SaveSettingOption(Utilities.RIGHT_SWIPE_BACK_KEY, tmp);
                        BaseActivity.SetAllNotSwipeToFinish(!tmp);
                        break;
                    case R.id.SettingActivity_help_and_feedback_button:
                        FeedbackActivity.StartMe(SettingActivity.this);
                        break;
                    case R.id.SettingActivity_clear_cache_button:
                        clearCache();
                        break;
                    case R.id.SettingActivity_check_update_button:
                        //TODO update version code
                        Setting.getOurInstance().checkForUpdate("1", SettingActivity.this);
                        break;
                    case R.id.SettingActivity_about_us_button:
                        AboutUsActivity.StartMe(SettingActivity.this);
                        break;

                }
            }
        });
    }

    public void clearCache(){
        Toast.makeText(MyApplication.getContext(), "缓存已清除", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onSuccess(int statusCode, HashMap<String, String> mp, int requestCode) {
        Toast.makeText(MyApplication.getContext(), "您正在使用最新版的互助社区", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(int statusCode) {
        Toast.makeText(MyApplication.getContext(), Utilities.TOAST_NET_WORK_ERROR, Toast.LENGTH_SHORT).show();
    }
}
