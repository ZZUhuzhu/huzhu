package com.example.zzu.huzhucommunity.asynchttp;

import android.os.Handler;

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

public class Message {

    private static final Message ourInstance = new Message();
    private AsyncHttpCallback callback;
    private static final int GET_RELATED_USER_ID = 10701;
    private static final int GET_CHAT_RECORDS = 10702;

    public static final String NUMBER_JSON_KEY = "number";
    public static final String STATUS_JSON_KEY = "code";

    /**
     * 外部调用类方法，获得单体实例
     *
     * @return 单体实例
     */
    public static Message getOurInstance() {
        return ourInstance;
    }

    /**
     * 私有构造方法
     */
    private Message() {
    }

    public AsyncHttpCallback getCallback() {
        return this.callback;
    }



    public void getRelatedUserID(final String userID,final AsyncHttpCallback cBack) {
        try {
            if (userID != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("userID", userID);
                String path = "http://139.199.38.177/huzhu/php/getRelatedUserID.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if(i == 200){
                            //获取结果
                            try {
                                String result = new String(bytes,"utf-8");
                                android.os.Message message = new android.os.Message();
                                message.what = GET_RELATED_USER_ID;
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


    public void getChatRecords(final String myID, final String oppositeID, final String times,final AsyncHttpCallback cBack) {
        try {
            if (myID != null && oppositeID != null && times != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("myID", myID);
                params.put("oppositeID", oppositeID);
                params.put("times", times);
                String path = "http://139.199.38.177/huzhu/php/getChatRecords.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if(i == 200){
                            //获取结果
                            try {
                                String result = new String(bytes,"utf-8");
                                android.os.Message message = new android.os.Message();
                                message.what = GET_CHAT_RECORDS;
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
        public boolean handleMessage(android.os.Message message) {
            String Response = message.toString();
            switch (message.what) {
                case GET_RELATED_USER_ID:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code=userObject.getInt("status");
                        String number = userObject.getString(NUMBER_JSON_KEY);
                        //n可能很大
                        int n = Integer.parseInt(number);
                        HashMap<String, String> mp = new HashMap<>();
                        mp.put(STATUS_JSON_KEY, code + "");
                        mp.put(NUMBER_JSON_KEY, number);
                        for(int i = 0; i < n;++i) {
                            mp.put("" + i, userObject.getString("" + i));
                        }

                        callback.onSuccess(code, mp, GET_RELATED_USER_ID);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case GET_CHAT_RECORDS:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code=userObject.getInt("status");
                        String number = userObject.getString(NUMBER_JSON_KEY);
                        //n可能很大
                        int n = Integer.parseInt(number);
                        HashMap<String, String> mp = new HashMap<>();
                        mp.put(STATUS_JSON_KEY, code + "");
                        mp.put(NUMBER_JSON_KEY, number);
                        for(int i = 0; i < n;++i) {
                            mp.put("" + i, userObject.getString("" + i));
                        }

                        mp.put("code", code + "");

                        callback.onSuccess(code, mp, GET_CHAT_RECORDS);
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
