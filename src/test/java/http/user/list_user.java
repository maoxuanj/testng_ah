package http.user;

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

import static com.test.utils.JSONUtils.JSONchange;

public class list_user extends testBase12 {
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

    @BeforeMethod()
    public void beforeClass123() throws IOException {
        //调用teseBase中的Init方法，对url进行赋值，无需每次更新url
        init();
        TestBase.httpinit();
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
    }


    @Test(parameters ="",groups = "ignore")
    public void user_list_user_demo() throws IOException {
        request1 =RequestUtil.requestGet(url+"/user/list_user?limit=20&offset=0&order=&sort=",token);
        JSONObject result = TestBase.ResultHttp(request1);
        String asjson = result.getJSONObject("data").getJSONArray("list").get(0).toString();
//        JSONObject result_json = new JSONObject(asjson);
        //断言，目前第一个账号肯定是admin，账号数量肯定大于等于1
        int totalnum = Integer.parseInt(result.getJSONObject("data").get("total").toString());
        Assert.assertTrue(totalnum >= 1);
//这几行代码比较水，非admin账号做验证比较有意义，admin账号的就是水一下了
        //
        Assert.assertEquals(JSONchange(asjson,"lessUser"),"admin");
        Assert.assertEquals(JSONchange(asjson,"realName"),"admin");
        Assert.assertEquals(JSONchange(asjson,"roleName"),"admin");
        Assert.assertEquals(JSONchange(asjson,"userName"),"admin");
        Assert.assertEquals(JSONchange(asjson,"isLock"),"5");
        Assert.assertEquals(JSONchange(asjson,"changePwd"),"5");
        Assert.assertEquals(JSONchange(asjson,"isFirst"),"0");
        Assert.assertEquals(JSONchange(asjson,"password"),"");
        Assert.assertEquals(JSONchange(asjson,"createDate"),"0");
        Assert.assertEquals(JSONchange(asjson,"failCount"),"5");
        Assert.assertEquals(JSONchange(asjson,"neverTimeout"),"300");
        Assert.assertEquals(JSONchange(asjson,"type"),"local");
        /*这里加个注释方便理解
        为啥Expected 1  ，Actual 1也是报错呢。因为JSONchange（）方法的设置了返回类型是string，
        而后面的1代表的是int 1 ，string 1 != int 1，所以会报错
         */
        Assert.assertNotEquals(JSONchange(asjson,"beUsed"),1);;
        //那么为了验证是否正确，可以有多种写法
        Assert.assertEquals(JSONchange(asjson,"beUsed"),"1");;
        Assert.assertEquals(Integer.parseInt((JSONchange(asjson,"beUsed"))),1);;
        //注意，int 1 == int 1可以用==,都是string类型的时候用.equals();
        Assert.assertTrue(JSONchange(asjson,"beUsed").equals("1"));
        Assert.assertTrue(Integer.parseInt((JSONchange(asjson,"beUsed")))==1);;
    }

    @Test(parameters ="")
    public void user_list_user() throws IOException{
        request1 =RequestUtil.requestGet(url+"/user/list_user?limit=20&offset=0&order=&sort=",token);
        JSONObject result = TestBase.ResultHttp(request1);
    }



}