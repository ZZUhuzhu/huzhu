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
import android.util.Base64;
import android.util.DisplayMetrics;

import com.example.zzu.huzhucommunity.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.zzu.huzhucommunity.asynchttp.Profile.GET_ACCOUNT_PROFILE_LOGIN_TIME_KEY;
import static com.example.zzu.huzhucommunity.asynchttp.Profile.GET_ACCOUNT_PROFILE_STATUS_CODE_KEY;
import static com.example.zzu.huzhucommunity.asynchttp.Profile.GET_ACCOUNT_PROFILE_USER_ACCOUNT_KEY;
import static com.example.zzu.huzhucommunity.asynchttp.Profile.GET_ACCOUNT_PROFILE_USER_DEPT_KEY;
import static com.example.zzu.huzhucommunity.asynchttp.Profile.GET_ACCOUNT_PROFILE_USER_GRADE_KEY;
import static com.example.zzu.huzhucommunity.asynchttp.Profile.GET_ACCOUNT_PROFILE_USER_HEAD_KEY;
import static com.example.zzu.huzhucommunity.asynchttp.Profile.GET_ACCOUNT_PROFILE_USER_NAME_KEY;
import static com.example.zzu.huzhucommunity.asynchttp.Profile.GET_ACCOUNT_PROFILE_USER_PHONE_KEY;
import static com.example.zzu.huzhucommunity.asynchttp.Profile.GET_ACCOUNT_PROFILE_USER_REG_TIME_KEY;
import static com.example.zzu.huzhucommunity.asynchttp.Profile.GET_ACCOUNT_PROFILE_USER_SEX_KEY;

/**
 * Created by FEI on 2018/5/8.
 * 工具类，包含与活动无关的功能及常量：
 *      存放已登录用户个人信息
 *      获取已登录用户ID
 */

public class Utilities {
    /**
     * Toast 中的字符串常量
     */
    public static final String TOAST_NET_WORK_ERROR = "网络异常";
    /**
     * 性别
     */
    public static final String MALE = "male";
    public static final String FEMALE = "female";
    /**
     * 用户ID --> sharedPreference 文件名
     * 文件名后缀
     * 已登录用户键
     */
    private static final String USER_ID_TO_PROFILE_FILE_NAME = "userIDToFileName";
    private static final String USER_PROFILE_FILE_NAME_SUFFIX = "_Profile";
    private static final String LOGIN_USER_ID_KEY = "loginUserID";
    /**
     * 存放到sharedPreference时的键
     */
    private static final String USER_ID_KEY_SHARED_PREFERENCE = "userAccount";
    private static final String USER_NAME_KEY_SHARED_PREFERENCE = "userName";
    private static final String USER_HEAD_KEY_SHARED_PREFERENCE = "userHead";
    private static final String USER_HEAD_IMAGE_SHARED_PREFERENCE = "userHeadImage";
    private static final String ACCOUNT_PROFILE_INSIDE_SHARED_PREFERENCE = "hasAccountProfile";
    private static final String DEF_STRING_VALUE_SHARED_PREFERENCE = "";
    /**
     * 未找到用户
     */
    public static final String USER_NOT_FOUND = "-1";

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
     * 根据用户ID确定用户信息所在文件
     * @param userID 用户ID
     * @return 存放用户个人信息的文件名
     */
    private static String GetUserProfileFileName(String userID){
        SharedPreferences sp = MyApplication.getContext().
                getSharedPreferences(USER_ID_TO_PROFILE_FILE_NAME, Context.MODE_PRIVATE);
        String fileName = sp.getString(userID, null);
        if (fileName != null){
            return fileName;
        }
        fileName = userID + USER_PROFILE_FILE_NAME_SUFFIX;
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(userID, fileName);
        editor.apply();
        return fileName;
    }

    /**
     * 存储用户的基本个人信息到文件 {@link #USER_PROFILE_FILE_NAME_SUFFIX} 中
     * @param userID 用户ID
     * @param userName 用户名
     * @param userHead 用户头像URL
     */
    public static void SaveLoginUserProfile(String userID, String userName, String userHead){
        SharedPreferences.Editor editor;
        editor = MyApplication.getContext().getSharedPreferences(
                USER_ID_TO_PROFILE_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(LOGIN_USER_ID_KEY, userID);
        editor.apply();
        editor = MyApplication.getContext().getSharedPreferences(
                GetUserProfileFileName(userID), Context.MODE_PRIVATE).edit();
        editor.putString(USER_ID_KEY_SHARED_PREFERENCE, userID);
        editor.putString(USER_NAME_KEY_SHARED_PREFERENCE, userName);
        editor.putString(USER_HEAD_KEY_SHARED_PREFERENCE, userHead);
        editor.apply();
    }

    /**
     * 将 map 中的数据保存到 sharedPreference 中
     * 同时写入 {@link #ACCOUNT_PROFILE_INSIDE_SHARED_PREFERENCE} 值为 true
     * @param mp 待保存的数据集
     */
    public static void SaveLoginUserProfile(HashMap<String, String> mp){
        SharedPreferences.Editor editor = MyApplication.getContext().
                getSharedPreferences(GetUserProfileFileName(GetStingLoginUserId()), Context.MODE_PRIVATE).edit();
        for (Object o : mp.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            editor.putString(key, value);
        }
        editor.putBoolean(ACCOUNT_PROFILE_INSIDE_SHARED_PREFERENCE, true);
        editor.apply();
    }

    /**
     * 获取 sharedPreference 中的用户账户信息
     * @return 返回存放信息的 map
     */
    public static HashMap<String, String> GetLoginUserAccountProfile(){
        SharedPreferences sp = MyApplication.getContext().
                getSharedPreferences(GetUserProfileFileName(GetStingLoginUserId()), Context.MODE_PRIVATE);
        HashMap<String, String> mp = new HashMap<>();
        mp.put(GET_ACCOUNT_PROFILE_STATUS_CODE_KEY, sp.getString(GET_ACCOUNT_PROFILE_STATUS_CODE_KEY, DEF_STRING_VALUE_SHARED_PREFERENCE));
        mp.put(GET_ACCOUNT_PROFILE_USER_HEAD_KEY, sp.getString(GET_ACCOUNT_PROFILE_USER_HEAD_KEY, DEF_STRING_VALUE_SHARED_PREFERENCE));
        mp.put(GET_ACCOUNT_PROFILE_USER_NAME_KEY, sp.getString(GET_ACCOUNT_PROFILE_USER_NAME_KEY, DEF_STRING_VALUE_SHARED_PREFERENCE));
        mp.put(GET_ACCOUNT_PROFILE_USER_ACCOUNT_KEY, sp.getString(GET_ACCOUNT_PROFILE_USER_ACCOUNT_KEY, DEF_STRING_VALUE_SHARED_PREFERENCE));
        mp.put(GET_ACCOUNT_PROFILE_USER_SEX_KEY, sp.getString(GET_ACCOUNT_PROFILE_USER_SEX_KEY, DEF_STRING_VALUE_SHARED_PREFERENCE));
        mp.put(GET_ACCOUNT_PROFILE_USER_PHONE_KEY, sp.getString(GET_ACCOUNT_PROFILE_USER_PHONE_KEY, DEF_STRING_VALUE_SHARED_PREFERENCE));
        mp.put(GET_ACCOUNT_PROFILE_USER_GRADE_KEY, sp.getString(GET_ACCOUNT_PROFILE_USER_GRADE_KEY, DEF_STRING_VALUE_SHARED_PREFERENCE));
        mp.put(GET_ACCOUNT_PROFILE_USER_DEPT_KEY, sp.getString(GET_ACCOUNT_PROFILE_USER_DEPT_KEY, DEF_STRING_VALUE_SHARED_PREFERENCE));
        mp.put(GET_ACCOUNT_PROFILE_USER_REG_TIME_KEY, sp.getString(GET_ACCOUNT_PROFILE_USER_REG_TIME_KEY, DEF_STRING_VALUE_SHARED_PREFERENCE));
        mp.put(GET_ACCOUNT_PROFILE_LOGIN_TIME_KEY, sp.getString(GET_ACCOUNT_PROFILE_LOGIN_TIME_KEY, DEF_STRING_VALUE_SHARED_PREFERENCE));
        return mp;
    }

    /**
     * 将用户的头像 bitmap 压缩之后保存到 sharedPreference 中
     * @param bitmap 待保存的 bitmap
     */
    public static void SaveLoginUserHeadBitmap(Bitmap bitmap){
        SharedPreferences.Editor editor = MyApplication.getContext().
                getSharedPreferences(GetUserProfileFileName(GetStingLoginUserId()), Context.MODE_PRIVATE).edit();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
        String imageBase64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        editor.putString(USER_HEAD_IMAGE_SHARED_PREFERENCE, imageBase64);
        editor.apply();
    }

    /**
     * 判断是否已经将用户的账户信息存放到 sharedPreference 里面
     * @return true：如果已经存储
     *          false：未存储
     */
    public static boolean IsLoginUserAccountProfileStored(){
        SharedPreferences sharedPreferences = MyApplication.getContext().
                getSharedPreferences(GetUserProfileFileName(GetStingLoginUserId()), Context.MODE_PRIVATE);
        return sharedPreferences.contains(ACCOUNT_PROFILE_INSIDE_SHARED_PREFERENCE) &&
                sharedPreferences.getBoolean(ACCOUNT_PROFILE_INSIDE_SHARED_PREFERENCE, false);
    }

    /**
     * 获取 sharedPreference 中存储的用户头像 bitmap
     * @return 如果有，返回用户头像
     *          否则返回 null
     */
    public static Bitmap GetUserHeadBitmap(){
        SharedPreferences sharedPreferences = MyApplication.getContext().
                getSharedPreferences(GetUserProfileFileName(GetStingLoginUserId()), Context.MODE_PRIVATE);
        if (!sharedPreferences.contains(USER_HEAD_IMAGE_SHARED_PREFERENCE))
            return null;
        String temp = sharedPreferences.getString(USER_HEAD_IMAGE_SHARED_PREFERENCE, "");
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(temp.getBytes(), Base64.DEFAULT));
        return BitmapFactory.decodeStream(bais);
    }

    /**
     * 获取已经登录的用户ID
     * @return 若已登录（sharedPreference文件中已存储信息）则返回用户ID
     *          否则返回 {@link #USER_NOT_FOUND}
     */
    public static int GetLoginUserId(){
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences
                (USER_ID_TO_PROFILE_FILE_NAME, Context.MODE_PRIVATE);

        return Integer.parseInt(sharedPreferences.getString(LOGIN_USER_ID_KEY, USER_NOT_FOUND));
    }
    private static String GetStingLoginUserId(){
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences
                (USER_ID_TO_PROFILE_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LOGIN_USER_ID_KEY, USER_NOT_FOUND);
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
