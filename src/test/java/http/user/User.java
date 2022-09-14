package http.user;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.utils.RequestUtil;
import com.test.utils.SkipHttpsUtil;
import http.TestBase;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class User extends testBase12 {
    CloseableHttpResponse response=null;
    String entityStr = null;
    //登录url
    OkHttpClient client;
    Request request1;

    @BeforeMethod
    public void beforeClass123() throws IOException {
        init();
        TestBase.http_user_init();
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
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
