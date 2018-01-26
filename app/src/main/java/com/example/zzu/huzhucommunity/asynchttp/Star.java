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


/**
 * Created by do_pc on 2018/1/25.
 *
 */

public class Star {

    private static final Star ourInstance = new Star();
    private AsyncHttpCallback callback;
    private static final int GET_STAR = 11101;
    private static final int CANCEL_STAR = 11102;

    /**
     * 外部调用类方法，获得单体实例
     *
     * @return 单体实例
     */
    public static Star getOurInstance() {
        return ourInstance;
    }

    /**
     * 私有构造方法
     */
    private Star() {

    }

    public AsyncHttpCallback getCallback() {
        return this.callback;
    }

    public void getStar(final String userID, final AsyncHttpCallback cBack) {
        try {
            if (userID != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("userID", userID);
                String path = "http://139.199.38.177/huzhu/php/getStar.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if (i == 200) {
                            //获取结果
                            try {
                                String result = new String(bytes, "utf-8");
                                Message message = new Message();
                                message.what = GET_STAR;
                                message.obj = result;
                                handler.sendMessage(message);
                                cBack.onSuccess(i);
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

    public void cancelStar(final String userID,final String resourceID, final AsyncHttpCallback cBack) {
        try {
            if (userID !=null && resourceID !=null && cBack != null){
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("userID", userID);
                params.put("resourceID", resourceID);
                String path = "http://139.199.38.177/huzhu/php/cancelStar.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if (i == 200) {
                            //获取结果
                            try {
                                String result = new String(bytes, "utf-8");
                                Message message = new Message();
                                message.what = CANCEL_STAR;
                                message.obj = result;
                                handler.sendMessage(message);
                                cBack.onSuccess(i);
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
            } else{
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
                case GET_STAR:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code = userObject.getInt("status");
                        callback.onSuccess(code);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CANCEL_STAR:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code = userObject.getInt("status");
                        callback.onSuccess(code);
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
