package http.groupAndtitle;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.utils.AssertUtil;
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

public class title extends testBase12 {
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
    //查看许可是否存在
    @Test(parameters ="",priority=1)
    public void permissions() throws IOException {
        request1 = RequestUtil.requestGet(url+"/permissions/asset-group-tag",token);
        JSONObject result = TestBase.ResultHttp(request1);

    }

    //获取标签列表
    @Test(parameters ="",priority=2)
    public void get_labels() throws IOException {
        request1 = RequestUtil.requestGet(url+"/group_label/get_labels?limit=20&offset=0&order=&sort=",token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }


    //获取分组列表
    @Test(parameters ="",priority=3)
    public void find_group() throws IOException {
        request1 = RequestUtil.requestGet(url+"/group_label/find_group?_time=1671009737161&order=&sort=&name=",token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }



}
