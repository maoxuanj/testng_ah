package http.microisolation;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.utils.*;
import http.TestBase;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class microisolation_label_process extends testBase12 {
    OkHttpClient client;
    MediaType mediaType;
    Request request1;
    String asjson;
    String path;
    String path_del_backup;
    String name;
    String node_id;
    String microisolation_id;


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
        name = "微隔离"+Math.random();
    }
    //获取标签下微隔离列表
    @Test(parameters ="",priority=1)
    public void get_labels_count() throws IOException {


        request1 = RequestUtil.requestGet(url+"/microisolation_label/get_labels_count",token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }


    //新增微隔离策略
    @Test(parameters ="",priority=2)
    public void add() throws IOException {
        //RequestBody body = RequestBody.create(mediaType,"{\"ids\":["+"\""+node_id+"\"],\"paths\":["+asjson+"]}");
        RequestBody body = RequestBody.create(mediaType,"{\"name\":"+"\""+name+"\",\"linkType\":0,\"local_ip\":\"*\",\"local_port\":\"*\",\"remote_ip\":"+"\""+linux+"\",\"remote_port\":\"*\",\"protocol\":65535,\"type\":0,\"active\":1,\"nodes\":["+"\""+node_id+"\"]}");
        request1 = RequestUtil.requestPost1(url+"/microisolation/add",body,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }


    //获取微隔离列表，获取新添加的微隔离id后续使用
    @Test(parameters ="",priority=3)
    public void list() throws IOException {
        request1 = RequestUtil.requestGet(url+"/microisolation/list?limit=20&offset=0&order=&sort=",token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
        microisolation_id = GetidUtil.getId_new(name,"name",result);
    }

    //触发微隔离
    @Test(parameters ="",priority=4)
    public void test_microisolation() throws IOException {
        //cmd ping
        Runtime.getRuntime().exec("cmd /k ping"+linux+"" );
        //远程连接
        ShellUtil.shellCommand(linux,linux_username,linux_password,"ls");
    }

    //关闭微隔离
    @Test(parameters ="",priority=5)
    public void unactive() throws IOException {
        RequestBody body = RequestBody.create(mediaType,"{\"ids\":["+"\""+microisolation_id+"\"]}");
        request1 = RequestUtil.requestPut(url+"/microisolation/unactive",token,body);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));

    }

    //关闭隔离后再次触发
    @Test(parameters ="",priority=6)
    public void test_microisolation_afterClose() throws IOException {
        //cmd ping
        Runtime.getRuntime().exec("cmd /k ping"+linux+"" );
        //远程连接
        ShellUtil.shellCommand(linux,linux_username,linux_password,"ls");
    }

    //开启微隔离
    @Test(parameters ="",priority=7)
    public void active() throws IOException {
        System.out.println("{\"ids\":["+"\""+microisolation_id+"\"]}");
        RequestBody body = RequestBody.create(mediaType,"{\"ids\":["+"\""+microisolation_id+"\"]}");
        request1 = RequestUtil.requestPut(url+"/microisolation/active",token,body);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));

    }

    //开启隔离后再次触发
    @Test(parameters ="",priority=8)
    public void test_microisolation_afterActive() throws IOException {
        //cmd ping
        Runtime.getRuntime().exec("cmd /k ping"+linux+"" );//诱饵文件
        //远程连接
        ShellUtil.shellCommand(linux,linux_username,linux_password,"ls");
    }



    //关闭微隔离
    @Test(parameters ="",priority=9)
    public void unactive_again() throws IOException {
        RequestBody body = RequestBody.create(mediaType,"{\"ids\":["+"\""+microisolation_id+"\"]}");
        request1 = RequestUtil.requestPut(url+"/microisolation/unactive",token,body);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));

    }
    //删除微隔离
    @Test(parameters ="",priority=10)
    public void delete() throws IOException {
        request1 = RequestUtil.requestDELETE(url+"/microisolation/delete?ids[]="+microisolation_id,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }




}
