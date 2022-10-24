package http.add_assets;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.utils.RequestUtil;
import com.test.utils.SkipHttpsUtil;
import http.TestBase;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class get_download_win extends testBase12 {
    CloseableHttpResponse response = null;
    String entityStr = null;
    //登录url
    OkHttpClient client;

    Request request1;
    String shell;
    String shell_linux;

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

    @Test(parameters = "",priority = 1)
    public void download_win() throws IOException, InterruptedException {

        request1 = RequestUtil.requestGet(url + "/add_assets/download_win?group_id=1", token);
        Thread.sleep(3000);
        Thread.sleep(3000);
//        Thread.sleep(3000);
//        Thread.sleep(3000);
//        Thread.sleep(3000);
//        Thread.sleep(3000);
//        Thread.sleep(3000);
//        Thread.sleep(3000);
//        Thread.sleep(3000);
//        Thread.sleep(3000);
//        Thread.sleep(3000);
//        Thread.sleep(3000);
//        Thread.sleep(3000);
//        Thread.sleep(3000);
//        Thread.sleep(3000);
//        Thread.sleep(3000);
//        Thread.sleep(3000);
//        Thread.sleep(3000);
//        Thread.sleep(3000);
//        Thread.sleep(3000);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(result.get("data")!=null);

        shell = result.getJSONObject("data").get("onlineWinShell").toString();
        shell_linux = result.getJSONObject("data").get("onlineShell").toString();
        System.out.println(shell);
        System.out.println(shell_linux);
    }
}
