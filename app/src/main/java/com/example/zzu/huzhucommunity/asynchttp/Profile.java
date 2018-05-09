package com.example.zzu.huzhucommunity.asynchttp;

import android.os.Handler;
import android.os.Message;

import com.example.zzu.huzhucommunity.activities.AccountProfileActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by do_pc on 2018/1/25.
 * 
 */

public class Profile {

    private static final Profile ourInstance = new Profile();
    private AsyncHttpCallback callback;
    private static final int UPDATE = 11401;
    private static final int GET_ACCOUNT_PROFILE = 11406;
    private static final int UPDATE_PASSWORD = 11407;

    /**
     * 获取账户信息时 JSON 数据里面的键以及回调时 map 里面的键
     */
    private static final String GET_ACCOUNT_PROFILE_STATUS_CODE_KEY = "status";
    public static final String GET_ACCOUNT_PROFILE_USER_HEAD_KEY = "userHead";
    public static final String GET_ACCOUNT_PROFILE_USER_NAME_KEY = "userName";
    private static final String GET_ACCOUNT_PROFILE_USER_ACCOUNT_KEY = "userAccount";
    public static final String GET_ACCOUNT_PROFILE_USER_SEX_KEY = "userSex";
    public static final String GET_ACCOUNT_PROFILE_USER_PHONE_KEY = "userPhone";
    public static final String GET_ACCOUNT_PROFILE_USER_GRADE_KEY = "userGrade";
    public static final String GET_ACCOUNT_PROFILE_USER_DEPT_KEY = "userDept";
    public static final String GET_ACCOUNT_PROFILE_USER_REG_TIME_KEY = "userRegisterTime";
    public static final String GET_ACCOUNT_PROFILE_LOGIN_TIME_KEY = "userLoginTime";

    /**
     * 外部调用类方法，获得单体实例
     *
     * @return 单体实例
     */
    public static Profile getOurInstance() {
        return ourInstance;
    }

    /**
     * 私有构造方法
     */
    private Profile() {

    }

    public AsyncHttpCallback getCallback() {
        return this.callback;
    }

    public void update(final String target, final String userID,final String newItem, final AsyncHttpCallback cBack) {
        try {
            if (target != null && userID != null && newItem != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("target", target);
                params.put("userID", userID);
                params.put("newItem", newItem);
                String path = "http://139.199.38.177/huzhu/php/update.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if(i == 200){
                            //获取结果
                            try {
                                String result = new String(bytes,"utf-8");
                                Message message = new Message();
                                message.what =UPDATE;
                                message.obj = result;
                                handler.sendMessage(message);
                                cBack.onSuccess(i, null, 0);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }else{
                            cBack.onError(i);
                        }
                    }
                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        cBack.onError(i);
                    }
                });
            } else {
                throw new Exception("参数传递错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAccountProfile(final String userID, final AsyncHttpCallback cBack) {
        try {
            if (userID != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("userID", userID);
                String path = "http://139.199.38.177/huzhu/php/getAccountProfile.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if(i == 200){
                            //获取结果
                            try {
                                String result = new String(bytes,"utf-8");
                                Message message = new Message();
                                message.what = GET_ACCOUNT_PROFILE;
                                message.obj = result;
                                handler.sendMessage(message);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }else{
                            cBack.onError(i);
                        }
                    }
                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        cBack.onError(i);
                    }
                });
            } else {
                throw new Exception("参数传递错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新密码
     * @param userID 用户ID
     * @param newPassword 新密码
     * @param cBack 回调对象
     */
    public void updatePassword(final String userID, final String newPassword, final AsyncHttpCallback cBack) {
        try {
            if (userID != null && newPassword != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("userID", userID);
                params.put("newPassword", newPassword);
                String path = "http://139.199.38.177/huzhu/php/updatePassword.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if(i == 200){
                            //获取结果
                            try {
                                String result = new String(bytes,"utf-8");
                                Message message = new Message();
                                message.what = UPDATE_PASSWORD;
                                message.obj = result;
                                handler.sendMessage(message);
                                cBack.onSuccess(i, null, 0);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }else{
                            cBack.onError(i);
                        }
                    }
                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        cBack.onError(i);
                    }
                });
            } else {
                throw new Exception("参数传递错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            String Response = message.toString();
            switch (message.what) {
                case UPDATE:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code=userObject.getInt("status");

                        HashMap<String, String> mp = new HashMap<>();
                        mp.put("code", code + "");

                        callback.onSuccess(code, null, 0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case GET_ACCOUNT_PROFILE:
                    try {
                        JSONObject userObject = new JSONObject(message.obj.toString());
                        int code=userObject.getInt(GET_ACCOUNT_PROFILE_STATUS_CODE_KEY);
                        String userHead = userObject.getString(GET_ACCOUNT_PROFILE_USER_HEAD_KEY);
                        String userName = userObject.getString(GET_ACCOUNT_PROFILE_USER_NAME_KEY);
                        String userAccount = userObject.getString(GET_ACCOUNT_PROFILE_USER_ACCOUNT_KEY);
                        String userSex = userObject.getString(GET_ACCOUNT_PROFILE_USER_SEX_KEY);
                        String userPhone = userObject.getString(GET_ACCOUNT_PROFILE_USER_PHONE_KEY);
                        String userGrade = userObject.getString(GET_ACCOUNT_PROFILE_USER_GRADE_KEY);
                        String userDept = userObject.getString(GET_ACCOUNT_PROFILE_USER_DEPT_KEY);
                        String userRegisterTime = userObject.getString(GET_ACCOUNT_PROFILE_USER_REG_TIME_KEY);
                        String userLoginTime = userObject.getString(GET_ACCOUNT_PROFILE_LOGIN_TIME_KEY);

                        HashMap<String, String> mp = new HashMap<>();
                        mp.put(GET_ACCOUNT_PROFILE_STATUS_CODE_KEY, code + "");
                        mp.put(GET_ACCOUNT_PROFILE_USER_HEAD_KEY, userHead);
                        mp.put(GET_ACCOUNT_PROFILE_USER_NAME_KEY, userName);
                        mp.put(GET_ACCOUNT_PROFILE_USER_ACCOUNT_KEY, userAccount);
                        mp.put(GET_ACCOUNT_PROFILE_USER_SEX_KEY, userSex);
                        mp.put(GET_ACCOUNT_PROFILE_USER_PHONE_KEY, userPhone);
                        mp.put(GET_ACCOUNT_PROFILE_USER_GRADE_KEY, userGrade);
                        mp.put(GET_ACCOUNT_PROFILE_USER_DEPT_KEY, userDept);
                        mp.put(GET_ACCOUNT_PROFILE_USER_REG_TIME_KEY, userRegisterTime);
                        mp.put(GET_ACCOUNT_PROFILE_LOGIN_TIME_KEY, userLoginTime);

                        callback.onSuccess(code, mp, AccountProfileActivity.REQUEST_CODE_GET_ACCOUNT_PROFILE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case UPDATE_PASSWORD:
                    break;
                default:
                    break;
            }
            return true;
        }
    });
}
