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

public class ops_log extends testBase12 {
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





    @Test(parameters = "",priority = 1,description = "查看是否有运维日志权限")
    public void permissions() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/permissions/ops-log", token);
        JSONObject result = TestBase.ResultHttp(request1);

    }


    @Test(parameters = "",priority = 3,description = "运维日志类型")
    public void get_risk() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/operationlog/get_log_event", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters = "",priority = 3,description = "获取查询结果")
    public void get_log() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/operationlog/get_log?limit=20&offset=0&order=&sort=&key=&nodeId=", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));

    }

    @Test(parameters = "",priority = 9,description = "导出查询结果")
    public void export_csv() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/operationlog/export_csv?exportType=0&limit=20&offset=0&order=&sort=&key=&nodeId=", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));

    }

    //@Test(parameters = "",priority = 10,description = "导出查询结果2")
    public void export_csv_file() throws IOException, InterruptedException {
        RequestBody body = RequestBody.create(mediaType, "");
        request1 = RequestUtil.requestPost1(url + "/operationlog/export_csv_file?&username= autotest&exportType=0", body,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters = "",priority = 11,description = "终端日志类型")
    public void get_log_type_nodestatus() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/operationlog/get_log_type?eventType=%E7%BB%88%E7%AB%AF%E6%97%A5%E5%BF%97", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));

    }

    @Test(parameters = "",priority = 13,description = "性能监控类型")
    public void get_log_type_function() throws IOException {
        request1 = RequestUtil.requestGet(url + "/operationlog/get_log_type?eventType=%E6%80%A7%E8%83%BD%E7%9B%91%E6%8E%A7",token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }



    @Test(parameters = "",priority =14,description = "外设管控类型")
    public void get_log_type_peripheral() throws IOException, InterruptedException {
        request1 = RequestUtil.requestGet(url + "/operationlog/get_log_type?eventType=%E5%A4%96%E8%AE%BE%E7%AE%A1%E6%8E%A7", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }


    @Test(parameters = "",priority =15,description = "运维操作类型")
    public void get_log_type_Operation () throws IOException, InterruptedException {
        request1 = RequestUtil.requestGet(url + "/operationlog/get_log_type?eventType=%E8%BF%90%E7%BB%B4%E6%93%8D%E4%BD%9C", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters = "",priority =16,description = "获取终端列表")
    public void get_nodeName () throws IOException, InterruptedException {
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

}
