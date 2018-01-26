package com.example.zzu.huzhucommunity.activities;

import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;

public class UserProfileActivity extends AppCompatActivity {
    private static final String TAG = "UserProfileActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_layout);
        Toolbar toolbar = findViewById(R.id.UserProfile_me_top_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        addListener(R.id.UserProfile_resource_published_item);
        addListener(R.id.UserProfile_resource_received_item);
        addListener(R.id.UserProfile_setting_item);
        addListener(R.id.UserProfile_star_item);
        addListener(R.id.UserProfile_track_item);
        addListener(R.id.UserProfile_comment_item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu_item, menu);
        return true;
    }

    /**
     * 为每个控件添加监听器
     * @param res 控件ID
     */
    public void addListener(final int res){
        Log.e(TAG, "addListener: " + findViewById(res).isClickable());
        findViewById(res).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (res){
                    case R.id.UserProfile_resource_published_item:
                    case R.id.UserProfile_resource_received_item:
                    case R.id.UserProfile_setting_item:
                    case R.id.UserProfile_star_item:
                    case R.id.UserProfile_track_item:
                    case R.id.UserProfile_comment_item:
                        Toast.makeText(UserProfileActivity.this, "正在全力开发中...", Toast.LENGTH_SHORT).show();
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
            case R.id.Setting_menu_item:
                findViewById(R.id.UserProfile_setting_item).performClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
