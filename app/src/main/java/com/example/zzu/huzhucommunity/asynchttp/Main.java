package com.example.zzu.huzhucommunity.asynchttp;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.zzu.huzhucommunity.commonclass.NewRequestItem;
import com.example.zzu.huzhucommunity.commonclass.NewResourceItem;
import com.example.zzu.huzhucommunity.dataclass.Request;
import com.example.zzu.huzhucommunity.dataclass.Resource;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

    public static final int GET_NEW_RESOURCE = 10301;
    public static final int GET_NEW_REQUEST = 10302;
    public static final int GET_RESOURCE_BY_TYPE = 10303;
    public static final int UPDATE_RESOURCE = 10304;
    public static final int UPDATE_REQUEST = 10305;

    public static final String REQUEST_NUMBER_JSON_KEY = "number";
    private static final String REQUEST_CODE_JSON_KEY = "code";

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

    /**
     * 获取新资源
     * @param times 次数
     * @param cBack 回调对象
     */
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
     * 获取请求
     * @param times 次数
     * @param cBack 回调对象
     */
    public void getNewRequest(final String times, final AsyncHttpCallback cBack) {
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
                                message.what = GET_NEW_REQUEST;
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

    /**
     * 检查资源是否有更新
     * @param list 现有资源列表
     * @param callback 回调对象
     */
    public void checkNewResUpdate(final ArrayList<NewResourceItem> list, final  AsyncHttpCallback callback){
        String times = "1";
        try {
            if (callback != null) {
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("times", times);
                String path = "http://139.199.38.177/huzhu/php/getNewResource.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if (i == 200) {
                            //获取结果
                            try {
                                JSONObject userObject = new JSONObject(new String(bytes, "utf-8"));
                                StringBuilder json = new StringBuilder("[");
                                int n = (Integer) userObject.get("number");
                                if(n > 0)
                                    json.append(userObject.get("0"));
                                for(int ti = 1; ti < n; ++ti) {
                                    json.append(",").append(userObject.get("" + ti));
                                }
                                json.append("]");
                                List<Resource> resList = new Gson().fromJson(json.toString(),
                                        new TypeToken<List<Resource>>() {}.getType());
                                if (!resList.get(0).getResourceID().equals(list.get(0).getItemID())){
                                    int code = userObject.getInt("status");
                                    String number = userObject.getString("number");
                                    HashMap<String, String> mp = new HashMap<>();
                                    mp.put(REQUEST_CODE_JSON_KEY, code + "");
                                    mp.put(REQUEST_NUMBER_JSON_KEY, number);
                                    for (int ti = 0; ti < n; ++ti) {
                                        mp.put("" + ti, userObject.getString("" + ti));
                                    }
                                    callback.onSuccess(i, mp, UPDATE_RESOURCE);
                                }
                                else{
                                    callback.onSuccess(i, null, UPDATE_RESOURCE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            callback.onError(i);
                        }
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        callback.onError(i);
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
     * 检查请求是否有更新
     * @param list 现有资源列表
     * @param callback 回调对象
     */
    public void checkNewReqUpdate(final ArrayList<NewRequestItem> list, final  AsyncHttpCallback callback){
        String times = "1";
        try {
            if (callback != null) {
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
                                JSONObject userObject = new JSONObject(new String(bytes, "utf-8"));
                                StringBuilder json = new StringBuilder("[");
                                int n = (Integer) userObject.get("number");
                                if(n > 0)
                                    json.append(userObject.get("0"));
                                for(int ti = 1; ti < n; ++ti) {
                                    json.append(",").append(userObject.get("" + ti));
                                }
                                json.append("]");
                                List<Request> resList = new Gson().fromJson(json.toString(),
                                        new TypeToken<List<Request>>() {}.getType());
                                if (!resList.get(0).getResourceID().equals(list.get(0).getItemID())){
                                    int code = userObject.getInt("status");
                                    String number = userObject.getString("number");
                                    HashMap<String, String> mp = new HashMap<>();
                                    mp.put(REQUEST_CODE_JSON_KEY, code + "");
                                    mp.put(REQUEST_NUMBER_JSON_KEY, number);
                                    for (int ti = 0; ti < n; ++ti) {
                                        mp.put("" + ti, userObject.getString("" + ti));
                                    }
                                    callback.onSuccess(i, mp, UPDATE_REQUEST);
                                }
                                else{
                                    callback.onSuccess(i, null, UPDATE_REQUEST);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            callback.onError(i);
                        }
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        callback.onError(i);
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
                        String number = userObject.getString("number");
                        int n = Integer.parseInt(number);
                        HashMap<String, String> mp = new HashMap<>();
                        mp.put(REQUEST_CODE_JSON_KEY, code + "");
                        mp.put(REQUEST_NUMBER_JSON_KEY, number);
                        for (int i = 0; i < n; ++i) {
                            mp.put("" + i, userObject.getString("" + i));
                        }
                        callback.onSuccess(code, mp, GET_NEW_RESOURCE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case GET_NEW_REQUEST:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code = userObject.getInt("status");
                        String number = userObject.getString("number");
                        int n = Integer.parseInt(number);
                        HashMap<String, String> mp = new HashMap<>();
                        mp.put(REQUEST_CODE_JSON_KEY, code + "");
                        mp.put(REQUEST_NUMBER_JSON_KEY, number);
                        for (int i = 0; i < n; ++i) {
                            mp.put("" + i, userObject.getString("" + i));
                        }
                        callback.onSuccess(code, mp, GET_NEW_REQUEST);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case GET_RESOURCE_BY_TYPE:
                    try {
                        JSONObject userObject = new JSONObject(Response);
                        int code = userObject.getInt("status");
                        String number = userObject.getString("number");
                        HashMap<String, String> mp = new HashMap<>();
                        mp.put(REQUEST_CODE_JSON_KEY, userObject.getString(REQUEST_CODE_JSON_KEY));
                        mp.put(REQUEST_NUMBER_JSON_KEY, userObject.getString(REQUEST_NUMBER_JSON_KEY));
                        int n = Integer.parseInt(number);
                        for (int i = 0; i < n; ++i) {
                            mp.put("" + i, userObject.getString("" + i));
                        }
                        callback.onSuccess(code, mp, GET_RESOURCE_BY_TYPE);
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
