package com.example.zzu.huzhucommunity.asynchttp;

import java.util.HashMap;

/**
 * Created by ido on 2018/1/24.
 * asyncHttp回调接口
 */

public interface AsyncHttpCallback {
    /**
     * 回调处理HTTP请求成功
     * @param statusCode 返回状态
     * @param mp 存储需要传递数据的哈希表，若无数据则 mp 为 null
     * @param requestCode 请求码，标识同一个activity的不同网络请求
     */
    void onSuccess(int statusCode, HashMap<String, String> mp, int requestCode);

    /**
     * 回调处理HTTP请求异常
     * @param statusCode 返回状态
     */
    void onError(int statusCode);

}
