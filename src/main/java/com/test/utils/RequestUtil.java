package com.test.utils;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

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

    public static final Request requestPatch(String url, RequestBody requestBody, String token){
        Request request = new Request.Builder()
                .url(url)
                .patch(requestBody)
                //times属性仅针对login接口
//                .addHeader("Times", "1654046536400")
                .addHeader("Authorization", "Bearer "+token)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();

        return request;
    }



    public static final Request requestPost1(String url, RequestBody requestBody, String token){
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                //times属性仅针对login接口
//                .addHeader("Times", "1654046536400")
                .addHeader("Authorization", "Bearer "+token)
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

    public static final Request requestDELETE(String url, String token){
        Request request = new Request.Builder()
                .url(url)
                .method("DELETE", null)
                .addHeader("Authorization", "Bearer "+token)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", "JSESSIONID=A1027E17F6A4B1E71CA9629BE2E5C5CD")
                .build();

        return request;
    }

    public static final Request requestPut(String url, String token, RequestBody body){
        Request request = new Request.Builder()
                .url(url)
                .method("PUT", body)
                .addHeader("Authorization", "Bearer "+token)
                .addHeader("Content-Type", "application/json")
                .build();

        return request;
    }

    public static final Request requestGet_NoToken(String url){
//        Request request = new Request.Builder()
//                .url(url)
//                .method("GET", null)
//                .addHeader("Accept", "application/json, text/plain, */*")
//                .addHeader("Accept-Language", "")
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Cookie", "token=; JSESSIONID=2EA297F115442A937B0543395A874C20")
//                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Accept-Language", "")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", "token=; JSESSIONID=2EA297F115442A937B0543395A874C20")
                .build();
        return request;
    }




}
