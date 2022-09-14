package http.dash_board;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.client.RestfulClientGet;
import com.test.utils.JSONParser;
import com.test.utils.RequestUtil;
import com.test.utils.SkipHttpsUtil;
import http.TestBase;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class node_state extends testBase12{
    CloseableHttpResponse response = null;
    String entityStr = null;
    //登录url
    OkHttpClient client;
    JSONObject responseBody;
    int responseCode;
    String postBody;
    CloseableHttpClient httpclient1;
    HttpPost httpPost1;
    CloseableHttpResponse httpResponse1;
    RestfulClientGet clientget;
    JSONObject responseBodyget;
    JSONParser jParser;
    int responseCodeget;
    Request request;
    Request request1;
    int webqingqiufanghu;

    @BeforeMethod()
    public void beforeClass123() throws IOException {
        //调用teseBase中的Init方法，对url进行赋值，无需每次更新url
        init();
        TestBase.http_user_init_other();
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
    }

    @Test(parameters ="")
    public void platform() throws IOException {

        request1 = RequestUtil.requestGet(url+"/dash_board/node_state",token);
        JSONObject result = TestBase.ResultHttp(request1);
//        Assert.assertTrue(result.get("data")!=null);
//        Assert.assertEquals(result.get("data"),"/licManage/exportLic");
    }

    @Test(parameters ="")
    public void get_attack_type_pro() throws IOException {

        request1 = RequestUtil.requestGet(url+"/dash_board/get_attack_type_pro?type=week&event_type=web",token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(result.get("data")!=null);
//        Assert.assertEquals(result.get("data"),"/licManage/exportLic");
    }

    @Test(parameters ="")
    public void get_attack_type_pro_month() throws IOException {

        request1 = RequestUtil.requestGet(url+"/dash_board/get_attack_type_pro?type=month&event_type=web",token);
        JSONObject result = TestBase.ResultHttp(request1);
    }

    @Test(parameters ="")
    public void get_attack_type_pro_day() throws IOException {

        request1 = RequestUtil.requestGet(url+"/dash_board/get_attack_type_pro?type=day&event_type=web",token);
        JSONObject result = TestBase.ResultHttp(request1);
    }


    @Test(parameters ="")
    public void get_last_msg() throws IOException {

        request1 = RequestUtil.requestGet(url+"/dash_board/get_last_msg?num=4",token);
        JSONObject result = TestBase.ResultHttp(request1);
    }

    @Test(parameters ="")
    public void get_security_status() throws IOException {

        request1 = RequestUtil.requestGet(url+"/dash_board/get_security_status?type=week",token);
        JSONObject result = TestBase.ResultHttp(request1);
    }

    @Test(parameters ="",description = "弱口令，病毒，待处理漏洞数量")
    public void get_security_status_day() throws IOException {

        request1 = RequestUtil.requestGet(url+"/dash_board/get_security_status?type=day",token);
        JSONObject result = TestBase.ResultHttp(request1);
    }

    @Test(parameters ="")
    public void get_security_status_month() throws IOException {

        request1 = RequestUtil.requestGet(url+"/dash_board/get_security_status?type=month",token);
        JSONObject result = TestBase.ResultHttp(request1);
    }

    @Test(parameters ="",description = "威胁区域")
    public void get_attack_source_place_top() throws IOException {

        request1 = RequestUtil.requestGet(url+"/dash_board/get_attack_source_place_top?type=week",token);
        JSONObject result = TestBase.ResultHttp(request1);
    }

    @Test(parameters ="")
    public void get_attack_source_place_top_day() throws IOException {

        request1 = RequestUtil.requestGet(url+"/dash_board/get_attack_source_place_top?type=day",token);
        JSONObject result = TestBase.ResultHttp(request1);
    }

    @Test(parameters ="")
    public void get_attack_source_place_top_month() throws IOException {

        request1 = RequestUtil.requestGet(url+"/dash_board/get_attack_source_place_top?type=month",token);
        JSONObject result = TestBase.ResultHttp(request1);
    }

    @Test(parameters ="")
    public void get_attack_source_ip_top() throws IOException {

        request1 = RequestUtil.requestGet(url+"/dash_board/get_attack_source_ip_top?type=week",token);
        JSONObject result = TestBase.ResultHttp(request1);
    }

    @Test(parameters ="")
    public void get_attack_source_ip_top_day() throws IOException {

        request1 = RequestUtil.requestGet(url+"/dash_board/get_attack_source_ip_top?type=day",token);
        JSONObject result = TestBase.ResultHttp(request1);
    }

    @Test(parameters ="")
    public void get_attack_source_ip_top_month() throws IOException {

        request1 = RequestUtil.requestGet(url+"/dash_board/get_attack_source_ip_top?type=month",token);
        JSONObject result = TestBase.ResultHttp(request1);
    }


    @Test(parameters ="",description = "安全威胁防护统计  ")
    public void get_protect_count() throws IOException {

        request1 = RequestUtil.requestGet(url+"/dash_board/get_protect_count?type=week",token);
        JSONObject result = TestBase.ResultHttp(request1);
    }

    @Test(parameters ="")
    public void get_protect_count_day() throws IOException {

        request1 = RequestUtil.requestGet(url+"/dash_board/get_protect_count?type=day",token);
        JSONObject result = TestBase.ResultHttp(request1);
        //断言：点击后跳转进入对应的日志搜索页面，查看数量是否一致
        if(result.getJSONArray("data").size()>0){
            webqingqiufanghu = (int) result.getJSONArray("data").getJSONObject(4).get("value");
            String timeStr1= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            request1 = RequestUtil.requestGet(url+"/log/get_log?limit=20&offset=0&order=&sort=&key=&nodeId=&eventStr[]=%E9%98%B2CC%E6%94%BB%E5%87%BB&eventStr[]=%E7%BD%91%E7%AB%99%E6%BC%8F%E6%B4%9E%E9%98%B2%E6%8A%A4&eventStr[]=%E7%BD%91%E7%AB%99%E8%AE%BF%E9%97%AE%E6%8E%A7%E5%88%B6&eventStr[]=%E7%BD%91%E7%AB%99IP%E9%BB%91%E7%99%BD%E5%90%8D%E5%8D%95&eventStr[]=%E7%BD%91%E9%A1%B5%E9%98%B2%E7%AF%A1%E6%94%B9&standardTimestamp[]="+timeStr1+"+00:00:00&standardTimestamp[]="+timeStr1+"+23:59:59",token);
            JSONObject result2 = TestBase.ResultHttp(request1);
            Assert.assertEquals(webqingqiufanghu,(int)result2.getJSONObject("data").get("total"));
        }

    }

    @Test(parameters ="")
    public void get_protect_count_month() throws IOException {

        request1 = RequestUtil.requestGet(url+"/dash_board/get_protect_count?type=month",token);
        JSONObject result = TestBase.ResultHttp(request1);
    }


    @Test(parameters ="")
    public void get_risk_endpoint_top() throws IOException {

        request1 = RequestUtil.requestGet(url+"/dash_board/get_risk_endpoint_top?type=week",token);
        JSONObject result = TestBase.ResultHttp(request1);
    }

    @Test(parameters ="")
    public void get_risk_endpoint_top_day() throws IOException {

        request1 = RequestUtil.requestGet(url+"/dash_board/get_risk_endpoint_top?type=day",token);
        JSONObject result = TestBase.ResultHttp(request1);
    }

    @Test(parameters ="")
    public void get_risk_endpoint_top_month() throws IOException {

        request1 = RequestUtil.requestGet(url+"/dash_board/get_risk_endpoint_top?type=month",token);
        JSONObject result = TestBase.ResultHttp(request1);
    }



}
