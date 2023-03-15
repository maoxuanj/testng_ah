package http.node_risk;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.utils.AssertUtil;
import com.test.utils.GetidUtil;
import com.test.utils.RequestUtil;
import com.test.utils.SkipHttpsUtil;
import http.TestBase;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class risk_process extends testBase12 {
    CloseableHttpResponse response = null;
    String entityStr = null;
    //登录url
    OkHttpClient client;
    Request request2;
    Request request1;
    MediaType mediaType;
    MediaType mediaType_text;

    String node_id;


    @BeforeMethod()
    public void beforeClass123() throws IOException {
        //调用teseBase中的Init方法，对url进行赋值，无需每次更新url
        init();
        TestBase.http_user_init_other();
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
        mediaType = MediaType.parse("application/json;charset=UTF-8");
        mediaType_text = MediaType.parse("text/plain");
    }

    @Test(parameters = "", priority = 1)
    public void detect() throws IOException {
        request2 = RequestUtil.requestGet(url + "/permissions/env-detect", token);
        JSONObject result = TestBase.ResultHttp(request2);
    }


    @Test(parameters = "", priority = 2)
    public void list_node() throws IOException {
        request2 = RequestUtil.requestGet(url + "/risk/list_node?limit=20&offset=0&order=&sort=", token);
        JSONObject result2 = TestBase.ResultHttp(request2);
        node_id = GetidUtil.getId_new(linux, "ip", result2);
    }

    @Test(parameters = "", priority = 3,description = "终端评估")
    public void node_assess() throws IOException {
        RequestBody body = RequestBody.create(mediaType,"{\"ids\":["+"\""+node_id+"\"]}");
        request1 = RequestUtil.requestPost1(url+"/risk/node_assess",body,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters = "", priority = 4,description = "勒索评估")
    public void ransom_assess() throws IOException {
        RequestBody body = RequestBody.create(mediaType,"{\"ids\":["+"\""+node_id+"\"]}");
        request1 = RequestUtil.requestPost1(url+"/risk/ransom_assess",body,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters = "", priority = 5,description = "挖矿评估")
    public void mine_assess() throws IOException {
        RequestBody body = RequestBody.create(mediaType,"{\"ids\":["+"\""+node_id+"\"]}");
        request1 = RequestUtil.requestPost1(url+"/risk/mine_assess",body,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters = "", priority = 6,description = "弱口令评估")
    public void weak_assess() throws IOException {
        RequestBody body = RequestBody.create(mediaType,"{\"ids\":["+"\""+node_id+"\"]}");
        request1 = RequestUtil.requestPost1(url+"/risk/weak_assess",body,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }


    @Test(parameters = "", priority = 7,description = "断言部分判断是否执行")
    public void list_node_assert() throws IOException {
        request2 = RequestUtil.requestGet(url + "/risk/list_node?limit=20&offset=0&order=&sort=", token);
        JSONObject result = TestBase.ResultHttp(request2);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }


}
