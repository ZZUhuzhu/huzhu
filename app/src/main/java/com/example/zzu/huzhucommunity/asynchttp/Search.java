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

public class Search {
    private static final Search ourInstance = new Search();
    private AsyncHttpCallback callback;
    private static final int GET_RESOURCE_BY_KEYWORDS = 10401;

    public static final String SEARCH_CODE_JSON_KEY = "code";


    /**
     * 外部调用类方法，获得单体实例
     *
     * @return 单体实例
     */
    public static Search getOurInstance() {
        return ourInstance;
    }

    /**
     * 私有构造方法
     */
    private Search() {

    }

    /**
     * @return 返回全局回调函数
     */
    public AsyncHttpCallback getCallback() {
        return this.callback;
    }


    /**
     * 搜索功能实现API
     * @param keyWords 搜索关键字
     * @param resourceType 搜索类型
     * @param times 调用次数
     * @param cBack 回调
     */
    public void getResourceByKeyWords(final String keyWords, final String resourceType, final String times, final AsyncHttpCallback cBack) {
        try {
            if (keyWords != null && resourceType != null && cBack != null) {
                this.callback = cBack;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(3000);
                RequestParams params = new RequestParams();
                params.put("keyWords", keyWords);
                params.put("resourceType", resourceType);
                params.put("times", times);
                String path = "http://139.199.38.177/huzhu/php/getResourceByKeyWords.php";
                client.post(path, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                        //判断状态码
                        if (i == 200) {
                            //获取结果
                            try {
                                String result = new String(bytes, "utf-8");
                                Message message = new Message();
                                message.what = GET_RESOURCE_BY_KEYWORDS;
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
                case GET_RESOURCE_BY_KEYWORDS:
                try {
                    JSONObject userObject = new JSONObject(Response);
                    int code=userObject.getInt("status");

                    HashMap<String, String> mp = new HashMap<>();
                    mp.put(SEARCH_CODE_JSON_KEY, code +"");


                    callback.onSuccess(code, mp, GET_RESOURCE_BY_KEYWORDS);
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
