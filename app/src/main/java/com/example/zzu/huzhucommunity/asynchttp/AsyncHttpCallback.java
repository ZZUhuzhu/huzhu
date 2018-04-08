package com.example.zzu.huzhucommunity.asynchttp;

import java.util.HashMap;

/**
 * Created by ido on 2018/1/24.
 * asyncHttp回调接口
 */

public interface AsyncHttpCallback {
    /**
     * 回调处理HTTP请求成功
     * @param code 返回状态
     */
    void onSuccess(int code, HashMap<String, String> mp);

    /**
     * 回调处理HTTP请求异常
     * @param code 返回状态
     */
    void onError(int code);

}
