package com.example.zzu.huzhucommunity.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.asynchttp.AsyncHttpCallback;
import com.example.zzu.huzhucommunity.asynchttp.UserProfile;
import com.example.zzu.huzhucommunity.commonclass.ActivitiesCollector;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.commonclass.Utilities;
import com.example.zzu.huzhucommunity.customlayout.AccountProfileItemLayout;

import java.util.GregorianCalendar;
import java.util.HashMap;

public class OthersProfileActivity extends BaseActivity implements AsyncHttpCallback {
    private static final String USER_ID_EXTRA = "USER_ID";
    private String userID, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_profile_layout);
        setSupportActionBar((Toolbar)findViewById(R.id.OthersProfileActivity_toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        userID = getIntent().getStringExtra(USER_ID_EXTRA);

        UserProfile.getOurInstance().getUserProfile(userID, this);

        addListener(R.id.OthersProfileActivity_send_message_button);
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
                                if (userID != null && userName != null){
                                    ChatRoomActivity.startMe(OthersProfileActivity.this, userID, userName);
                                    finish();
                                }
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

    public static void startMe(Context context, String userID){
        Intent intent = new Intent(context, OthersProfileActivity.class);
        intent.putExtra(USER_ID_EXTRA, userID);
        context.startActivity(intent);
    }

    @Override
    public void onSuccess(int statusCode, HashMap<String, String> mp, int requestCode) {
        if (statusCode != 200){
            Toast.makeText(MyApplication.getContext(), Utilities.TOAST_NET_WORK_ERROR, Toast.LENGTH_SHORT).show();
            return;
        }
        switch (requestCode){
            case UserProfile.GET_USER_PROFILE:
                userName = mp.get(UserProfile.USER_NAME_JSON_KEY);
                String userSex = mp.get(UserProfile.USER_SEX_JSON_key);
                String userPhone = mp.get(UserProfile.USER_PHONE_JSON_KEY);
                String userGrade = mp.get(UserProfile.USER_GRADE_JSON_KEY);
                String userDept = mp.get(UserProfile.USER_DEPARTMENT_JSON_KEY);
                String userHead = mp.get(UserProfile.USER_HEAD_JSON_KEY);
                String userRegTime = mp.get(UserProfile.USER_REGISTER_TIME);

                ((CollapsingToolbarLayout) findViewById(R.id.OthersProfileActivity_collapsing_tool_bar)).setTitle(userName);
                AccountProfileItemLayout itemLayout = findViewById(R.id.OthersProfileActivity_user_name_view);
                itemLayout.setContent(userName);

                itemLayout = findViewById(R.id.OthersProfileActivity_sex_view);
                itemLayout.setImageDrawable(getDrawable(
                        (userSex.equals(Utilities.MALE)? R.drawable.male_icon: R.drawable.female_icon)));
                itemLayout = findViewById(R.id.OthersProfileActivity_phone_view);
                itemLayout.setContent(userPhone);
                itemLayout = findViewById(R.id.OthersProfileActivity_grade_view);
                itemLayout.setContent(userGrade);
                itemLayout = findViewById(R.id.OthersProfileActivity_department_view);
                itemLayout.setContent(userDept);
                itemLayout = findViewById(R.id.OthersProfileActivity_register_time_view);
                itemLayout.setContent(userRegTime);

                Utilities.getImageBitmapByUrl(userHead, new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        if (msg.what == Utilities.GET_IMAGE_BITMAP_BY_URL){
                            ((ImageView)findViewById(R.id.OthersProfileActivity_head_image_view)).
                                    setImageBitmap((Bitmap) msg.obj);
                            return true;
                        }
                        return false;
                    }
                }));
                break;
        }
    }

    @Override
    public void onError(int statusCode) {
        Toast.makeText(MyApplication.getContext(), Utilities.TOAST_NET_WORK_ERROR, Toast.LENGTH_SHORT).show();
    }
}
