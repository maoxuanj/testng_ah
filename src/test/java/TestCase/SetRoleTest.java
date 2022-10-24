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

import java.io.File;
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





///service/api/token这个是针对openApi拿到apiKey的，一般接口使用Login拿到token即可
    @Test(threadPoolSize = 20,invocationCount = 50)
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
        String token1 = JSONUtils.JSONchange(data,"token");
        System.out.println(token);
        System.out.println(token1);


    }

    @Test()
    public void skipSSL() throws Exception {
        CloseableHttpClient httpClient =(CloseableHttpClient)SkipHttpsUtil.wrapClient();
        HttpPost httpPost = new HttpPost("https://10.50.38.76/login");
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

    //强行增加本地cup的东西，自动化不执行
    //@Test(threadPoolSize = 20,invocationCount = 10000)
    public void add() throws IOException {
        String path1 = "D:\\zhuomian\\map\\delete\\";
        String name;
        String file_path;
        File file;
        for(int i=0;i<1000;i++){
             name = "aaa"+System.currentTimeMillis();
            file_path =path1+name+"file.txt";
            file = new File(file_path);
            file.createNewFile();
            i++;
            System.out.println(file);
        }
    }

}
