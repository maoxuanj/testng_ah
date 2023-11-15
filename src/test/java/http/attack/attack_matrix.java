package http.attack;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.utils.RequestUtil;
import com.test.utils.SkipHttpsUtil;
import http.TestBase;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class attack_matrix extends testBase12 {
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

    @Test(parameters = "", priority = 1,description = "查看是否有权限")
    public void permissions() throws IOException {
        request2 = RequestUtil.requestGet(url + "/permissions/attack-matrix", token);
        JSONObject result = TestBase.ResultHttp(request2);
    }


    @Test(parameters = "", priority = 2,description = "攻击信息")
    public void get_list() throws IOException {
        request2 = RequestUtil.requestGet(url + "/alarms/attack_info", token);
        JSONObject result2 = TestBase.ResultHttp(request2);
    }


    @Test(parameters = "", priority = 3,description = "攻击消息message")
    public void get_filters_list() throws IOException {
        request2 = RequestUtil.requestGet(url + "/matrix/matrix_message", token);
        JSONObject result2 = TestBase.ResultHttp(request2);
    }

    @Test(parameters = "", priority = 4,description = "查看设备审批类型列表")
    public void get_device_type() throws IOException {
        request2 = RequestUtil.requestGet(url + "/peripherals/get_device_type", token);
        JSONObject result2 = TestBase.ResultHttp(request2);
    }

    @Test(parameters = "", priority = 5,description = "设置设备审批类型列表")
    public void set_device_type() throws IOException {
        RequestBody body = RequestBody.create(mediaType, "{\"auto_apply\":0,\"auto_acl\":2}");
        request2 = RequestUtil.requestPatch(url + "/peripherals/set_device_type", body,token);
        JSONObject result2 = TestBase.ResultHttp(request2);
    }

    @Test(parameters = "", priority = 6,description = "设置设备审批类型列表")
    public void exportCSV() throws IOException {
        request2 = RequestUtil.requestGet(url + "/peripherals/exportCSV?key=",token);
        JSONObject result2 = TestBase.ResultHttp(request2);
    }


}
