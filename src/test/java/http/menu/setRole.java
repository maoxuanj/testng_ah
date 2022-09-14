package http.menu;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.utils.SkipHttpsUtil;
import http.TestBase;
import okhttp3.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class setRole extends testBase12 {
    CloseableHttpResponse response=null;
    String entityStr = null;
    //登录url
    OkHttpClient client;


    @BeforeMethod
    public void beforeClass123()  throws IOException {
        init();
        TestBase.httpinit();
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
    }





    @Test(parameters ="")
    public void setRole() throws IOException {
        MediaType mediaType = MediaType.parse("application/json;charset=UTF-8");
        RequestBody body = RequestBody.create(mediaType, "{\"ids\":[12,1],\"name\":\"abc\",\"id\":\"\"}");
        Request request = new Request.Builder()
                .url(url+"/menu/setRole")
                .method("POST", body)
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Authorization", "Bearer eyJ1aWQiOiIwMWQxYmZhYi00ZjhjLTQ0YzUtYWI3Zi03YzY3ZjUxNjczYjkiLCJ0cyI6IjE2NTYwNTgwMDI0MDEifQ==.Njg5ZDRhNDRlNWFmM2I1NTQwZWFiNWI1YjgwNzRjMTA=")
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .addHeader("Cookie", "token=; JSESSIONID=4EACE862B718F3C54AAFA77EED8CE5AF")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(JSONObject.parseObject(response.body().string()));
    }




}
