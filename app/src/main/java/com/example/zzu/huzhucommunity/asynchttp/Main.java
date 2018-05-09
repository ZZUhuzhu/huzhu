package com.example.zzu.huzhucommunity.asynchttp;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.zzu.huzhucommunity.dataclass.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by do_pc on 2018/1/25.
 *
 */

public class Main {
    private static final String TAG = "Main";
    private static final Main ourInstance = new Main();
    private AsyncHttpCallback callback;
    private static final int GET_NEW_RESOURCE = 10301;
    private static final int GET_REQUEST = 10302;
    private static final int GET_RESOURCE_BY_TYPE = 10303;

    public static final String REQUEST_NUMBER_JSON_KEY = "number";
    public static final String REQUEST_CODE_JSON_KEY = "code";

    /**
     * 外部调用类方法，获得单体实例
     *
     * @return 单体实例
     */
    public static Main getOurInstance() {
        return ourInstance;
    }

    /**
     * 私有构造方法
     */
    private Main() {

    }

    public AsyncHttpCallback getCallback() {
        return this.callback;
    }

    public void getNewResource(final String times, final AsyncHttpCallback cBack) {
        try {
            if (times != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("times", times);
                String path = "http://139.199.38.177/huzhu/php/getNewResource.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        //判断状态码
                        if (i == 200) {
                            //获取结果
                            try {
                                String result = new String(bytes, "utf-8");
                                Message message = new Message();
                                message.what = GET_NEW_RESOURCE;
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


    public void getRequest(final String times, final AsyncHttpCallback cBack) {
        try {
            if (times != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("times", times);
                String path = "http://139.199.38.177/huzhu/php/getRequest.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if (i == 200) {
                            //获取结果
                            try {
                                String result = new String(bytes, "utf-8");
                                Message message = new Message();
                                message.what = GET_REQUEST;
                                message.obj = result;
                                handler.sendMessage(message);
                                //cBack.onSuccess(i, null, 0);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //cBack.onError(i);
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

    public void getResourceByType(final String resourceType, final String times, final AsyncHttpCallback cBack) {
        try {
            if (resourceType != null && times != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("resourceType", resourceType);
                params.put("times", times);
                String path = "http://139.199.38.177/huzhu/php/getResourceByType.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if (i == 200) {
                            //获取结果
                            try {
                                String result = new String(bytes, "utf-8");
                                Message message = new Message();
                                message.what = GET_RESOURCE_BY_TYPE;
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
            String Response = message.obj.toString();
            switch (message.what) {
                case GET_NEW_RESOURCE:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code = userObject.getInt("status");
                        //TODO 判断返回状态码&将返回数据写进本地数据库
                        callback.onSuccess(code, null, 0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case GET_REQUEST:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code = userObject.getInt("status");
                        String number = userObject.getString("number");
                        int n = Integer.parseInt(number);
                        HashMap<String, String> mp = new HashMap<>();
                        mp.put(REQUEST_CODE_JSON_KEY, code + "");
                        mp.put(REQUEST_NUMBER_JSON_KEY, number);
                        for (int i = 0; i < n; ++i) {
                            //Log.e(TAG, "handleMessage: " + userObject.getString("" + i));
                            mp.put("" + i, userObject.getString("" + i));
                        }
//                        String json = "[";
//                        if (n > 0) json += userObject.getString("0");
//                        for (int i = 1; i < n; ++i) {
//                            json += "," + userObject.getString("" + i);
//                        }
//                        json += "]";
//                        Gson gson = new Gson();
//                        List<Request> list = gson.fromJson(json,
//                                new TypeToken<List<Request>>() {}.getType());


                        callback.onSuccess(code, mp, 0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case GET_RESOURCE_BY_TYPE:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code = userObject.getInt("status");
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
