package http.log;

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

public class action_log extends testBase12 {
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





    @Test(parameters = "",priority = 1,description = "查看是否有操作日志权限")
    public void permissions() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/permissions/operation-log", token);
        JSONObject result = TestBase.ResultHttp(request1);

    }


    @Test(parameters = "",priority = 2,description = "操作日志类型")
    public void get_risk() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/action_log/get_event_type", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters = "",priority = 3,description = "获取查询结果")
    public void get_log() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/action_log/get_log?limit=20&offset=0&order=&sort=&key=", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));

    }

    @Test(parameters = "",priority = 9,description = "导出查询结果")
    public void export_csv() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/action_log/export_csv?exportType=0&limit=20&offset=0&order=&sort=&key=", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));

    }

    //@Test(parameters = "",priority = 10,description = "导出查询结果2")
    public void export_csv_file() throws IOException, InterruptedException {
        RequestBody body = RequestBody.create(mediaType, "");
        request1 = RequestUtil.requestPost1(url + "/action_log/export_csv_file?&username=autotest&exportType=0", body,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters = "",priority = 11,description = "导出查询结果EXCEL")
    public void export_excel() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/action_log/export_csv?exportType=1&limit=20&offset=0&order=&sort=&key=", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));

    }

    //@Test(parameters = "",priority = 13,description = "导出查询结果excel2")
    public void export_EXCEL_file() throws IOException, InterruptedException {
        RequestBody body = RequestBody.create(mediaType, "");
        request1 = RequestUtil.requestPost1(url + "/action_log/export_csv_file?&username=autotest&exportType=1", body,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }



    @Test(parameters = "",priority =14,description = "根据关键字查询对应日志")
    public void get_log_key() throws IOException, InterruptedException {
        request1 = RequestUtil.requestGet(url + "/action_log/get_log?limit=20&offset=0&order=&sort=&key=1234"+node_id, token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }


    @Test(parameters = "",priority =15,description = "仅筛选状态为成功的日志")
    public void get_log_success() throws IOException, InterruptedException {
        request1 = RequestUtil.requestGet(url + "/action_log/get_log?limit=20&offset=0&order=&sort=&key=&action[]=1", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

}
