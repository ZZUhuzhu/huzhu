package com.example.zzu.huzhucommunity.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.commonclass.ActivitiesCollector;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.commonclass.Utilities;
import com.example.zzu.huzhucommunity.customlayout.AccountProfileItemLayout;

import java.util.GregorianCalendar;

public class OthersProfileActivity extends BaseActivity {
    private static final String USER_ID_EXTRA = "USER_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_profile_layout);
        setSupportActionBar((Toolbar)findViewById(R.id.OthersProfileActivity_toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        addListener(R.id.OthersProfileActivity_send_message_button);

        initUserInfo();
    }

    public void initUserInfo(){
        AccountProfileItemLayout itemLayout = findViewById(R.id.OthersProfileActivity_user_name_view);
        itemLayout.setContent(getString(R.string.solider));
        itemLayout = findViewById(R.id.OthersProfileActivity_sex_view);
        itemLayout.setImageDrawable(getDrawable(R.drawable.female_icon));
        itemLayout = findViewById(R.id.OthersProfileActivity_phone_view);
        itemLayout.setContent("18655986532");
        itemLayout = findViewById(R.id.OthersProfileActivity_grade_view);
        itemLayout.setContent("2016");
        itemLayout = findViewById(R.id.OthersProfileActivity_department_view);
        itemLayout.setContent("建筑学院");
        itemLayout = findViewById(R.id.OthersProfileActivity_register_time_view);
        itemLayout.setContent(Utilities.convertTimeInMillToString(GregorianCalendar.getInstance().getTimeInMillis()));
    }
    @Override
    public void addListener(final int res) {
        findViewById(res).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (res){
                    case R.id.OthersProfileActivity_send_message_button:
                        int sz = ActivitiesCollector.activities.size();
                        if (sz >= 2) {
                            BaseActivity activity = ActivitiesCollector.activities.get(sz - 2);
                            if (activity != null && activity instanceof ChatRoomActivity){
                                finish();
                            }
                            else {
                                ChatRoomActivity.startMe(OthersProfileActivity.this);
                                finish();
                            }
                        }
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

    public static void startMe(Context context, int userID){
        Intent intent = new Intent(context, OthersProfileActivity.class);
        intent.putExtra(USER_ID_EXTRA, userID);
        context.startActivity(intent);
    }

}
