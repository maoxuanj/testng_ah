package http.weak_password;

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
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class weak_password extends testBase12 {
    OkHttpClient client;
    MediaType mediaType;
    Request request1;
    String node_id;

    @BeforeClass()
    public void beforeClass123() throws IOException {
        //调用teseBase中的Init方法，对url进行赋值，无需每次更新url
        init();
        TestBase.http_user_init_other();
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
        mediaType= MediaType.parse("application/json;charset=UTF-8");
    }
    //获取终端列表
    @Test(parameters ="",priority=1)
    public void get_node_weak_left() throws IOException {
        request1 = RequestUtil.requestGet(url+"/weak_password/get_node_weak_left?limit=20&offset=0&key=",token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
        //这里看起来特别长的原因是返回的终端列表字段对应的是weaks....很不理解
        node_id = GetidUtil.getId(linux,"ip",result.getJSONObject("data").getJSONArray("weaks"),result.getJSONObject("data").getJSONArray("weaks").size());
    }

    //获取对应终端的应用弱口令统计
    @Test(parameters ="",priority=2)
    public void get_node_detail_count() throws IOException {
        request1 = RequestUtil.requestGet(url+"/weak_password/get_node_detail_count?id="+node_id,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    //获取对应终端的应用类型
    @Test(parameters ="",priority=3)
    public void get_app_type() throws IOException {
        request1 = RequestUtil.requestGet(url+"/weak_password/get_app_type?id="+node_id,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    //获取所有终端弱口令总数
    @Test(parameters ="",priority=4)
    public void get_node_weak_left_all() throws IOException {
        request1 = RequestUtil.requestGet(url+"/weak_password/get_node_weak_left?limit=20&offset=20&key=",token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }


    @Test(parameters ="",priority=5,description = "获取规则列表")
    public void get_rule_list() throws IOException {
        request1 = RequestUtil.requestGet(url+"/weak_password/get_rule_list?limit=20&offset=0&order=&sort=",token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters ="",priority=6,description = "获取规则总数")
    public void get_rule_count() throws IOException {
        request1 = RequestUtil.requestGet(url+"/weak_password/get_rule_count",token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }



}
