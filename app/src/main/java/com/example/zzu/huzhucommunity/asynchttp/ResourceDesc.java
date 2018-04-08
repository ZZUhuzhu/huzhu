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

public class ResourceDesc {
    private static final ResourceDesc ourInstance = new ResourceDesc();
    private AsyncHttpCallback callback;
    private static final int GET_RES_PUBLISHER_INFO = 10801;
    private static final int GET_RESOURCE_DESC = 10802;
    private static final int GET_RESOURCE_COMMENT = 10803;
    private static final int PUBLISH_COMMENT = 10804;
    private static final int UPDATE_STAR = 10805;
    private static final int RECEIVE_RESOURCE = 10806;
    private static final int ADD_TO_TRACK = 10807;

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
                                String result = new String(bytes,"utf-8");
                                Message message = new Message();
                                message.what = GET_RES_PUBLISHER_INFO;
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

    public void publishComment(final String userID, final String commentFather,final String commentDetail, final AsyncHttpCallback cBack) {
        try {
            if (userID != null && commentFather != null && commentDetail != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("userID", userID);
                params.put("commentFather", commentFather);
                params.put("commentDetail", commentDetail);
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

    public void updateStar(final String userID, final String resourceID,final String isStarred, final AsyncHttpCallback cBack) {
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
                case GET_RES_PUBLISHER_INFO:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        HashMap<String, String> mp = new HashMap<>();
                        int code= userObject.getInt("status");
                        mp.put("status", code + "");
                        mp.put("User_head", userObject.getString("User_head"));
                        mp.put("User_name", userObject.getString("User_name"));
                        mp.put("userLastLogin", userObject.getString("userLastLogin"));
                        callback.onSuccess(code, mp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case GET_RESOURCE_DESC:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code=userObject.getInt("status");
                        callback.onSuccess(code, null);
                        HashMap<String, String> mp = new HashMap<>();
                        mp.put("status", code + "");
                        mp.put("resourceTitle", userObject.getString("Res_title"));
                        mp.put("RresourceDetail", userObject.getString("Res_detail"));
                        mp.put("price", userObject.getString("price"));
                        mp.put("publishDate", userObject.getString("publishDate"));
                        mp.put("deadline", userObject.getString("deadline"));
                        mp.put("imageNumbers", userObject.getString("imageNumbers"));
                        mp.put("resourceStatus", userObject.getString("resourceStatus"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case GET_RESOURCE_COMMENT:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code=userObject.getInt("status");
                        callback.onSuccess(code, null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case PUBLISH_COMMENT:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code=userObject.getInt("status");
                        callback.onSuccess(code, null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case UPDATE_STAR:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code=userObject.getInt("status");
                        callback.onSuccess(code, null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case RECEIVE_RESOURCE:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code=userObject.getInt("status");
                        callback.onSuccess(code, null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case ADD_TO_TRACK:
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
