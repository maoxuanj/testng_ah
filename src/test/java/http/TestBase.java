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
        FormBody formBody = new FormBody.Builder().add("username",user_name).add("password","LYqe5P2mA/BaEeyKdSKmDA==")
                .build();
        Request request = new Request.Builder()
                .url(url+"/login")
                .post(formBody)
                .addHeader("Times", "1662445157137")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        token = JSONObject.parseObject(response.body().string()).get("token").toString();
        return token;
    }

    //




    public static String  http_user_init() throws IOException {
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
        //初始化拿到token
        FormBody formBody = new FormBody.Builder().add("username",user_name).add("password","LYqe5P2mA/BaEeyKdSKmDA==")
                .build();
        Request request = new Request.Builder()
                .url(url+"/login")
                .post(formBody)
                .addHeader("Times", "1662445157137")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        token = JSONObject.parseObject(response.body().string()).get("token").toString();
        return token;
        //或者可以通过页面触发的方式，获取token，浏览器启动，输入账号密码，初始化的东西TODO
    }


    public static String  http_user_init_other() throws IOException {
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
        //初始化拿到token
        FormBody formBody = new FormBody.Builder().add("username",user_name).add("password","4X29YyXnw+zhGLXYp0VulQ==").add("type","other")
                .build();
        Request request = new Request.Builder()
                .url(url+"/login")
                .post(formBody)
                .addHeader("Times", "1662445157137")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        token = JSONObject.parseObject(response.body().string()).get("token").toString();
        return token;

    }

    public static String  admin_login_other() throws IOException {
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
        //初始化拿到token
        FormBody formBody = new FormBody.Builder().add("username","admin").add("password","4X29YyXnw+zhGLXYp0VulQ==").add("type","other")
                .build();
        Request request = new Request.Builder()
                .url(url+"/login")
                .post(formBody)
                .addHeader("Times", "1662445157137")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        token_admin = JSONObject.parseObject(response.body().string()).get("token").toString();
        return token_admin;

    }



    public static  JSONObject  ResultHttp(Request request1) throws IOException {
        Response response = client.newCall(request1).execute();
        JSONObject jsonObject =JSONObject.parseObject(response.body().string());
        System.out.println(jsonObject);
        return jsonObject;
    }

}
