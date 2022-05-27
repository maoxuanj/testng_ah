package TestCase;

import com.alibaba.fastjson.JSONObject;
import com.test.client.RestfulClientPost;
import com.test.utils.JSONUtils;
import com.test.utils.SkipHttpsUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class SetRoleTest {
    CloseableHttpResponse response=null;
    String entityStr = null;
    //登录url
    String url ="https://10.50.38.76/user/list_user";
   // String url ="https://www.baidu.com";
    RestfulClientPost client;
    JSONObject responseBody;
    int responseCode;
    String postBody;
    CloseableHttpClient httpclient1;
    HttpPost httpPost1;
    CloseableHttpResponse httpResponse1;
    String token;


    @BeforeClass
    public void beforeClass() throws ClientProtocolException, IOException {
//        createSSLClientDefault();
//        client = new RestfulClientPost();
//        //用NameValuePair的list来添加请求主体参数
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("id", ""));
//        List idList = new ArrayList();
//        idList.add(11);
//        idList.add(1);
////        params.add(new BasicNameValuePair("",idList));
//        params.add(new BasicNameValuePair("name", "Test"));
//        //用哈希图准备请求头部信息
//        HashMap<String, String> hashHead = new HashMap<String, String>();
//        hashHead.put("Content-Type", "application/json;charset=UTF-8");
//        //传参发送post请求并接收反馈
//        client.sendPost(url, params, hashHead);
//
//        responseBody = client.getBodyInJSON();
//        responseCode = client.getCodeInNumber();

    }


    @Test()
    public void test() throws Exception {
        SkipHttpsUtil.sendHttpPost(url,"aaab");
    }






    @Test()
    public void login() throws Exception {
        CloseableHttpClient httpClient =(CloseableHttpClient)SkipHttpsUtil.wrapClient();
        HttpPost httpPost = new HttpPost("https://10.50.38.76/service/api/token");
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("Authorization","Bearer");
        httpPost.addHeader("User-Type","admin");

        JSONObject params = new JSONObject();

        params.put("username","admin");
        params.put("password","4X29YyXnw+zhGLXYp0VulQ==");
        params.put("type","ngfw");

        httpPost.setEntity(new StringEntity(params.toString(),"UTF-8")); //防止中文乱码


        CloseableHttpResponse response = httpClient.execute(httpPost);
        System.out.println(response);
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        System.out.println(responseContent);
        //responseContent = {"code":200,"message":"success","data":{"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IjRYMjlZeVhudyt6aEdMWFlwMFZ1bFE9PSIsImlzcyI6ImF1dGgwIiwidHlwZSI6Im5nZnciLCJleHAiOjE2NTM1ODA5NTB9.UwRlWFHx0tzMiWVV--OcyjuNyPB2jL8do0Iqpreqen0"}}
        //需要把data里面的token取出

        JSONObject jsonObject = JSONObject.parseObject(responseContent);
        System.out.println(jsonObject);
        System.out.println(jsonObject.get("data"));
        String data = jsonObject.get("data").toString();
        Assert.assertEquals(jsonObject.get("code"),200);
        token =JSONObject.parseObject(jsonObject.get("data").toString()).get("token").toString();
        String token1 = JSONUtils.JSONchange(data);
        System.out.println(token);
        System.out.println(token1);


    }

    @Test(dependsOnMethods = "login")
    public void skipSSL() throws Exception {
        CloseableHttpClient httpClient =(CloseableHttpClient)SkipHttpsUtil.wrapClient();
        HttpPost httpPost = new HttpPost("https://10.50.38.76/user/list_user");
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.addHeader("apiKey",token);
        JSONObject params = new JSONObject();
        params.put("token",token);
        params.put("limit",20);
        params.put("offset",0);
        params.put("type","ngfw");
        httpPost.setEntity(new StringEntity(params.toString(),"UTF-8")); //防止中文乱码

        CloseableHttpResponse response = httpClient.execute(httpPost);
        System.out.println(response);
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        System.out.println(responseContent);
    }

}