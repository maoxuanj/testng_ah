package com.test.utils;

import okhttp3.FormBody;
import okhttp3.Request;

public class RequestUtil {
    public static final Request requestPost(String url, FormBody formBody){
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                //times属性仅针对login接口
//                .addHeader("Times", "1654046536400")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();

        return request;
    }

    public static final Request requestGet(String url, String token){
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Authorization", "Bearer "+token)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", "JSESSIONID=A1027E17F6A4B1E71CA9629BE2E5C5CD")
                .build();

        return request;
    }



}
