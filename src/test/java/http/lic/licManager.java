package http.lic;

import MyTest.testBase12;
import com.test.utils.RequestUtil;
import com.test.utils.SkipHttpsUtil;
import http.TestBase;
import okhttp3.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class licManager extends testBase12 {
    CloseableHttpResponse response=null;
    String entityStr = null;
    //登录url
    OkHttpClient client;
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

    @Test(parameters ="")
    public void get_lic_model1() throws IOException {
        request1 =RequestUtil.requestGet(url+"/licManage/get_lic_model",token);
        Response response = client.newCall(request1).execute();
        System.out.println(response.body().string());

    }


    @Test(parameters ="",description = "查看产品许可证详情")
    public void get_product_lic() throws IOException {
        request1 =RequestUtil.requestGet(url+"/licManage/get_product_lic",token);
        Response response = client.newCall(request1).execute();
        System.out.println(response.body().string());

    }



    @Test(parameters ="",description = "导出许可")
    public void exportLic() throws IOException {
        request1 =RequestUtil.requestGet(url+"/licManage/exportLic",token);
        Response response = client.newCall(request1).execute();
        System.out.println(response.body().string());

    }


    @Test(parameters ="",description = "判断是否已导入许可")
    public void import_license_first() throws IOException {
        request1 =RequestUtil.requestGet_NoToken(url+"/licManage/import_license_first");
        Response response = client.newCall(request1).execute();
        System.out.println(response.body().string());


//        Request request = new Request.Builder()
//                .url(url+"/licManage/import_license_first")
//                .method("GET", null)
//                .addHeader("Accept", "application/json, text/plain, */*")
//                .addHeader("Accept-Language", "")
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Cookie", "token=; JSESSIONID=2EA297F115442A937B0543395A874C20")
//                .build();
//        Response response = client.newCall(request).execute();
//        System.out.println(response.body().string());

    }



    @Test(parameters ="",description = "获取许可模型情况")
    public void get_lic_model() throws IOException {
        request1 =RequestUtil.requestGet(url+"/licManage/get_lic_model",token);
        Response response = client.newCall(request1).execute();
        System.out.println(response.body().string());

    }







}
