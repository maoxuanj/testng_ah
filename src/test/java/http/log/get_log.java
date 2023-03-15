package http.log;

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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class get_log extends testBase12 {
    CloseableHttpResponse response = null;
    String entityStr = null;
    //登录url
    OkHttpClient client;

    Request request1;
    String shell;
    String shell_linux;
    MediaType mediaType;
    MediaType mediaType_text;
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
        mediaType_text = MediaType.parse("text/plain");
    }





    @Test(parameters = "",priority = 1,description = "查看是否有日志索引权限")
    public void permissions() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/permissions/protect-log", token);
        JSONObject result = TestBase.ResultHttp(request1);

    }


    @Test(parameters = "",priority = 2,description = "日志索引等级获取")
    public void get_risk() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/log/get_risk", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }


    @Test(parameters = "",priority = 3,description = "日志索引类型获取")
    public void get_log_event() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/log/get_log_event", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));

    }

    @Test(parameters = "",priority = 4,description = "系统防护")
    public void system_protect() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/log/get_log_type?eventType=%E7%B3%BB%E7%BB%9F%E9%98%B2%E6%8A%A4", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));

    }

    @Test(parameters = "",priority = 5,description = "网络防护")
    public void net_protect() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/log/get_log_type?eventType=%E7%BD%91%E7%BB%9C%E9%98%B2%E6%8A%A4", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));

    }

    @Test(parameters = "",priority = 6,description = "web应用防护")
    public void web_protect() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/log/get_log_type?eventType=Web%E5%BA%94%E7%94%A8%E9%98%B2%E6%8A%A4", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));

    }


    @Test(parameters = "",priority = 7,description = "自动响应")
    public void xiangying() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/log/get_log_type?eventType=%E8%87%AA%E5%8A%A8%E5%93%8D%E5%BA%94", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));

    }

    @Test(parameters = "",priority = 8,description = "获取查询结果")
    public void get_log() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/log/get_log?limit=20&offset=0&order=&sort=", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));

    }

    @Test(parameters = "",priority = 9,description = "导出查询结果")
    public void export_csv() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/log/export_csv?exportType=0&limit=20&offset=0&order=&sort=", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));

    }

   // @Test(parameters = "",priority = 10,description = "导出查询结果2")
    public void export_csv_file() throws IOException, InterruptedException {
        RequestBody body = RequestBody.create(mediaType, "");
        request1 = RequestUtil.requestPost1(url + "/log/export_csv_file?&username=autotest&exportType=0", body,token);
        //导出的这个response是一个非标准的东西，得想想如何处理，可以判断导出结果正常
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters = "",priority =11,description = "获取终端名称")
    public void get_nodeName() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/log/get_nodeName?key=", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
        node_id = GetidUtil.getId_no_list(linux,"ip",result);
    }


    @Test(parameters = "",priority =17,description = "根据关键词获取终端列表")
    public void get_nodeName_key () throws IOException, InterruptedException {
        request1 = RequestUtil.requestGet(url + "/log/get_nodeName?key="+linux, token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
        //这里可以加个断言，只有一个终端列表
        Assert.assertTrue(result.getJSONArray("data")!=null);
    }


    @Test(parameters = "",priority =12,description = "根据终端id查询对应日志")
    public void get_log_nodeId() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/log/get_log?limit=20&offset=0&order=&sort=&nodeId="+node_id, token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters = "",priority =13,description = "根据关键字查询对应日志")
    public void get_log_key() throws IOException, InterruptedException {
        request1 = RequestUtil.requestGet(url + "/log/get_log?limit=20&offset=0&order=&sort=&key="+node_id+"&nodeId=", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }


}
