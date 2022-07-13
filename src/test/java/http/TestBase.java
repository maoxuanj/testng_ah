package http;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.utils.SkipHttpsUtil;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


public class TestBase extends testBase12 {
    static OkHttpClient client;
    public static String  httpinit() throws IOException {
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
        //初始化拿到token
        FormBody formBody = new FormBody.Builder().add("username","admin").add("password","SjAHJH2XrbBgqgiJcxY/dQ==")
                .build();
        Request request = new Request.Builder()
                .url(url+"/login")
                .post(formBody)
                .addHeader("Times", "1657172548579")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        token = JSONObject.parseObject(response.body().string()).get("token").toString();
        return token;
    }

    public static String  http_user_init() throws IOException {
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
        //初始化拿到token
        FormBody formBody = new FormBody.Builder().add("username","mxj").add("password","SjAHJH2XrbBgqgiJcxY/dQ==")
                .build();
        Request request = new Request.Builder()
                .url(url+"/login")
                .post(formBody)
                .addHeader("Times", "1657172548579")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        token = JSONObject.parseObject(response.body().string()).get("token").toString();
        return token;
    }


    public static  JSONObject  ResultHttp(Request request1) throws IOException {
        Response response = client.newCall(request1).execute();
        JSONObject jsonObject =JSONObject.parseObject(response.body().string());
        System.out.println(jsonObject);
        return jsonObject;
    }

}
