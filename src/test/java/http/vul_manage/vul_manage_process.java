package http.vul_manage;

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

public class vul_manage_process extends testBase12 {
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
    //获取window系统漏洞级别
    @Test(parameters ="",priority=1)
    public void get_vul_level_win() throws IOException {


        request1 = RequestUtil.requestGet(url+"/vul_manage/win/get_vul_level",token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    //获取linux系统漏洞级别
    @Test(parameters ="",priority=2)
    public void get_vul_level_linux() throws IOException {


        request1 = RequestUtil.requestGet(url+"/vul_manage/linux/get_vul_level",token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    //获取linux应用漏洞级别
    @Test(parameters ="",priority=3)
    public void get_vul_type_linux() throws IOException {


        request1 = RequestUtil.requestGet(url+"/vul_manage/linux/get_vul_type",token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    //获取window应用漏洞级别
//    @Test(parameters ="",priority=4)
//    public void get_vul_type_win() throws IOException {
//
//
//        request1 = RequestUtil.requestGet(url+"/vul_manage/win/get_vul_type",token);
//        JSONObject result = TestBase.ResultHttp(request1);
//        Assert.assertTrue(AssertUtil.ifsuccess(result));
//    }

    //获取window系统漏洞列表
    @Test(parameters ="",priority=5)
    public void get_node_list_win() throws IOException {


        request1 = RequestUtil.requestGet(url+"/vul_manage/win/get_node_list?limit=20&offset=0&order=&sort=",token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

//
//    //扫描window系统漏洞
//    @Test(parameters ="",priority=6)
//    public void start_scan_win() throws IOException {
//
//        System.out.println("{\"ids\":["+"\""+node_id+"\"]}");
//        RequestBody body = RequestBody.create(mediaType,"{\"ids\":["+"\""+node_id+"\"]}");
//        request1 = RequestUtil.requestPut(url+"/vul_manage/win/start_scan",token,body);
//        JSONObject result = TestBase.ResultHttp(request1);
//
//    }
//
//    //扫描window应用漏洞
//    @Test(parameters ="",priority=7)
//    public void start_scan_winOther() throws IOException {
//
//        System.out.println("{\"ids\":["+"\""+node_id+"\"]}");
//        RequestBody body = RequestBody.create(mediaType,"{\"ids\":["+"\""+node_id+"\"],\"type\":\"winOther\"}");
//        request1 = RequestUtil.requestPut(url+"/vul_manage/linux/start_scan",token,body);
//        JSONObject result = TestBase.ResultHttp(request1);
//
//    }
//


    //扫描linux系统漏洞
    @Test(parameters ="",priority=6)
    public void start_scan_linux() throws IOException {

        System.out.println("{\"ids\":["+"\""+node_id+"\"]}");
        RequestBody body = RequestBody.create(mediaType,"{\"ids\":["+"\""+node_id+"\"]}");
        request1 = RequestUtil.requestPut(url+"/vul_manage/linux/start_scan",token,body);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));

    }

    //扫描linux应用漏洞
    @Test(parameters ="",priority=7)
    public void start_scan_linuxOther() throws IOException {

        System.out.println("{\"ids\":["+"\""+node_id+"\"]}");
        RequestBody body = RequestBody.create(mediaType,"{\"ids\":["+"\""+node_id+"\"],\"type\":\"linuxOther\"}");
        request1 = RequestUtil.requestPut(url+"/vul_manage/linux/start_scan",token,body);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));

    }



}
