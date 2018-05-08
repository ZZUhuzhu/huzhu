package com.example.zzu.huzhucommunity.asynchttp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import android.os.Handler;
import android.os.Message;

import com.example.zzu.huzhucommunity.commonclass.MyApplication;
import com.example.zzu.huzhucommunity.commonclass.Utilities;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


/**
 * Created by do_pc on 2018/1/24.
 *
 */

public class LoginRegister {
    private static final String TAG = "LoginRegister";
    private static final LoginRegister ourInstance = new LoginRegister();
    private AsyncHttpCallback callback;
    private static final int LOGIN = 10101;
    private static final int REGISTER = 10102;
    /**
     * 登录时 JsonObject 返回键值信息
     */
    private static final String USER_ID_KEY = "User_id";
    private static final String USER_NAME_KEY = "User_name";
    private static final String USER_HEAD_KEY = "User_head";
    /**
     * 外部调用类方法，获得单体实例
     *
     * @return 单体实例
     */
    public static LoginRegister getOurInstance() {
        return ourInstance;
    }

    /**
     * 私有构造方法
     */
    private LoginRegister() {

    }

    public AsyncHttpCallback getCallback() {
        return this.callback;
    }



    /**
     * 获取用户输入的账号和密码后登录
     *
     * @param account  用户输入的账号
     * @param password 用户输入的密码
     */
    public void login(final String account, final String password, final AsyncHttpCallback cBack) {
        Log.e(TAG, "login: now login");
        try {
            if (account != null && password != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("account", account);
                params.put("password", password);
                String path = "http://139.199.38.177/huzhu/php/login.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        Log.d(TAG, ("onSuccess: 访问文件成功"));
                        //判断状态码
                        if(i == 200){
                            //获取结果
                            try {
                                String result = new String(bytes,"utf-8");
                                Message message = new Message();//在子线程中将Message对象发出去
                                message.what = LOGIN;
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
                        Log.d(TAG, "onFailure: 访问文件失败");
                        cBack.onError(i);
                    }
                });
            } else {
                throw new Exception("context == null OR username&password == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void register(final String account, final String password,
                         final String phoneNumber, final String gender,
                         final String grade, final String department,
                         final AsyncHttpCallback cBack) {
        try {
            if (account != null && password != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("account", account);
                params.put("password", password);
                params.put("phoneNumber", phoneNumber);
                params.put("gender", gender);
                params.put("grade",grade);
                params.put("department",department);
                String path = "http://139.199.38.177/huzhu/php/register.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        //判断状态码
                        if(i == 200){
                            //获取结果
                            try {
                                String result = new String(bytes,"utf-8");
                                Message message = new Message();//在子线程中将Message对象发出去
                                message.what = REGISTER;
                                message.obj = result;
                                handler.sendMessage(message);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }else {
                            cBack.onError(i);
                        }
                    }
                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        cBack.onError(i);
                    }
                });
            } else {
                throw new Exception("context == null OR username&password == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用来同步登录和注册的handler
     * 登录成功后用户名存放在名为 Profile 的 SharedPreference 中
     */
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            String responseStr = message.obj.toString();
            //String responseStr = "{\"status\":200,\"User_id\":0,\"User_name\":null,\"User_head\":null}";
            switch (message.what) {
                case LOGIN:
                    try {
                        JSONObject userObject = new JSONObject(responseStr);
                        int code=userObject.getInt("status");
                        if(code == 200) {
                            Utilities.SaveLoginUserProfile(userObject.getInt(USER_ID_KEY), userObject.getString(USER_NAME_KEY),
                                    userObject.getString(USER_HEAD_KEY));
                            callback.onSuccess(code, null);
                        }
                        else {
                            callback.onError(code);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case REGISTER:
                    break;
                default:
                    break;
            }
            return true;
        }
    });
}


