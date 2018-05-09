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

public class Setting {
    private static final Setting ourInstance = new Setting();
    private AsyncHttpCallback callback;
    private static final int RECORD_USER_FEEDBACK = 11601;
    private static final int CHECK_FOR_UPDATE = 11602;

    /**
     * 外部调用类方法，获得单体实例
     *
     * @return 单体实例
     */
    public static Setting getOurInstance() {
        return ourInstance;
    }

    /**
     * 私有构造方法
     */
    private Setting() {

    }

    public AsyncHttpCallback getCallback() {
        return this.callback;
    }

    public void recordUserFeedback(final String userID, final String feedbackDetail, final AsyncHttpCallback cBack) {
        try {
            if (userID != null && feedbackDetail != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("userID", userID);
                params.put("feedbackDetail", feedbackDetail);
                String path = "http://139.199.38.177/huzhu/php/recordUserFeedback.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if (i == 200) {
                            //获取结果
                            try {
                                String result = new String(bytes, "utf-8");
                                Message message = new Message();
                                message.what = RECORD_USER_FEEDBACK;
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


    public void checkForUpdate(final String versionCode, final AsyncHttpCallback cBack) {
        try {
            if (versionCode != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("versionCode", versionCode);
                String path = "http://139.199.38.177/huzhu/php/checkForUpdate.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if (i == 200) {
                            //获取结果
                            try {
                                String result = new String(bytes, "utf-8");
                                Message message = new Message();
                                message.what = CHECK_FOR_UPDATE;
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

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            String Response = message.toString();
            switch (message.what) {
                case RECORD_USER_FEEDBACK:
                try {
                    JSONObject userObject = new JSONObject(Response);
                    int code = userObject.getInt("status");
                    String URL = userObject.getString("URL");

                    HashMap<String, String> mp = new HashMap<>();
                    mp.put("code", code + "");
                    mp.put("URL", URL);

                    callback.onSuccess(code, mp, 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
                case CHECK_FOR_UPDATE:

                break;
                default:
                    break;
            }
            return true;
        }
    });

}
