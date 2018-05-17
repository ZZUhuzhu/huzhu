package com.example.zzu.huzhucommunity.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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
import com.example.zzu.huzhucommunity.asynchttp.Publish;
import com.example.zzu.huzhucommunity.commonclass.ActivitiesCollector;
import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.commonclass.Utilities;
import com.example.zzu.huzhucommunity.customlayout.AccountProfileItemLayout;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import static com.example.zzu.huzhucommunity.asynchttp.Profile.GET_ACCOUNT_PROFILE;


public class AccountProfileActivity extends BaseActivity implements AsyncHttpCallback {
    private static final String USER_HEAD_IMG_NAME_SUFFIX = "_avatar";
    /**
     * 标识不同更新活动的请求码
     * 开启新活动更新个人信息时用
     */
    private static final int REQUEST_USER_NAME = Utilities.SAVE_USER_PROFILE_TARGET_USER_NAME;
    private static final int REQUEST_USER_PHONE= Utilities.SAVE_USER_PROFILE_TARGET_USER_PHONE;
    private static final int REQUEST_USER_GRADE = Utilities.SAVE_USER_PROFILE_TARGET_USER_GRADE;
    private static final int REQUEST_USER_DEPARTMENT = Utilities.SAVE_USER_PROFILE_TARGET_USER_DEPARTMENT;

    private static final String UPDATE_PROFILE_SUCCESS = "修改成功";

    private static final String UPDATE_HEAD_TARGET = "head";

    /**
     * 用于通过网络获取用户头像的 handler 以及 msg.what 值
     */
    private static final int MESSAGE_GET_USER_HEAD = 21;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_GET_USER_HEAD:
                    if (msg.obj == null)
                        Toast.makeText(MyApplication.getContext(), Utilities.TOAST_NET_WORK_ERROR, Toast.LENGTH_SHORT).show();
                    else{
                        Bitmap bitmap = (Bitmap) msg.obj;
                        Utilities.SaveLoginUserHeadBitmap(bitmap);
                        ((ImageView) findViewById(R.id.AccountProfileActivity_head_image_view)).setImageBitmap(bitmap);
                        ((ImageView) findViewById(R.id.AccountProfileActivity_expanded_image_view)).setImageBitmap(bitmap);

                    }
                    break;
            }
            return false;
        }
    });

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

        initUserAccountProfile();
    }

    /**
     * 从服务器获取用户账户信息
     */
    public void initUserAccountProfile(){
        int userID = Utilities.GetLoginUserId();
        if (userID == Integer.parseInt(Utilities.USER_NOT_FOUND)){
            Toast.makeText(MyApplication.getContext(), "出问题了，请重新登录", Toast.LENGTH_SHORT).show();
            ActivitiesCollector.exitLogin(this);
        }
        //用户信息已经存储，则直接读取，否则通过网络获取
        if (Utilities.IsLoginUserAccountProfileStored()){
            HashMap<String, String> mp = Utilities.GetLoginUserAccountProfile();
            AccountProfileItemLayout itemLayout = findViewById(R.id.AccountProfileActivity_sex_view);
            if (mp.get(Profile.GET_ACCOUNT_PROFILE_USER_SEX_KEY).equals(Utilities.FEMALE))
                itemLayout.setImageDrawable(getDrawable(R.drawable.female_icon));
            else
                itemLayout.setImageDrawable(getDrawable(R.drawable.male_icon));
            itemLayout = findViewById(R.id.AccountProfileActivity_user_name_view);
            itemLayout.setContent(mp.get(Profile.GET_ACCOUNT_PROFILE_USER_NAME_KEY));
            ((CollapsingToolbarLayout) findViewById(R.id.AccountProfileActivity_collapse_tool_bar)).
                    setTitle(mp.get(Profile.GET_ACCOUNT_PROFILE_USER_NAME_KEY));

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

            Bitmap bitmap = Utilities.GetLoginUserHeadBitmapFromSP();
            if (bitmap != null) {
                ImageView view = findViewById(R.id.AccountProfileActivity_head_image_view);
                view.setImageBitmap(bitmap);
                view = findViewById(R.id.AccountProfileActivity_expanded_image_view);
                view.setImageBitmap(bitmap);
            }
            else{
                getAndSetUserHead(mp.get(Profile.GET_ACCOUNT_PROFILE_USER_HEAD_KEY));
            }
        }
        else{
            Profile.getOurInstance().getAccountProfile("" + userID, this);
        }
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
//                    case R.id.AccountProfileActivity_sex_view:
//                        new AlertDialog.Builder(AccountProfileActivity.this)
//                                .setCancelable(true)
//                                .setItems(R.array.sexChoice, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        AccountProfileItemLayout itemLayout1 = findViewById(res);
//                                        if (which == 0){
//                                            itemLayout1.setImageDrawable(getDrawable(R.drawable.male_icon));
//                                            updateProfile();
//                                            dialog.dismiss();
//                                        }
//                                        else if (which == 1){
//                                            itemLayout1.setImageDrawable(getDrawable(R.drawable.female_icon));
//                                            updateProfile();
//                                            dialog.dismiss();
//                                        }
//                                    }
//                                })
//                                .setTitle(R.string.sex)
//                                .show();
                    case R.id.AccountProfileActivity_update_password_view:
                        UpdatePasswordActivity.StartMe(AccountProfileActivity.this);
                        break;
                }
            }
        });
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
                Utilities.StartPickImageDialog(this);
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
        if (resultCode != RESULT_OK)
            return;
        Bundle extra = data.getExtras();
        String newInfo = null;
        if (extra != null) {
            newInfo = extra.getString(ProfileUpdateActivity.RETURN_INFO);
        }
        AccountProfileItemLayout itemLayout;

        switch (requestCode){
            case Utilities.PICK_IMAGE_FROM_CAMERA:
            case Utilities.PICK_IMAGE_FROM_GALLERY:
                Bitmap bitmap = Utilities.GetImageFromDialog(requestCode, resultCode, data);
                if (bitmap != null){
                    ((ImageView) findViewById(R.id.AccountProfileActivity_head_image_view)).setImageBitmap(bitmap);
                    ((ImageView) findViewById(R.id.AccountProfileActivity_expanded_image_view)).setImageBitmap(bitmap);
                    Publish.getOurInstance().uploadImage(bitmap,
                            Utilities.GetStringLoginUserId() + USER_HEAD_IMG_NAME_SUFFIX, this);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    newInfo = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    requestCode = Utilities.SAVE_USER_PROFILE_TARGET_USER_HEAD_IMAGE;
                }
                break;
            case REQUEST_USER_NAME:
                itemLayout = findViewById(R.id.AccountProfileActivity_user_name_view);
                ((CollapsingToolbarLayout) findViewById(R.id.AccountProfileActivity_collapse_tool_bar)).setTitle(newInfo);
                itemLayout.setContent(newInfo);
                break;
            case REQUEST_USER_PHONE:
                itemLayout = findViewById(R.id.AccountProfileActivity_phone_view);
                itemLayout.setContent(newInfo);
                break;
            case REQUEST_USER_GRADE:
                itemLayout = findViewById(R.id.AccountProfileActivity_grade_view);
                itemLayout.setContent(newInfo);
                break;
            case REQUEST_USER_DEPARTMENT:
                itemLayout = findViewById(R.id.AccountProfileActivity_department_view);
                itemLayout.setContent(newInfo);
                break;
        }
        Utilities.SaveLoginUserProfile(newInfo, requestCode);
        Toast.makeText(MyApplication.getContext(), UPDATE_PROFILE_SUCCESS, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取用户账户信息，更新用户账户信息时网络请求的回调函数
     * @param statusCode 返回状态
     * @param mp 存储需要传递数据的哈希表，若无数据则 mp 为 null
     * @param requestCode 请求码，标识同一个activity的不同网络请求
     *                    {@link Profile#GET_ACCOUNT_PROFILE}: 从服务器拉取用户账户信息
     *                    {@link Profile#UPDATE}: 更新用户信息
     *                    {@link Profile#UPDATE_PASSWORD}
     */
    @Override
    public void onSuccess(int statusCode, final HashMap<String, String> mp, int requestCode) {
        switch (requestCode){
            case GET_ACCOUNT_PROFILE:
                if (statusCode != 200){
                    onError(statusCode);
                    break;
                }
                Bitmap bitmap = Utilities.GetLoginUserHeadBitmapFromSP();
                if (bitmap != null){
                    ((ImageView) findViewById(R.id.AccountProfileActivity_head_image_view)).setImageBitmap(bitmap);
                    ((ImageView) findViewById(R.id.AccountProfileActivity_expanded_image_view)).setImageBitmap(bitmap);
                }
                else {
                    getAndSetUserHead(mp.get(Profile.GET_ACCOUNT_PROFILE_USER_HEAD_KEY));
                }

                Utilities.SaveLoginUserProfile(mp);

                AccountProfileItemLayout itemLayout = findViewById(R.id.AccountProfileActivity_sex_view);
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
            case Profile.UPDATE:
                break;
            case Profile.UPDATE_PASSWORD:
                break;
        }
    }

    /**
     * 获取用户头像并设置
     * @param userHeadUrl 用户头像 URL
     */
    public void getAndSetUserHead(final String userHeadUrl){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = MESSAGE_GET_USER_HEAD;
                try {
                    URL url = new URL(userHeadUrl);
                    InputStream in = url.openConnection().getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    in.close();
                    message.obj = bitmap;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    message.obj = null;
                    handler.sendMessage(message);
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @Override
    public void onError(int statusCode) {
        Toast.makeText(MyApplication.getContext(), Utilities.TOAST_NET_WORK_ERROR, Toast.LENGTH_SHORT).show();
    }


    public static void startMe(Context context){
        context.startActivity(new Intent(context, AccountProfileActivity.class));
    }
}
