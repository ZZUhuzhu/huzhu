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

public class Publish {
    private static final Publish ourInstance = new Publish();
    private AsyncHttpCallback callback;
    private static final int PUBLISH_RESOURCE = 10501;

    /**
     * 外部调用类方法，获得单体实例
     *
     * @return 单体实例
     */
    public static Publish getOurInstance() {
        return ourInstance;
    }

    /**
     * 私有构造方法
     */
    private Publish() {

    }

    public AsyncHttpCallback getCallback() {
        return this.callback;
    }




    public void publishResource(final String userID, final String resourceType,
                                final String resourceTitle, final String resourceDetail,
                                final String resourceImageNumber, final String resourcePrice,
                                final String resourceDeadline, final String resourceImages,
                                final AsyncHttpCallback cBack) {
        try {
            //Images 允许为NUll
            if (userID != null && resourceType != null && resourceTitle != null && resourceDetail != null && resourceImageNumber != null && resourcePrice != null && resourceDeadline != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("userID", userID);
                params.put("resourceType", resourceType);
                params.put("resourceTitle", resourceTitle);
                params.put("resourceDetail", resourceDetail);
                params.put("resourceImageNumber", resourceImageNumber);
                params.put("resourcePrice", resourcePrice);
                params.put("resourceDeadline", resourceDeadline);
                params.put("resourceImages", resourceImages);
                String path = "http://139.199.38.177/huzhu/php/publishResource.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if (i == 200) {
                            //获取结果
                            try {
                                String result = new String(bytes, "utf-8");
                                Message message = new Message();
                                message.what = PUBLISH_RESOURCE;
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
                case PUBLISH_RESOURCE:
                try {
                    JSONObject userObject = new JSONObject(Response);
                    int code=userObject.getInt("status");
                    callback.onSuccess(code, null, 0);
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
