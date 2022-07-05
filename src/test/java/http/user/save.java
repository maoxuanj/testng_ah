package http.user;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.client.RestfulClientGet;
import com.test.utils.JSONParser;
import com.test.utils.SkipHttpsUtil;
import http.TestBase;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class save extends testBase12 {
    CloseableHttpResponse response = null;
    String entityStr = null;
    //登录url
    OkHttpClient client;
    JSONObject responseBody;
    int responseCode;
    String postBody;
    CloseableHttpClient httpclient1;
    HttpPost httpPost1;
    CloseableHttpResponse httpResponse1;
    RestfulClientGet clientget;
    JSONObject responseBodyget;
    JSONParser jParser;
    int responseCodeget;
    Request request;
    Request request1;

    @BeforeMethod()
    public void beforeClass123() throws IOException {
        //调用teseBase中的Init方法，对url进行赋值，无需每次更新url
        init();
        TestBase.httpinit();
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
    }
    @Test(parameters ="",threadPoolSize = 20)
    public void user_list_user() throws IOException {
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
        //初始化拿到token
        FormBody formBody = new FormBody.Builder().add("username", "admin").add("password", "KCpqAQ2l1b31dQD8HyMFYg==")
                .build();
        Request request = new Request.Builder()
                .url(url + "/login")
                .post(formBody)
                .addHeader("Times", "1656057556265")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
    }
}