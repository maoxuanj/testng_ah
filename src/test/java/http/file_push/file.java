package http.file_push;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.utils.AssertUtil;
import com.test.utils.GetidUtil;
import com.test.utils.RequestUtil;
import com.test.utils.SkipHttpsUtil;
import http.TestBase;
import okhttp3.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class file extends testBase12 {
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
        request1 = RequestUtil.requestGet(url+"/network_trojan_killing/horse/list_node?limit=20&offset=0&order=&sort=",token);
        JSONObject result = TestBase.ResultHttp(request1);
        node_id = GetidUtil.getId_new(linux,"ip",result);
    }


    //获取文件推送页面内容
    @Test(parameters = "",priority = 1)
    public void dasv_screen() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/permissions/file-push", token);
        Thread.sleep(3000);
        Thread.sleep(3000);
        JSONObject result = TestBase.ResultHttp(request1);

    }

    //获取可推送终端列表
    @Test(parameters = "",priority = 2)
    public void get_labels() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/common/get_labels", token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    //推送文件
    @Test(parameters = "",priority = 3)
    public void push_file() throws IOException {
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("active","false")
                .addFormDataPart("parameter","")
                .addFormDataPart("desc","")
                //注意一下，这里filename不要出现/的路径，否则生成的文件路径是有问题的，踩坑记录-耽搁了30Min+
                .addFormDataPart("file","test_push.py",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("bingduku/henji.py")))
                .addFormDataPart("ids",node_id)
                .addFormDataPart("is_system","false")
                .build();
        request1 = RequestUtil.requestPost1(url+"/file_push/push",body, token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    //推送文件
    @Test(parameters = "",priority = 4,description = "推送文件，最高权限")
    public void push_file_system() throws IOException {
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("active","false")
                .addFormDataPart("parameter","")
                .addFormDataPart("desc","")
                //注意一下，这里不要出现/的路径，否则生成的文件路径是有问题的，踩坑记录-耽搁了30Min+
                .addFormDataPart("file","test_push.py",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("bingduku/henji.py")))
                .addFormDataPart("ids",node_id)
                .addFormDataPart("is_system","true")
                .build();
        request1 = RequestUtil.requestPost1(url+"/file_push/push",body, token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }





}
