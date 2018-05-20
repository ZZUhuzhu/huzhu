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

public class Received {

    private static final Received ourInstance = new Received();
    private AsyncHttpCallback callback;
    private static final int GET_RECEIVED_RESOURCE = 11301;
    private static final int DELETE_RECEIVED_RESOURCE = 11302;

    public static final String STATUS_JSON_KEY = "code";
    public static final String NUMBER_JSON_KEY = "number";

    /**
     * 外部调用类方法，获得单体实例
     *
     * @return 单体实例
     */
    public static Received getOurInstance() {
        return ourInstance;
    }

    /**
     * 私有构造方法
     */
    private Received() {

    }

    public AsyncHttpCallback getCallback() {
        return this.callback;
    }

    public void getReceivedResource(final String userID, final AsyncHttpCallback cBack) {
        try {
            if (userID != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("userID", userID);
                String path = "http://139.199.38.177/huzhu/php/getReceivedResource.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if(i == 200){
                            //获取结果
                            try {
                                String result = new String(bytes,"utf-8");
                                Message message = new Message();
                                message.what = GET_RECEIVED_RESOURCE;
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

    public void deleteReceivedResource(final String resourceID, final String userID, final AsyncHttpCallback cBack) {
        try {
            if (resourceID != null && userID != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("resourceID", resourceID);
                params.put("userID", userID);
                //缺少该PHP文件
                String path = "http://139.199.38.177/huzhu/php/deleteReceivedResource.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if(i == 200){
                            //获取结果
                            try {
                                String result = new String(bytes,"utf-8");
                                Message message = new Message();
                                message.what = DELETE_RECEIVED_RESOURCE;
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
                case GET_RECEIVED_RESOURCE:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code=userObject.getInt("status");
                        String number = userObject.getString(NUMBER_JSON_KEY);
                        int n = Integer.parseInt(number);
                        HashMap<String, String> mp = new HashMap<>();
                        mp.put(STATUS_JSON_KEY, code + "");
                        mp.put(NUMBER_JSON_KEY, number);
                        for (int i = 0; i < n; ++i) {
                            mp.put("" + i, userObject.getString("" + i));
                        }

                        callback.onSuccess(code, mp, GET_RECEIVED_RESOURCE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case DELETE_RECEIVED_RESOURCE:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code=userObject.getInt("status");
                        HashMap<String, String> mp = new HashMap<>();
                        mp.put(STATUS_JSON_KEY, code + "");
                        callback.onSuccess(code, mp, DELETE_RECEIVED_RESOURCE);
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

