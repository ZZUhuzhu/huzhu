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

public class Comment {

    private static final Comment ourInstance = new Comment();
    private AsyncHttpCallback callback;
    private static final int GET_MY_COMMENT = 11501;
    private static final int GET_MENTIONED_COMMENT = 11502;
    private static final int DELETE_COMMENT = 11503;

    /**
     * 外部调用类方法，获得单体实例
     *
     * @return 单体实例
     */
    public static Comment getOurInstance() {
        return ourInstance;
    }

    /**
     * 私有构造方法
     */
    private Comment() {

    }

    public AsyncHttpCallback getCallback() {
        return this.callback;
    }


    public void getMyComment(final String userID, final String times, final AsyncHttpCallback cBack) {
        try {
            if (userID != null && times != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("userID", userID);
                params.put("times", times);
                String path = "http://139.199.38.177/huzhu/php/getMyComment.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if (i == 200) {
                            //获取结果
                            try {
                                String result = new String(bytes, "utf-8");
                                Message message = new Message();
                                message.what = GET_MY_COMMENT;
                                message.obj = result;
                                handler.sendMessage(message);
                                cBack.onSuccess(i, null);
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

    public void getMentionedComment(final String userID,final String times, final AsyncHttpCallback cBack) {
        try {
            if (userID !=null && times !=null && cBack != null){
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("userID", userID);
                params.put("times", times);
                String path = "http://139.199.38.177/huzhu/php/getMentionedComment.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if (i == 200) {
                            //获取结果
                            try {
                                String result = new String(bytes, "utf-8");
                                Message message = new Message();
                                message.what = GET_MENTIONED_COMMENT;
                                message.obj = result;
                                handler.sendMessage(message);
                                cBack.onSuccess(i, null);
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

    public void deleteComment(final String commentID, final AsyncHttpCallback cBack) {
        try {
            if ( commentID!= null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("commentID", commentID);
                String path = "http://139.199.38.177/huzhu/php/deleteComment.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if(i == 200){
                            //获取结果
                            try {
                                String result = new String(bytes,"utf-8");
                                Message message = new Message();
                                message.what = DELETE_COMMENT;
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
                case GET_MY_COMMENT:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code = userObject.getInt("status");
                        callback.onSuccess(code, null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case GET_MENTIONED_COMMENT:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code = userObject.getInt("status");
                        callback.onSuccess(code, null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case DELETE_COMMENT:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code = userObject.getInt("status");
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
