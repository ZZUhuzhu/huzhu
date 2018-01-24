package com.example.zzu.huzhucommunity.asynchttp;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.UnsupportedEncodingException;

/**
 * Created by do_pc on 2018/1/24.
 */

@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class loginRegister {
    private static final loginRegister ourInstance = new loginRegister();
    private asyncHttpCallback callback;
    private static final int LOGIN = 1001;
    private static final int REGISTER = 1002;

    /**
     * 外部调用类方法，获得单体实例
     *
     * @return 单体实例
     */
    public static loginRegister getOurInstance() {
        return ourInstance;
    }

    /**
     * 私有构造方法
     */
    private loginRegister() {

    }

    public asyncHttpCallback getCallback() {
        return this.callback;
    }

    /**
     * 获取用户输入的账号和密码后登录
     *
     * @param account  用户输入的账号
     * @param password 用户输入的密码
     */
    public void login(final String account, final String password, final asyncHttpCallback cBack) {
        try {
            if (account != null && password != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("account", account);
                params.put("password", password);
                String path = "http://www.idooooo.tk/huzhu/php/login.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if(i == 200){
                            //获取结果
                            try {
                                String result = new String(bytes,"utf-8");
                                Message message = new Message();//在子线程中将Message对象发出去
                                message.what = LOGIN;
                                message.obj = result;
                                handler.sendMessage(message);
                                cBack.onSuccess(i);
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
                throw new Exception("context == null OR username&password == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void register(final String account, final String password,
                         final String phoneNumber, final String gender,
                         final String grade, final String department,
                         final asyncHttpCallback cBack) {
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
                String path = "http://www.idooooo.tk/huzhu/php/register.php";
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
                                cBack.onSuccess(i);
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

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case LOGIN:
                    break;
                case REGISTER:
                    break;
            }
            return true;
        }
    });
}


