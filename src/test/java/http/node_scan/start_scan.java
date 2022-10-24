package http.node_scan;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class start_scan extends testBase12 {
    CloseableHttpResponse response = null;
    String entityStr = null;
    //登录url
    OkHttpClient client;
    Request request2;
    Request request1;
    MediaType mediaType;
    MediaType mediaType_text;
    String name;
    String scan_id;
    String scan_id_1;
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
        name = "主机发现任务"+Math.random();
        RequestBody body = RequestBody.create(mediaType,"{\"ip\":\"10.50.38.1-10.50.38.2\",\"nodeId\":\"\",\"name\":"+"\""+name+"\"}");
        request1 = RequestUtil.requestPost1(url + "/node_scan/save",body, token);
        JSONObject result = TestBase.ResultHttp(request1);
        request2 = RequestUtil.requestGet(url + "/node_scan/list?limit=20&offset=0&order=&sort=", token);
        JSONObject result2 = TestBase.ResultHttp(request2);
        scan_id = GetidUtil.getId_new(name,"name",result2);
    }

    @AfterClass
    public void destroy() {
        if(scan_id!=null){
            request1 = RequestUtil.requestDELETE(url + "/node_scan/delete?ids[]="+scan_id, token);
        }
    }


    @Test(parameters = "",priority=1)
    public void start_scan() throws IOException {
        RequestBody body = RequestBody.create(mediaType,"{\"ids\":["+"\""+scan_id+"\"]}");
        request1 = RequestUtil.requestPut(url + "/node_scan/start_scan", token,body);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(result.get("error_code")!=null);

    }

}
