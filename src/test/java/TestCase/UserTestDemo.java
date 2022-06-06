package TestCase;

import com.alibaba.fastjson.JSONObject;
import com.test.client.RestfulClientGet;
import com.test.utils.JSONParser;
import com.test.utils.SkipHttpsUtil;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class UserTest {
    CloseableHttpResponse response=null;
    String entityStr = null;
    //登录url
    String url ="https://10.20.128.74";
    // String url ="https://www.baidu.com";
    OkHttpClient client;
    JSONObject responseBody;
    int responseCode;
    String postBody;
    CloseableHttpClient httpclient1;
    HttpPost httpPost1;
    CloseableHttpResponse httpResponse1;
    String token;
    RestfulClientGet clientget;
    JSONObject responseBodyget;
    JSONParser jParser;
    int responseCodeget;

    //一个比较简单的testdemo，包含了get,post两种类型的Http接口

    @BeforeMethod
    public void beforeClass123()  {
                OkHttpClient client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();


    }




    //priority属性表示优先级字段，从小到大依次执行
    @Test(priority = 1)
    public void login() throws Exception{
        CloseableHttpClient httpClient =(CloseableHttpClient)SkipHttpsUtil.wrapClient();
        HttpPost httpPost = new HttpPost(url+"/login");
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        //httpPost.addHeader("Times",String.valueOf(System.currentTimeMillis()));
        httpPost.addHeader("Times","1654046536400");
        JSONObject params = new JSONObject();
        params.put("username","admin");
        //这里的密码是通过时间戳的方式加密，需要加密的方式不能写死
        params.put("password","1foU/PiaMrJdyErmMAj+Vg==");
        httpPost.setEntity(new StringEntity(params.toString(),"UTF-8")); //防止中文乱码
        CloseableHttpResponse response = httpClient.execute(httpPost);
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "username=admin&password=1foU/PiaMrJdyErmMAj+Vg==");
        System.out.println(response);
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        System.out.println(responseContent);


    }



    @Test
    public void login_postmanchange() throws Exception{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//        RequestBody body = RequestBody.create(mediaType, "username=admin&password=1foU/PiaMrJdyErmMAj+Vg==");
        FormBody formBody = new FormBody.Builder().add("username","admin").add("password","1foU/PiaMrJdyErmMAj+Vg==")
                .build();
        Request request = new Request.Builder()
                .url("https://10.20.128.74/login")
                .post(formBody)
                .addHeader("Times", "1654046536400")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        String a= response.body().string();
//        System.out.println(response.body().string());
        System.out.println(a);
        JSONObject jsonObject = JSONObject.parseObject(a);
        token = jsonObject.get("token").toString();
    }


    //dependsOnMethods表示依赖前一个方法测试通过，该方法才执行，若不通过，则跳过执行
    @Test(dependsOnMethods ="login_postmanchange" )
    public void user_list_user() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
        Request request = new Request.Builder()
                .url("https://10.20.128.74/user/list_user?limit=20&offset=0")
                .method("GET", null)
                .addHeader("Authorization", "Bearer "+token)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", "JSESSIONID=A1027E17F6A4B1E71CA9629BE2E5C5CD")
                .build();
        Response response = client.newCall(request).execute();
        String a= response.body().string();
//        System.out.println(response.body().string());
        System.out.println(a);

    }


    @Test
    public void getAllLessUser() throws IOException {

        OkHttpClient    client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
        Request request = new Request.Builder()
                .url("https://10.20.128.74/user/getAllLessUser")
                .method("GET", null)
                .addHeader("Authorization", "Bearer eyJ1aWQiOiIwMWQxYmZhYi00ZjhjLTQ0YzUtYWI3Zi03YzY3ZjUxNjczYjkiLCJ0cyI6IjE2NTQwNzA3MTc1MjcifQ==.ZTVmNjFlMzE4ZmY5Mjc4ZmRhOTdmNTMxYjdiNDA4YmU=")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", "JSESSIONID=A1027E17F6A4B1E71CA9629BE2E5C5CD")
                .build();
        Response response = client.newCall(request).execute();
        String a= response.body().string();
        System.out.println(a);
        JSONObject jsonObject = JSONObject.parseObject(a);

        //断言举例,比较常用的就是assertTrue和assertEquals

        Assert.assertTrue(jsonObject.get("data")!=null);
        Assert.assertEquals(jsonObject.get("message"),"success");
    }

    //groups可以划分分组，后续自动化时，可以根据分组来判断是否执行
    @Test(groups = "password")
    public void password() throws IOException {
        OkHttpClient    client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
        Request request = new Request.Builder()
                .url("https://10.20.128.74/password_policy/get_policy")
                .method("GET", null)
                .addHeader("Authorization", "Bearer eyJ1aWQiOiIwMWQxYmZhYi00ZjhjLTQ0YzUtYWI3Zi03YzY3ZjUxNjczYjkiLCJ0cyI6IjE2NTQwNzA3MTc1MjcifQ==.ZTVmNjFlMzE4ZmY5Mjc4ZmRhOTdmNTMxYjdiNDA4YmU=")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", "JSESSIONID=A1027E17F6A4B1E71CA9629BE2E5C5CD")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }





}
