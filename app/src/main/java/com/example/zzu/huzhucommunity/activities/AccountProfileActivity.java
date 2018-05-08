package com.example.zzu.huzhucommunity.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zzu.huzhucommunity.R;
import com.example.zzu.huzhucommunity.asynchttp.AsyncHttpCallback;
import com.example.zzu.huzhucommunity.asynchttp.UserProfile;
import com.example.zzu.huzhucommunity.commonclass.ActivitiesCollector;
import com.example.zzu.huzhucommunity.commonclass.Constants;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.commonclass.Utilities;
import com.example.zzu.huzhucommunity.customlayout.AccountProfileItemLayout;

import java.util.GregorianCalendar;
import java.util.HashMap;


public class AccountProfileActivity extends BaseActivity implements AsyncHttpCallback {
    private static final int REQUEST_USER_NAME = 0;
    private static final int REQUEST_USER_PHONE= 1;
    private static final int REQUEST_USER_GRADE = 2;
    private static final int REQUEST_USER_DEPARTMENT = 3;

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
        AccountProfileItemLayout itemLayout = findViewById(R.id.AccountProfileActivity_sex_view);
        //todo 用户账户信息
        int userID = Utilities.GetLoginUserId();
        if (userID == Utilities.USER_NOT_FOUND){
            Toast.makeText(MyApplication.getContext(), "出问题了，请重新登录", Toast.LENGTH_SHORT).show();
            ActivitiesCollector.exitLogin(this);
        }
        UserProfile.getOurInstance().getUserProfile("" + userID, this);
        itemLayout.setImageDrawable(getDrawable(R.drawable.female_icon));
        itemLayout = findViewById(R.id.AccountProfileActivity_user_name_view);
        itemLayout.setContent(getString(R.string.solider));
        itemLayout = findViewById(R.id.AccountProfileActivity_grade_view);
        itemLayout.setContent("2015");
        itemLayout = findViewById(R.id.AccountProfileActivity_department_view);
        itemLayout.setContent("信息工程学院");
        itemLayout = findViewById(R.id.AccountProfileActivity_register_time_view);
        itemLayout.setContent(Utilities.convertTimeInMillToString(GregorianCalendar.getInstance().getTimeInMillis()));
        itemLayout = findViewById(R.id.AccountProfileActivity_last_login_time_view);
        itemLayout.setContent(Utilities.convertTimeInMillToString(GregorianCalendar.getInstance().getTimeInMillis()));
        itemLayout = findViewById(R.id.AccountProfileActivity_phone_view);
        itemLayout.setContent("15766988562");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case Constants.PICK_IMAGE_FROM_CAMERA:
            case Constants.PICK_IMAGE_FROM_GALLERY:
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

    public static void startMe(Context context){
        context.startActivity(new Intent(context, AccountProfileActivity.class));
    }

    @Override
    public void onSuccess(int code, HashMap<String, String> mp) {
        //todo 成功
    }

    @Override
    public void onError(int code) {
    }
}
