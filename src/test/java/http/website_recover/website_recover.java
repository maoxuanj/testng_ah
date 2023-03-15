package http.website_recover;

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
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class website_recover extends testBase12 {
    OkHttpClient client;
    MediaType mediaType;
    Request request1;
    Request request2;
    String node_id;
    String name;
    String name_delete;
    String schedule_id;
    String schedule_id_delete;

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
        request1 = RequestUtil.requestGet(url+"/network_trojan_killing/horse/list_node?limit=20&offset=0&order=&sort=",token);
        JSONObject result = TestBase.ResultHttp(request1);
        node_id = GetidUtil.getId_new(linux,"ip",result);

    }

    @Test(parameters ="",priority=1,description = "网站恢复许可")
    public void permissions() throws IOException {
        request1 = RequestUtil.requestGet(url+"/permissions/website-recover",token);
        JSONObject result = TestBase.ResultHttp(request1);
    }


    @Test(parameters ="",priority=2,description = "获取网站恢复列表内容")
    public void list() throws IOException {
        request1 = RequestUtil.requestGet(url+"/website_recover/list?limit=20&offset=0&order=&sort=",token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }


    @Test(parameters ="",priority=3,description = "获取发布端列表")
    public void get_push_list() throws IOException {
        request1 = RequestUtil.requestGet(url+"/website_recover/backupNode",token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }


    @Test(parameters ="",priority=4,description = "获取终端列表")
    public void node_list() throws IOException {
        request1 = RequestUtil.requestGet(url+"/website_recover/node_list",token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters = "", priority = 5,description = "新增网站恢复任务")
    public void add_task() throws IOException {
        name = "网站恢复"+Math.random();
        name_delete = "网站恢复"+Math.random();
        RequestBody body= RequestBody.create(mediaType,"{\"freTime\":{\"fme\":0,\"fet\":{\"time\":\"10:00:49\",\"mt\":[]}},\"taskName\":"+"\""+name+"\",\"words\":\"测试\",\"nodePathList\":[{\"id\":\""+node_id+"\",\"path\":\"/usr\"}]}");
        body = RequestBody.create(mediaType, "{\"taskName\":\""+name+"\",\"nodeId\":\""+node_id+"\",\"method\":0,\"recoverNodeId\":\""+node_id+"\",\"websitePath\":\"/usr/mxj\",\"excludePath\":\"\",\"recoverPath\":\"/usr/mxj1\",\"execTime\":{\"fme\":0,\"fet\":{\"mt\":[],\"time\":\"00:00:00\"}},\"remark\":\"\",\"day\":30,\"enable\":1,\"id\":\"\",\"nodeName\":\"\",\"nodeIp\":\"\",\"recoverNodeName\":\"\",\"recoverNodeIp\":\"\",\"updateTime\":\"\",\"status\":\"\"}");
        request1 = RequestUtil.requestPost1(url + "/website_recover/save_task",body, token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(result.get("error_code")!=null);
        body = RequestBody.create(mediaType, "{\"taskName\":\""+name_delete+"\",\"nodeId\":\""+node_id+"\",\"method\":0,\"recoverNodeId\":\""+node_id+"\",\"websitePath\":\"/usr/mxj\",\"excludePath\":\"\",\"recoverPath\":\"/usr/mxj1\",\"execTime\":{\"fme\":0,\"fet\":{\"mt\":[],\"time\":\"00:00:00\"}},\"remark\":\"\",\"day\":30,\"enable\":1,\"id\":\"\",\"nodeName\":\"\",\"nodeIp\":\"\",\"recoverNodeName\":\"\",\"recoverNodeIp\":\"\",\"updateTime\":\"\",\"status\":\"\"}");
        request2 = RequestUtil.requestPost1(url + "/website_recover/save_task",body, token);
        JSONObject result2 = TestBase.ResultHttp(request2);
        Assert.assertTrue(AssertUtil.ifsuccess(result2));
    }

    @Test(parameters ="",priority=6,description = "获取网站恢复列表内容，列表>=2")
    public void list_again() throws IOException {
        request1 = RequestUtil.requestGet(url+"/website_recover/list?limit=20&offset=0&order=&sort=",token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
        Assert.assertTrue((int)result.getJSONObject("data").get("total")>=2);
        schedule_id =GetidUtil.getId_new(name,"taskName",result);
        schedule_id_delete = GetidUtil.getId_new(name_delete,"taskName",result);
        System.out.println(schedule_id);
        System.out.println(schedule_id_delete);
    }

    @Test(parameters ="",priority=7,description = "编辑网站恢复任务")
    public void update() throws IOException {
        name = "update"+Math.random();
        RequestBody body = RequestBody.create(mediaType, "{\"taskName\":\""+name+"\",\"nodeId\":\""+node_id+"\",\"method\":0,\"recoverNodeId\":\""+node_id+"\",\"websitePath\":\"/usr/mxj1\",\"excludePath\":\"\",\"recoverPath\":\"/usr/mxj1\",\"execTime\":{\"fme\":0,\"fet\":{\"mt\":[],\"time\":\"00:00:00\"}},\"remark\":\"\",\"day\":30,\"enable\":1,\"id\":\"26\",\"nodeName\":\"centos6.7-x64-2.6.32-573\",\"nodeIp\":\"10.50.38.44\",\"recoverNodeName\":\"centos6.7-x64-2.6.32-573\",\"recoverNodeIp\":\"10.50.38.44\",\"updateTime\":\"2023-02-22 11:22:01\",\"status\":\"未开始\"}");
        request1 = RequestUtil.requestPost1(url+"/website_recover/save_task",body,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters = "", priority = 8,description = "删除网站恢复任务")
    public void delete_task() throws IOException {

        request1 = RequestUtil.requestDELETE(url + "/website_recover/del_task?ids[]="+schedule_id_delete, token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters ="",priority=9,description = "获取网站恢复列表内容，被删除任务不在列表中")
    public void list_3() throws IOException {
        request1 = RequestUtil.requestGet(url+"/website_recover/list?limit=20&offset=0&order=&sort=",token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
        Assert.assertFalse(GetidUtil.getId_new(name_delete,"taskName",result).equals(schedule_id_delete));
    }


}
