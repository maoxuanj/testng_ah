package Baseline_check;

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

public class baseline_process extends testBase12 {
    CloseableHttpResponse response = null;
    String entityStr = null;
    //登录url
    OkHttpClient client;
    Request request;
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
        name = "基线检查"+Math.random();
        name_delete = "基线检查_需要删除"+Math.random();
    }

    @Test(parameters = "", priority = 1)
    public void scheduled_permission() throws IOException {
        request = RequestUtil.requestGet(url + "/permissions/baseline-task", token);
        JSONObject result = TestBase.ResultHttp(request);
//        Assert.assertTrue(AssertUtil.ifsuccess(result));

    }

    @Test(parameters = "", priority = 2,description = "获取基线检查列表")
    public void detect() throws IOException {
        request = RequestUtil.requestGet(url + "/base_line/get_task_list?limit=20&offset=0&order=&sort=", token);
        JSONObject result = TestBase.ResultHttp(request);
        Assert.assertTrue(AssertUtil.ifsuccess(result));

    }
//
//    @Test(parameters = "", priority = 3,description = "获取定时任务巡检类型")
//    public void task_type() throws IOException {
//        request2 = RequestUtil.requestGet(url + "/task/task_type", token);
//        JSONObject result2 = TestBase.ResultHttp(request2);
//
//    }
//
//    @Test(parameters = "", priority = 4,description = "获取定时任务可选择终端列表")
//    public void get_node() throws IOException {
//        request2 = RequestUtil.requestGet(url + "/task/get_node", token);
//        JSONObject result2 = TestBase.ResultHttp(request2);
//    }

    @Test(parameters = "", priority = 5,description = "新增基线检查")
    public void add_task() throws IOException {
        RequestBody body = RequestBody.create(mediaType,"{\"name\":"+"\""+name_delete+"\",\"nodes\":["+"\""+node_id+"\"],\"ployIds\":[2,303],\"time\":{\"type\":\"day\",\"day\":[0],\"time\":\"00:00:00\"},\"params\":{},\"type\":1}");
        request = RequestUtil.requestPost1(url + "/base_line/save",body, token);
        JSONObject result = TestBase.ResultHttp(request);

        body = RequestBody.create(mediaType,"{\"name\":"+"\""+name+"\",\"nodes\":["+"\""+node_id+"\"],\"ployIds\":[2,303],\"time\":{\"type\":\"day\",\"day\":[0],\"time\":\"00:00:00\"},\"params\":{},\"type\":1}");
        request = RequestUtil.requestPost1(url + "/base_line/save",body, token);
        JSONObject result2 = TestBase.ResultHttp(request);
        Assert.assertTrue(AssertUtil.ifsuccess(result2));

    }

    @Test(parameters = "", priority = 6,description = "查看列表，新增的任务出现在列表中")
    public void list_task() throws IOException {
        request = RequestUtil.requestGet(url + "/base_line/get_task_list?limit=20&offset=0&order=&sort=&key=", token);
        JSONObject result = TestBase.ResultHttp(request);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
        schedule_id =GetidUtil.getId_new(name,"name",result);
        schedule_id_delete = GetidUtil.getId_new(name_delete,"name",result);
    }


    @Test(parameters = "", priority = 7,description = "编辑基线检查")
    public void modify_task() throws IOException {
        name = "基线检查update"+Math.random();
        RequestBody body = RequestBody.create(mediaType,"{\"name\":"+"\""+name+"\",\"nodes\":["+"\""+node_id+"\"],\"ployIds\":[2,303],\"time\":{\"type\":\"day\",\"day\":[0],\"time\":\"00:00:00\"},\"params\":{},\"type\":1}");
        request = RequestUtil.requestPost1(url + "/base_line/save",body, token);
        JSONObject result = TestBase.ResultHttp(request);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
        Assert.assertTrue(result.get("error_code")!=null);

    }

    @Test(parameters = "", priority = 8,description = "查看更新后的列表")
    public void list_task_afterupdate() throws IOException {
        request = RequestUtil.requestGet(url + "/base_line/get_task_list?limit=20&offset=0&order=&sort=&key=", token);
        JSONObject result = TestBase.ResultHttp(request);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
        schedule_id =GetidUtil.getId_new(name,"name",result);
        System.out.println(schedule_id);
    }

    @Test(parameters = "", priority = 9,description = "删除基线检查")
    public void delete_task() throws IOException {

        request = RequestUtil.requestDELETE(url + "/base_line/del?ids[]="+schedule_id_delete, token);
        JSONObject result = TestBase.ResultHttp(request);
        Assert.assertTrue(AssertUtil.ifsuccess(result));


    }

    @Test(parameters = "", priority = 10,description = "批量执行基线检查")
    public void start_scan() throws IOException {
        // 调试使用 schedule_id="19b60fbe-e9b2-4e4a-a761-c8bfb1f8733c";
        RequestBody body = RequestBody.create(mediaType,"{\"ids\":["+"\""+schedule_id+"\"]}");
        request = RequestUtil.requestPut(url+"/base_line/start_scan",token,body);
        JSONObject result = TestBase.ResultHttp(request);
        Assert.assertTrue(AssertUtil.ifsuccess(result));


    }

    @Test(parameters = "", priority =11,description = "查看检查结果")
    public void get_task_info_list() throws IOException {
        // 调试使用 schedule_id="19b60fbe-e9b2-4e4a-a761-c8bfb1f8733c";
        request = RequestUtil.requestGet(url + "/base_line/get_task_info_list?limit=20&offset=0&order=&sort=&key=&task_id="+schedule_id, token);
        JSONObject result = TestBase.ResultHttp(request);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters = "", priority =12,description = "查看基线结果详情")
    public void get_task_info_ploy() throws IOException {
        // 调试使用 schedule_id="19b60fbe-e9b2-4e4a-a761-c8bfb1f8733c";
        request = RequestUtil.requestGet(url + "/base_line/get_task_node_info_list?limit=20&offset=0&order=&sort=&key=&task_id="+schedule_id+"&ploy_id=303", token);
        JSONObject result = TestBase.ResultHttp(request);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }


    @Test(parameters = "", priority =13,description = "根据任务名称定向查询")
    public void search() throws IOException {
        // 调试使用 name="update";
        request = RequestUtil.requestGet(url + "/base_line/get_task_list?limit=20&offset=0&order=&sort=&key="+name,token);
        JSONObject result = TestBase.ResultHttp(request);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
        //定向搜索理论上肯定有内容，基本等于1，实际考虑复杂情况，>0即可
        Assert.assertTrue(result.getJSONObject("data").getJSONArray("list").size()>0);

    }

}
