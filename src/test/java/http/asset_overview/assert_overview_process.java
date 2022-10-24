package http.asset_overview;

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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class assert_overview_process extends testBase12{
    //登录url
    OkHttpClient client;
    MediaType mediaType;

    MediaType mediaType_text;
    Request request1;
    String templateId;
    String templateId_testallconfig;
    JSONObject mainConfig;
    JSONObject mainConfig_new;
    String templateId_create;
    String nodeId;
    String path1;
    String fileName;
    String nodeid;
    @BeforeMethod()
    public void beforeClass123() throws IOException {
        //调用teseBase中的Init方法，对url进行赋值，无需每次更新url
        init();
        TestBase.http_user_init_other();
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .readTimeout(25000, TimeUnit.MILLISECONDS)
                .connectTimeout(25000, TimeUnit.MILLISECONDS)
                .build();
        mediaType= MediaType.parse("application/json;charset=UTF-8");
        mediaType_text = MediaType.parse("text/plain");
        path1 = "danjituozhan";
        fileName = "neiwang.py";
    }
    //获取终端列表
    @Test(parameters ="",priority=1,description = "当前租户终端列表")
    public void list_template() throws IOException {
        request1 = RequestUtil.requestGet(url + "/asset_overview/node/findNode?limit=20&offset=0&order=&sort=&key=", token);
        JSONObject result = TestBase.ResultHttp(request1);
        nodeid=GetidUtil.getId_new(linux,"ip",result);
        System.out.println(nodeid);
    }

    @Test(parameters ="",priority=2,description = "根据ip关键词搜索")
    public void list_template_search() throws IOException {
        request1 = RequestUtil.requestGet(url + "/asset_overview/node/findNode?limit=20&offset=0&order=&sort=&key="+linux+"&ids[]="+nodeid, token);
        JSONObject result = TestBase.ResultHttp(request1);
    }

    @Test(parameters ="",priority=3,description = "停止防护")
    public void set_protect_status_stop() throws IOException, InterruptedException {
        RequestBody body = RequestBody.create(mediaType,"{\"status\":0,\"ids\":[\""+nodeid+"\"]}");
        request1 = RequestUtil.requestPost1(url + "/asset_overview/node/set_protect_status",body, token);
        JSONObject result = TestBase.ResultHttp(request1);
        Thread.sleep(3000);
    }



    @Test(parameters ="",priority=6,description = "升级客户端")
    public void update_program() throws IOException {
        RequestBody body = RequestBody.create(mediaType,"{\"type\":0,\"ids\":[\""+nodeid+"\"]}");
        request1 = RequestUtil.requestPost1(url + "/upgrade_manage/update_program",body, token);
        JSONObject result = TestBase.ResultHttp(request1);
    }

    @Test(parameters ="",priority=7,description = "消息推送")
    public void batch_sys_broadcast() throws IOException {
        RequestBody body = RequestBody.create(mediaType,"{\"time\":0,\"text\":\"测试推送消息\",\"ids\":[\""+nodeid+"\"]}");
        request1 = RequestUtil.requestPost1(url + "/asset_overview/node/batch_sys_broadcast",body, token);
        JSONObject result = TestBase.ResultHttp(request1);
    }


    @Test(parameters ="",priority=8,description = "重启客户端")
    public void batch_client_reboots() throws IOException, InterruptedException {
        RequestBody body = RequestBody.create(mediaType,"{\"ids\":[\""+nodeid+"\"]}");
        request1 = RequestUtil.requestPost1(url + "/asset_overview/node/batch_client_reboots",body, token);
        JSONObject result = TestBase.ResultHttp(request1);
        Thread.sleep(3000);
    }

    @Test(parameters ="",priority=9,description = "启动防护")
    public void set_protect_status_start() throws IOException, InterruptedException {
        RequestBody body = RequestBody.create(mediaType,"{\"status\":1,\"ids\":[\""+nodeid+"\"]}");
        request1 = RequestUtil.requestPost1(url + "/asset_overview/node/set_protect_status",body, token);
        Thread.sleep(3000);
        JSONObject result = TestBase.ResultHttp(request1);
        Thread.sleep(3000);

    }

    @Test(parameters ="",priority=50,description = "重启主机")
    public void batch_sys_reboot() throws IOException, InterruptedException {
        RequestBody body = RequestBody.create(mediaType,"{\"ids\":[\""+nodeid+"\"]}");
        request1 = RequestUtil.requestPost1(url + "/asset_overview/node/batch_sys_reboot",body, token);

        JSONObject result = TestBase.ResultHttp(request1);

    }

    @Test(parameters ="",priority=51,description = "卸载客户端")
    public void uninstallMain() throws IOException, InterruptedException {
        RequestBody body = RequestBody.create(mediaType,"{\"ids\":[\""+nodeid+"\"]}");
        request1 = RequestUtil.requestPost1(url + "/asset_overview/node/uninstallMain",body, token);
        Thread.sleep(3000);
        JSONObject result = TestBase.ResultHttp(request1);
        Thread.sleep(3000);
    }

    @Test(parameters ="",priority=52,description = "删除终端")
    public void removeBind() throws IOException, InterruptedException {
        request1 = RequestUtil.requestDELETE(url + "/asset_overview/node/removeBind?ids[]="+nodeid, token);
        Thread.sleep(3000);
        Thread.sleep(3000);
        Thread.sleep(3000);
        Thread.sleep(3000);
        JSONObject result = TestBase.ResultHttp(request1);
    }


}