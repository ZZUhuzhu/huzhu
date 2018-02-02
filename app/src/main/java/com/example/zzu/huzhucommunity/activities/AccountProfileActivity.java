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
import com.example.zzu.huzhucommunity.commonclass.ActivitiesCollector;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.customlayout.AccountProfileItemLayout;

public class AccountProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_profile_layout);
        Toolbar toolbar = findViewById(R.id.AccountProfileActivity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar =getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        addListener(R.id.AccountProfileActivity_head_image_view);
        addListener(R.id.AccountProfileActivity_user_name_view);
        addListener(R.id.AccountProfileActivity_sex_view);
        addListener(R.id.AccountProfileActivity_grade_view);
        addListener(R.id.AccountProfileActivity_department_view);
        addListener(R.id.AccountProfileActivity_register_time_view);
        addListener(R.id.AccountProfileActivity_last_login_time_view);
        addListener(R.id.AccountProfileActivity_update_password_view);
        addListener(R.id.AccountProfileActivity_exit_login_view);

        initImage();
    }
    public void initImage(){
        AccountProfileItemLayout itemLayout = findViewById(R.id.AccountProfileActivity_sex_view);
        itemLayout.setImageDrawable(getDrawable(R.drawable.female_icon));
        itemLayout = findViewById(R.id.AccountProfileActivity_head_image_view);
        itemLayout.setImageDrawable(getDrawable(R.drawable.profile_head));
        itemLayout = findViewById(R.id.AccountProfileActivity_user_name_view);
        itemLayout.setContent(getString(R.string.solider));
        itemLayout = findViewById(R.id.AccountProfileActivity_grade_view);
        itemLayout.setContent("2015");
        itemLayout = findViewById(R.id.AccountProfileActivity_department_view);
        itemLayout.setContent("信息工程学院");
        itemLayout = findViewById(R.id.AccountProfileActivity_register_time_view);
        itemLayout.setContent("2018/2/2 17:58");
        itemLayout = findViewById(R.id.AccountProfileActivity_last_login_time_view);
        itemLayout.setContent("2018/2/1 09:25");
    }
    /**
     * 为控件添加监听器
     * @param res 控件ID
     */
    public void addListener(final int res){
        findViewById(res).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (res){
                    case R.id.AccountProfileActivity_exit_login_view:
                        LoginActivity.startMe(AccountProfileActivity.this);
                        ActivitiesCollector.exitLogin();
                        break;
                    case R.id.AccountProfileActivity_head_image_view:
                    case R.id.AccountProfileActivity_user_name_view:
                    case R.id.AccountProfileActivity_sex_view:
                    case R.id.AccountProfileActivity_grade_view:
                    case R.id.AccountProfileActivity_department_view:
                    case R.id.AccountProfileActivity_register_time_view:
                    case R.id.AccountProfileActivity_last_login_time_view:
                    case R.id.AccountProfileActivity_update_password_view:
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
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public static void startMe(Context context){
        context.startActivity(new Intent(context, AccountProfileActivity.class));
    }
}
