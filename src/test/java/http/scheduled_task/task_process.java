package http.scheduled_task;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.utils.AssertUtil;
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
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class task_process extends testBase12 {
    CloseableHttpResponse response = null;
    String entityStr = null;
    //登录url
    OkHttpClient client;
    Request request2;
    Request request1;
    MediaType mediaType;
    MediaType mediaType_text;

    String node_id;
    String name;
    String schedule_id;
    String name_delete;
    String schedule_id_delete;


    @BeforeTest()
    public void beforeClass123() throws IOException {
        //调用teseBase中的Init方法，对url进行赋值，无需每次更新url
        init();
        TestBase.http_user_init_other();
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
        mediaType= MediaType.parse("application/json;charset=UTF-8");
        request1 = RequestUtil.requestGet(url+"/network_trojan_killing/horse/list_node?limit=20&offset=0&order=&sort=",token);
        JSONObject result = TestBase.ResultHttp(request1);
        node_id = GetidUtil.getId_new(linux,"ip",result);
        name = "定时巡检"+Math.random();
        name_delete = "定时巡检删除"+Math.random();
    }

    @Test(parameters = "", priority = 1)
    public void scheduled_permission() throws IOException {
        request2 = RequestUtil.requestGet(url + "/permissions/scheduled-task", token);
        JSONObject result2 = TestBase.ResultHttp(request2);

    }

    @Test(parameters = "", priority = 2,description = "获取定期巡检列表")
    public void detect() throws IOException {
        request2 = RequestUtil.requestGet(url + "/task/get_task_list?limit=20&offset=0&order=&sort=", token);
        JSONObject result = TestBase.ResultHttp(request2);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters = "", priority = 3,description = "获取定时任务巡检类型")
    public void task_type() throws IOException {
        request2 = RequestUtil.requestGet(url + "/task/task_type", token);
        JSONObject result = TestBase.ResultHttp(request2);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters = "", priority = 4,description = "获取定时任务可选择终端列表")
    public void get_node() throws IOException {
        request2 = RequestUtil.requestGet(url + "/task/get_node", token);
        JSONObject result = TestBase.ResultHttp(request2);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters = "", priority = 5,description = "新增定时任务")
    public void add_task() throws IOException {
        RequestBody body = RequestBody.create(mediaType,"{\"name\":"+"\""+name+"\",\"time\":{\"type\":\"day\",\"day\":[0],\"time\":\"00:00:00\"},\"type\":[\"antiav_quick\",\"weakPassword\",\"vulfix\"],\"node_ids\":["+"\""+node_id+"\"],\"desc\":\"8888\",\"flagAll\":false,\"id\":\"\"}");
        request1 = RequestUtil.requestPost1(url + "/task/add_task",body, token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(result.get("error_code")!=null);
        body = RequestBody.create(mediaType,"{\"name\":"+"\""+name_delete+"\",\"time\":{\"type\":\"day\",\"day\":[0],\"time\":\"00:00:00\"},\"type\":[\"antiav_quick\",\"weakPassword\",\"vulfix\"],\"node_ids\":["+"\""+node_id+"\"],\"desc\":\"8888\",\"flagAll\":false,\"id\":\"\"}");
        request2 = RequestUtil.requestPost1(url + "/task/add_task",body, token);
        JSONObject result2 = TestBase.ResultHttp(request2);
        Assert.assertTrue(AssertUtil.ifsuccess(result2));
    }

    @Test(parameters = "", priority = 6,description = "查看列表，新增的任务出现在列表中")
    public void list_task() throws IOException {
        request2 = RequestUtil.requestGet(url + "/task/get_task_list?limit=20&offset=0&order=&sort=", token);
        JSONObject result2 = TestBase.ResultHttp(request2);
        Assert.assertTrue(AssertUtil.ifsuccess(result2));
        schedule_id =GetidUtil.getId_new(name,"name",result2);
        schedule_id_delete = GetidUtil.getId_new(name_delete,"name",result2);
    }


    @Test(parameters = "", priority = 7,description = "编辑定时任务")
    public void modify_task() throws IOException {
        name = "定时巡检"+Math.random();
        RequestBody body = RequestBody.create(mediaType,"{\"name\":"+"\""+name+"\",\"time\":{\"type\":\"day\",\"day\":[0],\"time\":\"00:00:00\"},\"type\":[\"antiav_quick\",\"weakPassword\",\"vulfix\"],\"node_ids\":["+"\""+node_id+"\"],\"desc\":\"update\",\"flagAll\":false,\"id\":"+"\""+schedule_id+"\"}");
        request1 = RequestUtil.requestPatch(url + "/task/modify_task",body, token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(result.get("error_code")!=null);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters = "", priority = 8,description = "删除定时任务")
    public void delete_task() throws IOException {

        request1 = RequestUtil.requestDELETE(url + "/task/delete_task?ids[]="+schedule_id_delete, token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }


}
