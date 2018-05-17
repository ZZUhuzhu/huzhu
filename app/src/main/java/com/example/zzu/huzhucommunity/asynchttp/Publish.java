package com.example.zzu.huzhucommunity.asynchttp;


import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by do_pc on 2018/1/25.
 *
 */

public class Publish {
    private static final String TAG = "Publish";

    private static final Publish ourInstance = new Publish();
    private AsyncHttpCallback callback;
    public static final int PUBLISH_RES_REQ = 10501;
    public static final int UPLOAD_IMAGE = 10502;

    private static final String NAME_PARA = "name";
    private static final String IMAGE_PARA = "image";
    private static final String SERVER_PATH = "http://139.199.38.177/huzhu/php/SavePicture.php";

    public static final String PUBLISH_RET_RES_ID_KEY = "resourceID";


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

    /**
     * 发布新资源
     * @param userID 发布者ID
     * @param resourceType 发布的资源类型
     * @param resourceTitle 发布的资源标题
     * @param resourceDetail 发布的资源详细信息
     * @param resourceImageNumber 发布的图片数量
     * @param resourcePrice 发布的价格
     * @param resourceDeadline 资源截止日期
     * @param cBack 回调对象
     */
    public void publishResource(final String userID, final String resourceType,
                                final String resourceTitle, final String resourceDetail,
                                final String resourceImageNumber, final String resourcePrice,
                                final String resourceDeadline, final AsyncHttpCallback cBack) {
        try {
            //Images 允许为NUll
            if (userID != null && resourceType != null && resourceTitle != null && resourceDetail != null &&
                    resourceImageNumber != null && resourcePrice != null && resourceDeadline != null && cBack != null) {
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
                                message.what = PUBLISH_RES_REQ;
                                message.obj = result;
                                handler.sendMessage(message);
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
     * 上传资源，请求，用户头像图片
     * @param image 待上传的图片
     * @param name 图片名
     * @param callback 回调对象
     */
    public void uploadImage(Bitmap image, String name, final AsyncHttpCallback callback){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(3000);
        RequestParams params = new RequestParams();
        params.add(NAME_PARA, name);
        params.add(IMAGE_PARA, encodedImage);
        client.post(SERVER_PATH, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                if (i == 200)
                    callback.onSuccess(i, null, UPLOAD_IMAGE);
                else
                    callback.onError(i);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onError(i);
            }
        });
    }

    /**
     * 上传多张图片
     * @param bitmaps 图片列表
     * @param names 图片名列表
     * @param callback 回调对象
     */
    public void uploadImage(ArrayList<Bitmap> bitmaps, ArrayList<String> names, final AsyncHttpCallback callback){
        if (names == null || bitmaps == null)
            return;
        final int sz = bitmaps.size();
        for (int outI = 0; outI < sz; outI++){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmaps.get(outI).compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(3000);
            RequestParams params = new RequestParams();
            params.add(NAME_PARA, names.get(outI));
            params.add(IMAGE_PARA, encodedImage);
            final int tmpOI = outI;
            client.post(SERVER_PATH, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    if (i == 200 && tmpOI == sz - 1)
                        callback.onSuccess(i, null, UPLOAD_IMAGE);
                    else if (i != 200)
                        callback.onError(i);
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    callback.onError(i);
                }
            });
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            String Response = message.obj.toString();
            switch (message.what) {
                case PUBLISH_RES_REQ:
                try {
                    JSONObject userObject = new JSONObject(Response);
                    HashMap<String, String> mp = new HashMap<>();
                    mp.put(PUBLISH_RET_RES_ID_KEY, userObject.getString(PUBLISH_RET_RES_ID_KEY));
                    callback.onSuccess(200, mp, PUBLISH_RES_REQ);
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
