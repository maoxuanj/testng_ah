package http.sensitive_words;

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

public class sensitive_process extends testBase12 {
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
        name = "敏感词"+Math.random();
        name_delete = "敏感词"+Math.random();
    }

    @Test(parameters = "", priority = 1)
    public void scheduled_permission() throws IOException {
        request2 = RequestUtil.requestGet(url + "/permissions/sensitive-words", token);
        JSONObject result2 = TestBase.ResultHttp(request2);

    }

    @Test(parameters = "", priority = 2,description = "获取敏感词列表")
    public void detect() throws IOException {
        request2 = RequestUtil.requestGet(url + "/sensitive_words/list?limit=20&offset=0&order=&sort=", token);
        JSONObject result = TestBase.ResultHttp(request2);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters = "", priority = 3,description = "敏感词页面获取终端列表")
    public void task_type() throws IOException {
        request2 = RequestUtil.requestGet(url + "/sensitive_words/node_list", token);
        JSONObject result = TestBase.ResultHttp(request2);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }


    @Test(parameters = "", priority = 5,description = "新增敏感词任务")
    public void add_task() throws IOException {
        RequestBody body= RequestBody.create(mediaType,"{\"freTime\":{\"fme\":0,\"fet\":{\"time\":\"10:00:49\",\"mt\":[]}},\"taskName\":"+"\""+name+"\",\"words\":\"测试\",\"nodePathList\":[{\"id\":\""+node_id+"\",\"path\":\"/usr\"}]}");
        request1 = RequestUtil.requestPost1(url + "/sensitive_words/save_task",body, token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(result.get("error_code")!=null);
        body= RequestBody.create(mediaType,"{\"freTime\":{\"fme\":0,\"fet\":{\"time\":\"10:00:49\",\"mt\":[]}},\"taskName\":"+"\""+name_delete+"\",\"words\":\"测试\",\"nodePathList\":[{\"id\":\""+node_id+"\",\"path\":\"/usr\"}]}");
        request2 = RequestUtil.requestPost1(url + "/sensitive_words/save_task",body, token);
        JSONObject result2 = TestBase.ResultHttp(request2);
        Assert.assertTrue(AssertUtil.ifsuccess(result2));
    }

    @Test(parameters = "", priority = 6,description = "查看列表，新增的任务出现在列表中")
    public void list_task() throws IOException {
        request2 = RequestUtil.requestGet(url + "/sensitive_words/list?limit=20&offset=0&order=&sort=", token);
        JSONObject result2 = TestBase.ResultHttp(request2);
        Assert.assertTrue(AssertUtil.ifsuccess(result2));
        schedule_id =GetidUtil.getId_new(name,"taskName",result2);
        schedule_id_delete = GetidUtil.getId_new(name_delete,"taskName",result2);
    }



    @Test(parameters = "", priority = 7,description = "获取敏感词任务内容")
    public void get_task() throws IOException {
        request1 = RequestUtil.requestGet(url + "/sensitive_words/get_task?id="+schedule_id, token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }


    @Test(parameters = "", priority = 8,description = "编辑敏感词任务")
    public void modify_task() throws IOException {
        name = "敏感词update";
        RequestBody body = RequestBody.create(mediaType, "{\"id\":\""+schedule_id+"\",\"taskName\":\""+name+"\",\"words\":\"测试\",\"nodePathList\":[{\"id\":\""+node_id+"\",\"path\":\"/usr\"}],\"freTime\":{\"fme\":0,\"fet\":{\"mt\":[],\"time\":\"10:00:49\"}},\"desc\":null}");
        request1 = RequestUtil.requestPost1(url + "/sensitive_words/save_task",body, token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(result.get("error_code")!=null);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters = "", priority = 9,description = "删除敏感词任务")
    public void delete_task() throws IOException {

        request1 = RequestUtil.requestDELETE(url + "/sensitive_words/del_task?ids[]="+schedule_id_delete, token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    @Test(parameters = "", priority = 10,description = "获取敏感词任务结果")
    public void get_result() throws IOException {
        request1 = RequestUtil.requestGet(url + "/sensitive_words/get_result?limit=20&offset=0&order=&sort=&words=&service=&taskId="+schedule_id, token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }




}
