package com.example.zzu.huzhucommunity.commonclass;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;

import com.example.zzu.huzhucommunity.R;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by FEI on 2018/5/8.
 * 工具类，包含与活动无关的功能及常量：
 *      存放已登录用户个人信息
 *      获取已登录用户ID
 */

public class Utilities {
    /**
     * 性别
     */
    public static final String MALE = "male";
    public static final String FEMALE = "female";
    /**
     * 存放用户个人信息的sharedPreference的名字
     */
    private static final String USER_PROFILE_FILE_NAME = "Profile";
    /**
     * 存放到sharedPreference时的键
     */
    private static final String USER_ID_KEY_SHARED_PREFERENCE = "User_ID";
    private static final String USER_NAME_KEY_SHARED_PREFERENCE = "User_name";
    private static final String USER_HEAD_KEY_SHARED_PREFERENCE = "User_head";
    /**
     * 未找到用户
     */
    public static final int USER_NOT_FOUND = -1;

    /**
     * 抓取图片对话框相关常量
     */
    public static final int PICK_IMAGE_FROM_CAMERA = 20002;
    public static final int PICK_IMAGE_FROM_GALLERY = 20003;
    /**
     * 右滑返回相关常量
     */
    public static final int SWIPE_MIN_DISTANCE = 120;
    public static final int SWIPE_MAX_Y_DISTANCE = 120;
    public static final int SWIPE_THRESHOLD_VELOCITY = 200;


    /**
     * 存储用户的基本个人信息到文件 {@link #USER_PROFILE_FILE_NAME} 中
     * @param userID 用户ID
     * @param userName 用户名
     * @param userHead 用户头像URL
     */
    public static void SaveLoginUserProfile(int userID, String userName, String userHead){
        SharedPreferences.Editor editor = MyApplication.getContext().getSharedPreferences(
                USER_PROFILE_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(USER_ID_KEY_SHARED_PREFERENCE, userID);
        editor.putString(USER_NAME_KEY_SHARED_PREFERENCE, userName);
        editor.putString(USER_HEAD_KEY_SHARED_PREFERENCE, userHead);
        editor.apply();
    }

    /**
     * 获取已经登录的用户ID
     * @return 若已登录（sharedPreference文件中已存储信息）则返回用户ID
     *          否则返回 {@link #USER_NOT_FOUND}
     */
    public static int GetLoginUserId(){
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences
                (USER_PROFILE_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(USER_ID_KEY_SHARED_PREFERENCE, USER_NOT_FOUND);
    }
    /**
     * 选择照片对话框
     * @param context 上下文
     */
    public static void startPickImageDialog(final Context context){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setItems(R.array.AddImageFrom, new DialogInterface.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    ((Activity)context).startActivityForResult(intent, Utilities.PICK_IMAGE_FROM_CAMERA);
                }
                else{
                    intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    ((Activity)context).startActivityForResult(intent, Utilities.PICK_IMAGE_FROM_GALLERY);
                }
            }
        });
        dialog.show();
    }

    /**
     * 从拍照对话框返回后的数据中取出照片的 Bitmap
     * @param requestCode 请求码
     * @param resultCode 返回码
     * @param data 数据
     * @return 照片或者null
     */
    public static Bitmap getImageFromDialog(int requestCode, int resultCode, Intent data){
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode) {
                case PICK_IMAGE_FROM_CAMERA:
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        return (Bitmap) bundle.get("data");
                    }
                    return null;
                case PICK_IMAGE_FROM_GALLERY:
                    Uri uri = data.getData();
                    if (uri == null)
                        return null;
                    ContentResolver contentResolver = MyApplication.getContext().getContentResolver();
                    try {
                        return BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return null;
                    }
            }
        }
        return null;
    }
    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp){
        Resources resources = MyApplication.getContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px){
        Resources resources = MyApplication.getContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * 将毫秒级的时间转换为格式为 year-month-day hour:minute:second 的时间字符串
     * @param timeInMills 待转换的毫秒数
     * @return 返回转换后的字符串
     */
    public static String convertTimeInMillToString(long timeInMills){
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(timeInMills);
        return calendar.get(Calendar.YEAR) + "-"
                + calendar.get(Calendar.MONTH) + "-"
                + calendar.get(Calendar.DAY_OF_MONTH) + " "
                + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                + calendar.get(Calendar.MINUTE) + ":"
                + calendar.get(Calendar.SECOND);
    }
}
