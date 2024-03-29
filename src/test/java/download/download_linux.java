package download;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.utils.RequestUtil;
import com.test.utils.SkipHttpsUtil;
import http.TestBase;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class download_linux extends testBase12 {
    OkHttpClient client;

    Request request1;
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
    public void list_template() throws IOException {

        request1 = RequestUtil.requestGet(url+"/add_assets/download_win?group_id=1",token);
        JSONObject result = TestBase.ResultHttp(request1);
//        Assert.assertTrue(result.get("data")!=null);
//        Assert.assertEquals(result.get("data"),"/licManage/exportLic");
    }

}
