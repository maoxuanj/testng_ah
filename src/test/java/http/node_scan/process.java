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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class process extends testBase12 {
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
    }

    @Test(parameters = "",priority=1)
    public void save() throws IOException {
        name = "主机发现任务"+Math.random();
        RequestBody body = RequestBody.create(mediaType,"{\"ip\":\"10.50.38.1-10.50.38.2\",\"nodeId\":\"\",\"name\":"+"\""+name+"\"}");
        request1 = RequestUtil.requestPost1(url + "/node_scan/save",body, token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(result.get("error_code")!=null);

        request2 = RequestUtil.requestGet(url + "/node_scan/list?limit=20&offset=0&order=&sort=", token);
        JSONObject result2 = TestBase.ResultHttp(request2);
        //这里用了两种方式，后者更加简便，放着做个对比
        scan_id = GetidUtil.getId(name,"name",result2.getJSONObject("data").getJSONArray("list"), new Integer(result2.getJSONObject("data").get("total").toString()));
        scan_id_1 = GetidUtil.getId_new(name,"name",result2);
        System.out.println(scan_id);
        System.out.println(scan_id_1);
    }



    @Test(parameters = "",priority=2)
    public void start_scan() throws IOException {
        System.out.println("{\"ids\":["+"\""+scan_id+"\"]}");
        RequestBody body = RequestBody.create(mediaType,"{\"ids\":["+"\""+scan_id+"\"]}");
        request1 = RequestUtil.requestPut(url + "/node_scan/start_scan", token,body);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(result.get("error_code")!=null);

    }

}
