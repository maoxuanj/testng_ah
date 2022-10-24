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

public class save_task extends testBase12 {
    CloseableHttpResponse response = null;
    String entityStr = null;
    //登录url
    OkHttpClient client;

    Request request1;
    MediaType mediaType;
    MediaType mediaType_text;
    String name;
    String nodeId;
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

    @AfterMethod
    public void destroy() throws IOException {
        request1 = RequestUtil.requestGet(url + "/sensitive_words/list?limit=20&offset=0&order=&sort=", token);
        JSONObject result2 = TestBase.ResultHttp(request1);
        nodeId = GetidUtil.getId_new(name,"name",result2);
        if(nodeId!= null){
            request1 = RequestUtil.requestDELETE(url + "/sensitive_words/del_task?ids[]="+nodeId, token);
        }
    }

    @Test(parameters = "")
    public void save_task() throws IOException {
        name = "敏感词任务"+Math.random();
        RequestBody body = RequestBody.create(mediaType, "{\"freTime\":{\"fme\":0,\"fet\":{\"time\":\"17:38:21\",\"mt\":[]}},\"taskName\":"+"\""+name+"\",\"words\":\"muma\",\"desc\":\"12345\",\"nodePathList\":[{\"id\":\"051b99ae83dd1219d5289fcb2bb9686797a52938e869f3fd9d613ccda29e2ffa\",\"path\":\"C:\"}]}");
        request1 = RequestUtil.requestPost1(url+"/sensitive_words/save_task",body, token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(result.get("error_code")!=null);
    }

}
