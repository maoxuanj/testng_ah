package http.node_scan;

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
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class add extends testBase12 {
    CloseableHttpResponse response = null;
    String entityStr = null;
    //登录url
    OkHttpClient client;

    Request request1;
    MediaType mediaType;
    MediaType mediaType_text;
    String name;
    @BeforeMethod()
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

    @Test(parameters = "")
    public void save() throws IOException {
        name = "faxian"+Math.random();
        RequestBody body = RequestBody.create(mediaType,"{\"ip\":\"10.50.38.1-10.50.38.2\",\"nodeId\":\"\",\"name\":"+"\""+name+"\"}");
        request1 = RequestUtil.requestPost1(url + "/node_scan/save",body, token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(result.get("error_code")!=null);
    }

}
