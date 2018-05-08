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

public class Track {

    private static final Track ourInstance = new Track();
    private AsyncHttpCallback callback;
    private static final int GET_USER_TRACK = 11001;
    private static final int DELETE_TRACK = 11002;
    private static final int DELETE_ALL_TRACK = 11003;

    /**
     * 外部调用类方法，获得单体实例
     *
     * @return 单体实例
     */
    public static Track getOurInstance() {
        return ourInstance;
    }

    /**
     * 私有构造方法
     */
    private Track() {
    }

    public AsyncHttpCallback getCallback() {
        return this.callback;
    }


    public void getUserTrack(final String userID, final String times, final AsyncHttpCallback cBack) {
        try {
            if (userID != null && times != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("userID", userID);
                params.put("times", times);
                String path = "http://139.199.38.177/huzhu/php/getUserTrack.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if(i == 200){
                            //获取结果
                            try {
                                String result = new String(bytes,"utf-8");
                                Message message = new Message();
                                message.what = GET_USER_TRACK;
                                message.obj = result;
                                handler.sendMessage(message);
                                cBack.onSuccess(i, null);
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

    public void deleteTrack(final String userID, final String resourceID, final AsyncHttpCallback cBack) {
        try {
            if (userID != null && resourceID!= null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("userID", userID);
                params.put("resourceID", resourceID);
                String path = "http://139.199.38.177/huzhu/php/deleteTrack.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if(i == 200){
                            //获取结果
                            try {
                                String result = new String(bytes,"utf-8");
                                Message message = new Message();
                                message.what = DELETE_TRACK;
                                message.obj = result;
                                handler.sendMessage(message);
                                cBack.onSuccess(i, null);
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

    public void deleteAllTrack(final String userID, final AsyncHttpCallback cBack) {
        try {
            if (userID != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("userID", userID);
                String path = "http://139.199.38.177/huzhu/php/deleteAllTrack.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if(i == 200){
                            //获取结果
                            try {
                                String result = new String(bytes,"utf-8");
                                Message message = new Message();
                                message.what = DELETE_ALL_TRACK;
                                message.obj = result;
                                handler.sendMessage(message);
                                cBack.onSuccess(i, null);
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
                case GET_USER_TRACK:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code=userObject.getInt("status");
                        HashMap<String, String> mp = new HashMap<>();
                        mp.put("code", "" + code);
                        callback.onSuccess(code, mp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case DELETE_TRACK:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code=userObject.getInt("status");
                        HashMap<String, String> mp = new HashMap<>();
                        mp.put("code", "" + code);
                        callback.onSuccess(code, mp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case DELETE_ALL_TRACK:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code=userObject.getInt("status");
                        callback.onSuccess(code, null);
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
