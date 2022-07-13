package http.user;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.client.RestfulClientGet;
import com.test.utils.JSONParser;
import com.test.utils.RequestUtil;
import com.test.utils.SkipHttpsUtil;
import http.TestBase;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class permissions extends testBase12 {
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
    @Test(parameters ="")
    public void user_list_user() throws IOException {
        request1 = RequestUtil.requestGet(url+"/permissions/user_manage",token);
        JSONObject result = TestBase.ResultHttp(request1);
//        Assert.assertTrue(result.get("data")!=null);
//        Assert.assertEquals(result.get("data"),"/licManage/exportLic");
    }

    @Test(parameters ="")
    public void permissions() throws IOException {
        request1 = RequestUtil.requestGet(url+"/permissions",token);
        JSONObject result = TestBase.ResultHttp(request1);
//        Assert.assertTrue(result.get("data")!=null);
//        Assert.assertEquals(result.get("data"),"/licManage/exportLic");
    }


    @Test(parameters ="")
    public void permissions_user() throws IOException {
        TestBase.http_user_init();
        request1 = RequestUtil.requestGet(url+"/permissions",token);
        JSONObject result = TestBase.ResultHttp(request1);
//        Assert.assertTrue(result.get("data")!=null);
//        Assert.assertEquals(result.get("data"),"/licManage/exportLic");
    }

    @Test(parameters ="")
    public void profile() throws IOException {
        TestBase.http_user_init();
        request1 = RequestUtil.requestGet(url+"/profile",token);
        JSONObject result = TestBase.ResultHttp(request1);
//        Assert.assertTrue(result.get("data")!=null);
//        Assert.assertEquals(result.get("data"),"/licManage/exportLic");
    }

    @Test(parameters ="")
    public void home() throws IOException {
        TestBase.http_user_init();
        request1 = RequestUtil.requestGet(url+"/permissions/home",token);
        JSONObject result = TestBase.ResultHttp(request1);
//        Assert.assertTrue(result.get("data")!=null);
//        Assert.assertEquals(result.get("data"),"/licManage/exportLic");
    }



}