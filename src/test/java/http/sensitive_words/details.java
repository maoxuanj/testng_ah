package http.sensitive_words;

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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class details extends testBase12 {
    CloseableHttpResponse response = null;
    String entityStr = null;
    //登录url
    OkHttpClient client;
    Request request2;
    Request request1;

    Request request3;
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
        name = "敏感词任务"+Math.random();
        RequestBody body = RequestBody.create(mediaType,"{\"ip\":\"10.50.38.1-10.50.38.2\",\"nodeId\":\"\",\"name\":"+"\""+name+"\"}");
        //新建主机发现任务
        request1 = RequestUtil.requestPost1(url + "/node_scan/save",body, token);
        JSONObject result = TestBase.ResultHttp(request1);
        //列表获取任务id
        request2 = RequestUtil.requestGet(url + "/node_scan/list?limit=20&offset=0&order=&sort=", token);
        JSONObject result2 = TestBase.ResultHttp(request2);
        scan_id = GetidUtil.getId_new(name,"name",result2);
        //根据任务id，执行主机发现
        RequestBody body1 = RequestBody.create(mediaType,"{\"ids\":["+"\""+scan_id+"\"]}");
        request1 = RequestUtil.requestPut(url + "/node_scan/start_scan", token,body1);
        JSONObject result3 = TestBase.ResultHttp(request1);
    }

    @AfterMethod
    public void destroy() {
        if(scan_id!=null){
            request1 = RequestUtil.requestDELETE(url + "/node_scan/delete?ids[]="+scan_id, token);
        }
    }


    @Test(parameters = "",priority=1)
    public void details() throws IOException, InterruptedException {
        //等待扫描结束——因为这里网段设置较少，理论上30秒肯定出结果
        Thread.sleep(3000);
        Thread.sleep(3000);
        Thread.sleep(3000);
        Thread.sleep(3000);
        Thread.sleep(3000);
        Thread.sleep(3000);
        Thread.sleep(3000);
        Thread.sleep(3000);
        Thread.sleep(3000);
        Thread.sleep(3000);
        Thread.sleep(3000);
        Thread.sleep(3000);
        request1 = RequestUtil.requestGet(url + "/node_scan/details?limit=20&offset=0&order=&sort=&id="+scan_id+"&key=", token);
        JSONObject result = TestBase.ResultHttp(request1);
       //理论上38.2机器肯定开机，所以总数必定>0
        Assert.assertTrue(new Integer(result.getJSONObject("data").get("total").toString()) > 0);
    }

}
