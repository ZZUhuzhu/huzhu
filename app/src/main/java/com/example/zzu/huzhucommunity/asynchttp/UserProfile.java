package com.example.zzu.huzhucommunity.asynchttp;

import android.os.Handler;
import android.os.Message;

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

public class UserProfile {

    private static final UserProfile ourInstance = new UserProfile();
    private AsyncHttpCallback callback;
    private static final int GET_USER_ALL_KINDS_NUMBER = 10601;
    private static final int GET_USER_PROFILE = 10901;
    /**
     * 获取用户账户信息时的 JSON 键
     * 以及放入map里面时的键
     */
    private static final String USER_STATUS_JSON_KEY = "status";
    private static final String USER_HEAD_JSON_KEY = "userHead";
    private static final String USER_NAME_JSON_KEY = "userName";
    private static final String USER_SEX_JSON_key = "userSex";
    private static final String USER_GRADE_JSON_KEY = "userGrade";
    private static final String USER_DEPARTMENT_JSON_KEY = "userDepartment";
    private static final String USER_PHONE_JSON_KEY = "userPhone";


    /**
     * 外部调用类方法，获得单体实例
     *
     * @return 单体实例
     */
    public static UserProfile getOurInstance() {
        return ourInstance;
    }

    /**
     * 私有构造方法
     */
    private UserProfile() {

    }

    public AsyncHttpCallback getCallback() {
        return this.callback;
    }


    public void getUserAllKindsNumber(final String userID, final AsyncHttpCallback cBack) {
        try {
            if (userID != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("userID",userID);
                String path = "http://139.199.38.177/huzhu/php/getUserAllKindsNumber.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if (i == 200) {
                            //获取结果
                            try {
                                String result = new String(bytes, "utf-8");
                                Message message = new Message();
                                message.what = GET_USER_ALL_KINDS_NUMBER;
                                message.obj = result;
                                handler.sendMessage(message);
                                cBack.onSuccess(i, null, 0);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } else {
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
     * 获取用户信息，通过handler回调使用map返回数据
     * @param userID 用户ID
     * @param cBack 回调接口
     */
    public void getUserProfile(final String userID, final AsyncHttpCallback cBack) {
        try {
            if (userID != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("userID", userID);
                String path = "http://139.199.38.177/huzhu/php/getUserProfile.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if(i == 200){
                            //获取结果
                            try {
                                String result = new String(bytes,"utf-8");
                                Message message = new Message();
                                message.what = GET_USER_PROFILE;
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
                case GET_USER_ALL_KINDS_NUMBER:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code=userObject.getInt(USER_STATUS_JSON_KEY);
                        callback.onSuccess(code, null, 0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case GET_USER_PROFILE:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        // code 200 表示成功，map中的无用
                        int code = userObject.getInt(USER_STATUS_JSON_KEY);
                        String userHead = userObject.getString(USER_HEAD_JSON_KEY);
                        String userName = userObject.getString(USER_NAME_JSON_KEY);
                        String userSex = userObject.getString(USER_SEX_JSON_key);
                        String userGrade = userObject.getString(USER_GRADE_JSON_KEY);
                        String userDepartment = userObject.getString(USER_DEPARTMENT_JSON_KEY);
                        String userPhone = userObject.getString(USER_PHONE_JSON_KEY);
                        HashMap<String, String> mp = new HashMap<>();
                        mp.put(USER_HEAD_JSON_KEY, userHead);
                        mp.put(USER_SEX_JSON_key, userSex);
                        mp.put(USER_NAME_JSON_KEY, userName);
                        mp.put(USER_GRADE_JSON_KEY, userGrade);
                        mp.put(USER_DEPARTMENT_JSON_KEY, userDepartment);
                        mp.put(USER_PHONE_JSON_KEY, userPhone);
                        mp.put(USER_STATUS_JSON_KEY, "" + code);
                        callback.onSuccess(code, mp, 0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    });

}
