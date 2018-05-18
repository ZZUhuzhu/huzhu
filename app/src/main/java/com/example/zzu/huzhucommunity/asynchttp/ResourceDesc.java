package com.example.zzu.huzhucommunity.asynchttp;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

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

public class ResourceDesc {
    private static final String TAG = "ResourceDesc";
    private static final ResourceDesc ourInstance = new ResourceDesc();
    private AsyncHttpCallback callback;

    public static final int GET_RES_PUBLISHER_INFO = 10801;
    public static final int GET_RESOURCE_DESC = 10802;
    public static final int GET_RESOURCE_COMMENT = 10803;
    public static final int PUBLISH_COMMENT = 10804;
    public static final int UPDATE_STAR = 10805;
    public static final int RECEIVE_RESOURCE = 10806;
    public static final int ADD_TO_TRACK = 10807;

    public static final String RESOURCE_STATUS_JSON_KEY ="status",
            RESOURCE_USER_HEAD_JSON_KEY = "userHead",
            RESOURCE_USERNAME_JSON_KEY = "userName",
            RESOURCE_USER_LAST_LOGIN_JSON_KEY = "userLastLogin",
            RESOURCE_TITLE_JSON_KEY = "resourceTitle",
            RESOURCE_DETAIL_JSON_KEY = "resourceDetail",
            RESOURCE_PRICE_JSON_KEY = "resourcePrice",
            PUBLISH_DATE = "publishDate",
            DEADLINE = "deadline",
            IMAGE_NUMBERS = "resourceImageNumber",
            RESOURCE_STATUS = "resourceStatus",
            PUBLISH_STATE = "publishState",
            RESOURCE_NUMBER_JSON_KEY = "number",
            _1 = "1";
    public static final String COMMENTDetail = "commentDetail", COMMENTFather = "commentFather",  COMMENTDate = "commentDate",
            USERName = "userName", USERHead = "userHead", USERID = "commentRelatedUser";



    /**
     * 外部调用类方法，获得单体实例
     *
     * @return 单体实例
     */
    public static ResourceDesc getOurInstance() {
        return ourInstance;
    }

    /**
     * 私有构造方法
     */
    private ResourceDesc() {

    }

    public AsyncHttpCallback getCallback() {
        return this.callback;
    }

    /**
     * 获取资源发布者的信息
     * @param resourceID 资源ID
     * @param cBack 回调对象
     */
    public void getResPublisherInfo(final String resourceID, final AsyncHttpCallback cBack) {
        try {
            if (resourceID != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("resourceID", resourceID);
                String path = "http://139.199.38.177/huzhu/php/getResPublisherInfo.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if(i == 200){
                            //获取结果
                            try {
                                Message message = new Message();
                                message.what = GET_RES_PUBLISHER_INFO;
                                message.obj = new String(bytes,"utf-8");
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
     * 获取资源详情
     * @param resourceID 资源ID
     * @param cBack 回调对象
     */
    public void getResourceDesc(final String resourceID,final AsyncHttpCallback cBack) {
        try {
            if ( resourceID!= null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("resourceID", resourceID);
                String path = "http://139.199.38.177/huzhu/php/getResourceDesc.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if(i == 200){
                            //获取结果
                            try {
                                String result = new String(bytes,"utf-8");
                                Message message = new Message();
                                message.what = GET_RESOURCE_DESC;
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
     * 获取资源相关评论
     * @param resourceID 资源ID
     * @param cBack 回调对象
     */
    public void getResourceComment(final String resourceID ,final AsyncHttpCallback cBack) {
        try {
            if (resourceID != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("resourceID", resourceID);
                String path = "http://139.199.38.177/huzhu/php/getResourceComment.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if(i == 200){
                            //获取结果
                            try {
                                String result = new String(bytes,"utf-8");
                                Message message = new Message();
                                message.what = GET_RESOURCE_COMMENT;
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
     * 发布评论
     * @param userID 发布者
     * @param commentFather 评论的对象ID
     * @param commentDetail 评论内容
     * @param commentType 评论类型
     * @param commentResource 评论的资源
     * @param cBack 回调对象
     */
    public void publishComment(final String userID, final String commentFather,final String commentDetail,
                               final String commentType,final String commentResource,final AsyncHttpCallback cBack) {
        try {
            if (userID != null && commentFather != null && commentDetail != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("userID", userID);
                params.put("commentFather", commentFather);
                params.put("commentDetail", commentDetail);
                params.put("commentType", commentType);
                params.put("commentResource", commentResource);
                String path = "http://139.199.38.177/huzhu/php/publishComment.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if(i == 200){
                            //获取结果
                            try {
                                String result = new String(bytes,"utf-8");
                                Message message = new Message();
                                message.what = PUBLISH_COMMENT;
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
     * 更新收藏信息
     * @param userID 用户ID
     * @param resourceID 资源 ID
     * @param isStarred 是否已经收藏
     * @param cBack 回调对象
     */
    public void updateStar(final String userID, final String resourceID,
                           final String isStarred, final AsyncHttpCallback cBack) {
        try {
            if (userID != null && resourceID != null && isStarred != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("userID", userID);
                params.put("resourceID", resourceID);
                params.put("isStarred", isStarred);
                String path = "http://139.199.38.177/huzhu/php/updateStar.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if(i == 200){
                            //获取结果
                            try {
                                String result = new String(bytes,"utf-8");
                                Message message = new Message();
                                message.what = UPDATE_STAR;
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
     * 接单
     * @param userID 用户ID
     * @param resourceID 资源ID
     * @param cBack 回调对象
     */
    public void receiveResource(final String userID, final String resourceID, final AsyncHttpCallback cBack) {
        try {
            if ( userID!= null && resourceID!= null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("userID", userID);
                params.put("resourceID", resourceID);
                String path = "http://139.199.38.177/huzhu/php/receiveResource.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if(i == 200){
                            //获取结果
                            try {
                                String result = new String(bytes,"utf-8");
                                Message message = new Message();
                                message.what = RECEIVE_RESOURCE;
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
     * 添加到足迹
     * @param userID 用户ID
     * @param resourceID 资源ID
     * @param cBack 回调对象
     */
    public void addToTrack(final String userID, final String resourceID, final AsyncHttpCallback cBack) {
        try {
            if (userID != null && resourceID != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("userID", userID);
                params.put("resourceID", resourceID);
                String path = "http://139.199.38.177/huzhu/php/addToTrack.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if(i == 200){
                            //获取结果
                            try {
                                String result = new String(bytes,"utf-8");
                                Message message = new Message();
                                message.what = ADD_TO_TRACK;
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
            String Response = message.obj.toString();
            switch (message.what) {
                case GET_RES_PUBLISHER_INFO:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        HashMap<String, String> mp = new HashMap<>();
                        int code= userObject.getInt("status");
                        mp.put(RESOURCE_STATUS_JSON_KEY, code + "");
                        mp.put(RESOURCE_USER_HEAD_JSON_KEY,
                                userObject.getString(RESOURCE_USER_HEAD_JSON_KEY));
                        mp.put(RESOURCE_USERNAME_JSON_KEY,
                                userObject.getString(RESOURCE_USERNAME_JSON_KEY));
                        mp.put(RESOURCE_USER_LAST_LOGIN_JSON_KEY,
                                userObject.getString(RESOURCE_USER_LAST_LOGIN_JSON_KEY));
                        callback.onSuccess(code, mp, GET_RES_PUBLISHER_INFO);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case GET_RESOURCE_DESC:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code=userObject.getInt("status");
                        callback.onSuccess(code, null, 0);
                        HashMap<String, String> mp = new HashMap<>();
                        mp.put(RESOURCE_STATUS_JSON_KEY, code + "");
                        mp.put(RESOURCE_TITLE_JSON_KEY,
                                userObject.getString(RESOURCE_TITLE_JSON_KEY));
                        mp.put(RESOURCE_DETAIL_JSON_KEY, userObject.getString(RESOURCE_DETAIL_JSON_KEY));
                        mp.put(RESOURCE_PRICE_JSON_KEY,
                                userObject.getString(RESOURCE_PRICE_JSON_KEY));
                        mp.put(PUBLISH_DATE, userObject.getString(PUBLISH_DATE));
                        mp.put(DEADLINE, userObject.getString(DEADLINE));
                        mp.put(IMAGE_NUMBERS, userObject.getString(IMAGE_NUMBERS));
                        callback.onSuccess(code, mp, GET_RESOURCE_DESC);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case GET_RESOURCE_COMMENT:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code=userObject.getInt("status");
                        HashMap<String, String> mp = new HashMap<>();
                        String number =userObject.getString("number");
                        int n = Integer.parseInt(number);

                        mp.put(RESOURCE_NUMBER_JSON_KEY, number);
                        for (int i = 0; i < n; ++i) {
                            mp.put("" + i, userObject.getString("" + i));
                        }
                        callback.onSuccess(code, mp, GET_RESOURCE_COMMENT);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case PUBLISH_COMMENT:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code=userObject.getInt("status");
                        String publishState = userObject.getString(PUBLISH_STATE);
                        String resourceID = userObject.getString("resourceID");
                        String _1local = userObject.getString("1");
                        HashMap<String, String> mp = new HashMap<>();
                        mp.put(_1, _1local);
                        mp.put(RESOURCE_STATUS, resourceID);
                        mp.put(PUBLISH_STATE, publishState);

                        callback.onSuccess(code, mp, PUBLISH_COMMENT);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case UPDATE_STAR:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code=userObject.getInt("status");
                        HashMap<String, String> mp = new HashMap<>();
                        mp.put(RESOURCE_STATUS_JSON_KEY, code + "");

                        callback.onSuccess(code, mp, UPDATE_STAR);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case RECEIVE_RESOURCE:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code=userObject.getInt("status");
                        HashMap<String, String> mp = new HashMap<>();
                        mp.put(RESOURCE_STATUS_JSON_KEY, code + "");

                        callback.onSuccess(code, mp, RECEIVE_RESOURCE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case ADD_TO_TRACK:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code=userObject.getInt("status");
                        HashMap<String, String> mp = new HashMap<>();
                        mp.put(RESOURCE_STATUS_JSON_KEY, code + "");

                        callback.onSuccess(code, mp, RECEIVE_RESOURCE);
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
