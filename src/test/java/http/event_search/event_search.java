package http.event_search;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.utils.AssertUtil;
import com.test.utils.RequestUtil;
import com.test.utils.SkipHttpsUtil;
import http.TestBase;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class event_search extends testBase12 {
    CloseableHttpResponse response = null;
    String entityStr = null;
    //登录url
    OkHttpClient client;

    Request request1;
    String shell;
    String shell_linux;
    MediaType mediaType;
    MediaType mediaType_text;

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
        mediaType_text = MediaType.parse("text/plain");
    }




    //查看是否有事件调查许可
    @Test(parameters = "",priority = 1)
    public void event_search() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/permissions/event-search", token);
        JSONObject result = TestBase.ResultHttp(request1);

    }

    //获取事件调查类型
    @Test(parameters = "",priority = 2)
    public void type() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/fingerprint_audit/type", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    //获取采集配置内容
    @Test(parameters = "",priority = 3)
    public void info() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/fingerprint_audit/info", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));

    }

    //配置采集配置内容
    @Test(parameters = "",priority = 4)
    public void save() throws IOException, InterruptedException {
        //打开文件类型采集的开关
        RequestBody body = RequestBody.create(mediaType, "{\"file\":{\"open\":true,\"file_path\":\"txt\",\"file_exclude\":\"\",\"file_path_exclude\":\"\",\"file_extensions_exclude\":\"\"},\"proc\":{\"open\":true,\"name_audit\":\"\",\"name_exclude\":\"\"},\"net\":{\"open\":true,\"name_audit\":\"\",\"name_exclude\":\"\"},\"dns\":{\"open\":true,\"name_audit\":\"\",\"name_exclude\":\"\"},\"port\":{\"open\":true,\"name_audit\":\"\",\"name_exclude\":\"\"},\"user\":{\"open\":true,\"name_audit\":\"\",\"name_exclude\":\"\"},\"msconfig\":{\"open\":true,\"name_audit\":\"\",\"name_exclude\":\"\"},\"crontab\":{\"open\":true,\"name_audit\":\"\",\"name_exclude\":\"\"}}");
        request1 = RequestUtil.requestPost1(url + "/fingerprint_audit/save",body,token);
        JSONObject result = TestBase.ResultHttp(request1);
//        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }



    @Test(parameters = "",priority = 5,description = "再次获取采集配置内容，看看是否配置生效")
    public void info_check() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/fingerprint_audit/info", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
        ////验证文件类型采集的开关是否打开
        Assert.assertEquals(result.getJSONObject("data").getJSONObject("file").get("open"),true);

    }


    //搜索采集日志
    @Test(parameters = "",priority = 5)
    public void log() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/fingerprint_audit/log?limit=20&offset=0&order=&sort=&key="+linux, token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }



}
