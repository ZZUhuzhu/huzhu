package com.example.zzu.huzhucommunity.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.asynchttp.AsyncHttpCallback;
import com.example.zzu.huzhucommunity.asynchttp.Profile;
import com.example.zzu.huzhucommunity.commonclass.ActivitiesCollector;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.commonclass.Utilities;
import com.example.zzu.huzhucommunity.customlayout.AccountProfileItemLayout;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;


public class AccountProfileActivity extends BaseActivity implements AsyncHttpCallback {
    private static final String TAG = "AccountProfileActivity";
    /**
     * 标识不同更新活动的请求码
     * 开启新活动更新个人信息时用
     */
    private static final int REQUEST_USER_NAME = 0;
    private static final int REQUEST_USER_PHONE= 1;
    private static final int REQUEST_USER_GRADE = 2;
    private static final int REQUEST_USER_DEPARTMENT = 3;

    /**
     * 标识不同网络请求的请求码
     * 与相关网络连接类 {@link Profile}配合使用
     */
    public static final int REQUEST_CODE_GET_ACCOUNT_PROFILE = 11;
    public static final int REQUEST_CODE_UPDATE_ACCOUNT_PROFILE = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_profile_layout);
        Toolbar toolbar = findViewById(R.id.AccountProfileActivity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar =getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        addListener(R.id.AccountProfileActivity_sex_view);
        addListener(R.id.AccountProfileActivity_grade_view);
        addListener(R.id.AccountProfileActivity_user_name_view);
        addListener(R.id.AccountProfileActivity_phone_view);
        addListener(R.id.AccountProfileActivity_department_view);
        addListener(R.id.AccountProfileActivity_update_password_view);
        addListener(R.id.AccountProfileActivity_exit_login_view);
        addListener(R.id.AccountProfileActivity_head_image_view);
        addListener(R.id.AccountProfileActivity_expanded_holder);
        addListener(R.id.AccountProfileActivity_expanded_image_view);

        initImage();
    }

    /**
     * 从服务器获取用户账户信息
     */
    public void initImage(){
        int userID = Utilities.GetLoginUserId();
        if (userID == Utilities.USER_NOT_FOUND){
            Toast.makeText(MyApplication.getContext(), "出问题了，请重新登录", Toast.LENGTH_SHORT).show();
            ActivitiesCollector.exitLogin(this);
        }
        Profile.getOurInstance().getAccountProfile("" + userID, this);
        //todo on success 更新

    }
    /**
     * 为控件添加监听器
     * @param res 控件ID
     */
    public void addListener(final int res){
        findViewById(res).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AccountProfileItemLayout itemLayout;
                switch (res){
                    case R.id.AccountProfileActivity_exit_login_view:
                        ActivitiesCollector.exitLogin(AccountProfileActivity.this);
                        break;
                    case R.id.AccountProfileActivity_user_name_view:
                        itemLayout = findViewById(res);
                        ProfileUpdateActivity.startMe(AccountProfileActivity.this,
                                ProfileUpdateActivity.TYPE_USER_NAME, REQUEST_USER_NAME, itemLayout.getContentText());
                        break;
                    case R.id.AccountProfileActivity_phone_view:
                        itemLayout = findViewById(res);
                        ProfileUpdateActivity.startMe(AccountProfileActivity.this,
                                ProfileUpdateActivity.TYPE_PHONE, REQUEST_USER_PHONE, itemLayout.getContentText());
                        break;
                    case R.id.AccountProfileActivity_grade_view:
                        itemLayout = findViewById(res);
                        ProfileUpdateActivity.startMe(AccountProfileActivity.this,
                                ProfileUpdateActivity.TYPE_GRADE, REQUEST_USER_GRADE, itemLayout.getContentText());
                        break;
                    case R.id.AccountProfileActivity_department_view:
                        itemLayout = findViewById(res);
                        ProfileUpdateActivity.startMe(AccountProfileActivity.this,
                                ProfileUpdateActivity.TYPE_DEPARTMENT, REQUEST_USER_DEPARTMENT, itemLayout.getContentText());
                        break;
                    case R.id.AccountProfileActivity_head_image_view:
                        headImageExpandedShow();
                        break;
                    case R.id.AccountProfileActivity_expanded_image_view:
                    case R.id.AccountProfileActivity_expanded_holder:
                        headImageExpandedDisappear();
                        break;
                    case R.id.AccountProfileActivity_sex_view:
                        new AlertDialog.Builder(AccountProfileActivity.this)
                                .setCancelable(true)
                                .setItems(R.array.sexChoice, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AccountProfileItemLayout itemLayout1 = findViewById(res);
                                        if (which == 0){
                                            itemLayout1.setImageDrawable(getDrawable(R.drawable.male_icon));
                                            updateProfile();
                                            dialog.dismiss();
                                        }
                                        else if (which == 1){
                                            itemLayout1.setImageDrawable(getDrawable(R.drawable.female_icon));
                                            updateProfile();
                                            dialog.dismiss();
                                        }
                                    }
                                })
                                .setTitle(R.string.sex)
                                .show();
                    case R.id.AccountProfileActivity_update_password_view:
                        Toast.makeText(MyApplication.getContext(), "正在全力开发中...", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    //todo 更新信息
    public void updateProfile(){
        Toast.makeText(MyApplication.getContext(), "正在全力开发中...", Toast.LENGTH_SHORT).show();
    }
    /**
     * 显示头像大图
     */
    public void headImageExpandedShow(){
        View view = findViewById(R.id.AccountProfileActivity_expanded_holder);
        view.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏头像大图
     */
    public void headImageExpandedDisappear(){
        View view = findViewById(R.id.AccountProfileActivity_expanded_holder);
        view.setVisibility(View.GONE);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(new ScaleAnimation(1, 1.5f, 1, 1.5f, size.x / 2, size.y / 2));
        animationSet.addAnimation(new AlphaAnimation(1, 0));
        animationSet.setDuration(200);
        view.startAnimation(animationSet);
    }

    @Override
    public void onBackPressed() {
        View view = findViewById(R.id.AccountProfileActivity_expanded_holder);
        if (view.getVisibility() == View.VISIBLE)
            headImageExpandedDisappear();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.account_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.account_profile_menu_item_change_image:
                Utilities.startPickImageDialog(AccountProfileActivity.this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 用户更新账户信息时返回本活动调用
     *
     * @param requestCode 请求码，标识不同的更新目标
     * @param resultCode 结果，判断用户是否进行了更新
     * @param data 返回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case Utilities.PICK_IMAGE_FROM_CAMERA:
            case Utilities.PICK_IMAGE_FROM_GALLERY:
                Bitmap bitmap = Utilities.getImageFromDialog(requestCode, resultCode, data);
                if (bitmap != null){
                    ((ImageView) findViewById(R.id.AccountProfileActivity_head_image_view)).setImageBitmap(bitmap);
                    ((ImageView) findViewById(R.id.AccountProfileActivity_expanded_image_view)).setImageBitmap(bitmap);
                }
                break;
            case REQUEST_USER_NAME:
            case REQUEST_USER_PHONE:
            case REQUEST_USER_GRADE:
            case REQUEST_USER_DEPARTMENT:
                updateProfile();
                break;
        }
    }

    /**
     * 获取用户账户信息，更新用户账户信息时网络请求的回调函数
     * @param statusCode 返回状态
     * @param mp 存储需要传递数据的哈希表，若无数据则 mp 为 null
     * @param requestCode 请求码，标识同一个activity的不同网络请求
     *                    {@link #REQUEST_CODE_GET_ACCOUNT_PROFILE}: 从服务器拉取用户账户信息
     *                    {@link #REQUEST_CODE_UPDATE_ACCOUNT_PROFILE}: 更新用户信息
     */
    @Override
    public void onSuccess(int statusCode, HashMap<String, String> mp, int requestCode) {
        switch (requestCode){
            case REQUEST_CODE_GET_ACCOUNT_PROFILE:
                if (statusCode != 200){
                    onError(statusCode);
                    break;
                }
                AccountProfileItemLayout itemLayout = findViewById(R.id.AccountProfileActivity_sex_view);

                String userHeadUri = mp.get(Profile.GET_ACCOUNT_PROFILE_USER_HEAD_KEY);
                //todo 设置头像
                Log.e(TAG, "onSuccess: " + Uri.parse(userHeadUri));
                ((ImageView) findViewById(R.id.AccountProfileActivity_head_image_view)).setImageURI();
                ((ImageView) findViewById(R.id.AccountProfileActivity_expanded_image_view)).
                        setImageURI(Uri.parse(userHeadUri));
                if (mp.get(Profile.GET_ACCOUNT_PROFILE_USER_SEX_KEY).equals(Utilities.FEMALE))
                    itemLayout.setImageDrawable(getDrawable(R.drawable.female_icon));
                else
                    itemLayout.setImageDrawable(getDrawable(R.drawable.male_icon));
                itemLayout = findViewById(R.id.AccountProfileActivity_user_name_view);
                itemLayout.setContent(mp.get(Profile.GET_ACCOUNT_PROFILE_USER_NAME_KEY));
                itemLayout = findViewById(R.id.AccountProfileActivity_grade_view);
                itemLayout.setContent(mp.get(Profile.GET_ACCOUNT_PROFILE_USER_GRADE_KEY));
                itemLayout = findViewById(R.id.AccountProfileActivity_department_view);
                itemLayout.setContent(mp.get(Profile.GET_ACCOUNT_PROFILE_USER_DEPT_KEY));
                itemLayout = findViewById(R.id.AccountProfileActivity_register_time_view);
                itemLayout.setContent(mp.get(Profile.GET_ACCOUNT_PROFILE_USER_REG_TIME_KEY));
                itemLayout = findViewById(R.id.AccountProfileActivity_last_login_time_view);
                itemLayout.setContent(mp.get(Profile.GET_ACCOUNT_PROFILE_LOGIN_TIME_KEY));
                itemLayout = findViewById(R.id.AccountProfileActivity_phone_view);
                itemLayout.setContent(mp.get(Profile.GET_ACCOUNT_PROFILE_USER_PHONE_KEY));
                break;
            case REQUEST_CODE_UPDATE_ACCOUNT_PROFILE:
                break;
        }
    }

    @Override
    public void onError(int statusCode) {
        Toast.makeText(MyApplication.getContext(), "获取信息失败，请检查网络连接", Toast.LENGTH_SHORT).show();
    }


    public static void startMe(Context context){
        context.startActivity(new Intent(context, AccountProfileActivity.class));
    }
}
