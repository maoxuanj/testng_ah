package http.menu;

import MyTest.testBase12;
import com.test.utils.RequestUtil;
import com.test.utils.SkipHttpsUtil;
import http.TestBase;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class getRoleAll extends testBase12 {
    CloseableHttpResponse response=null;
    String entityStr = null;
    //登录url
    OkHttpClient client;
    Request request1;

    @BeforeMethod
    public void beforeClass123()  throws IOException {
        //调用teseBase中的Init方法，对url进行赋值，无需每次更新url
        init();
        //对于client初始化
        init();
        TestBase.http_user_init();
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
    }





    @Test(parameters ="")
    public void user_list_user() throws IOException {
        request1 =RequestUtil.requestGet(url+"/menu/getRoleAll",token);
        Response response = client.newCall(request1).execute();
        System.out.println(response.body().string());

    }




}
