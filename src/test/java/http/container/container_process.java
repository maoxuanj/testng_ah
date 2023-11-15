package http.container;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.utils.RequestUtil;
import com.test.utils.SkipHttpsUtil;
import http.TestBase;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class container_process extends testBase12 {
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
        request2 = RequestUtil.requestGet(url + "/permissions/container-list", token);
        JSONObject result = TestBase.ResultHttp(request2);
    }


    @Test(parameters = "", priority = 2,description = "查看容器安全列表")
    public void get_list() throws IOException {
        request2 = RequestUtil.requestGet(url + "/container/get_list?limit=20&offset=0&order=&sort=&key=", token);
        JSONObject result2 = TestBase.ResultHttp(request2);
        //node_id = GetidUtil.getId_new(linux, "ip", result2);
    }


}
