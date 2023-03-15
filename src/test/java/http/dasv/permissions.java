package http.dasv;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.utils.AssertUtil;
import com.test.utils.RequestUtil;
import com.test.utils.SkipHttpsUtil;
import http.TestBase;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class permissions extends testBase12 {
    CloseableHttpResponse response = null;
    String entityStr = null;
    //登录url
    OkHttpClient client;

    Request request1;
    String shell;
    String shell_linux;

    @BeforeClass()
    public void beforeClass123() throws IOException {
        //调用teseBase中的Init方法，对url进行赋值，无需每次更新url
        init();
        TestBase.http_user_init_other();
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .connectTimeout(95, TimeUnit.MILLISECONDS)
                .readTimeout(95, TimeUnit.MILLISECONDS)
                .build();
    }

    @Test(parameters = "",priority = 1)
    public void dasv_screen() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/permissions/dasv-screen", token);
        Thread.sleep(3000);
        Thread.sleep(3000);
        JSONObject result = TestBase.ResultHttp(request1);

    }

    @Test(parameters = "",priority = 2)
    public void host() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/dasv/host", token);
        Thread.sleep(3000);
        Thread.sleep(3000);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters = "",priority = 3)
    public void cockpit() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + ":1081/dasv/cockpit/dasv/v1/env", token);
        Thread.sleep(3000);
        Thread.sleep(3000);
        JSONObject result = TestBase.ResultHttp(request1);
//        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters = "",priority = 4)
    public void v1_cockpit() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + ":1081/dasv/cockpit/dasv/v1/cockpit", token);
        Thread.sleep(3000);
        Thread.sleep(3000);
        JSONObject result = TestBase.ResultHttp(request1);
//        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }
}
