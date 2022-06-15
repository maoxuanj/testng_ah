package http.user;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.client.RestfulClientGet;
import com.test.utils.JSONParser;
import com.test.utils.RequestUtil;
import com.test.utils.SkipHttpsUtil;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class User extends testBase12 {
    CloseableHttpResponse response=null;
    String entityStr = null;
    //登录url
    OkHttpClient client;
    JSONObject responseBody;
    int responseCode;
    String postBody;
    CloseableHttpClient httpclient1;
    HttpPost httpPost1;
    CloseableHttpResponse httpResponse1;
    String token;
    RestfulClientGet clientget;
    JSONObject responseBodyget;
    JSONParser jParser;
    int responseCodeget;
    Request request;
    Request request1;

    @BeforeMethod
    public void beforeClass123() throws IOException {
        //调用teseBase中的Init方法，对url进行赋值，无需每次更新url
        init();
        //对于client初始化
         client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
      //初始化拿到token
        FormBody formBody = new FormBody.Builder().add("username","admin").add("password","VErz1M+wN/eDim4MG0fJOg==")
                .build();
        Request request = new Request.Builder()
                .url(url+"/login")
                .post(formBody)
                .addHeader("Times", "1655196405896")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        token = JSONObject.parseObject(response.body().string()).get("token").toString();
    }





    @Test(parameters ="")
    public void user_list_user() throws IOException {
        request1 =RequestUtil.requestGet(url+"/user/list_user?limit=20&offset=0",token);
        Response response = client.newCall(request1).execute();
        System.out.println(response.body().string());

    }

    @Test(parameters ="")
    public void getAllLessUser() throws IOException {
        request1 =RequestUtil.requestGet(url+"/user/getAllLessUser",token);
        Response response = client.newCall(request1).execute();
        String a= response.body().string();
        System.out.println(a);
        JSONObject jsonObject = JSONObject.parseObject(a);

        //断言举例,比较常用的就是assertTrue和assertEquals
        Assert.assertTrue(jsonObject.get("data")!=null);
        Assert.assertEquals(jsonObject.get("message"),"success");
    }

    //groups可以划分分组，后续自动化时，可以根据分组来判断是否执行
    @Test(parameters ="",groups = "password")
    public void password() throws IOException {
        request1 =RequestUtil.requestGet(url+"/password_policy/get_policy",token);
        Response response = client.newCall(request1).execute();
        System.out.println(response.body().string());
    }





}
