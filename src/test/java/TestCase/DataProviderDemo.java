package TestCase;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.utils.SkipHttpsUtil;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class DataProviderDemo extends testBase12 {



    @BeforeClass
    public void before() throws IOException {
        //testBase中的Init,主要是为了赋值
        init();
    }

    @DataProvider(name = "testlogin")
    public Object[][] testlogin() {
        return new Object[][]{
                {1,"用户名为空","", "12345678",401},
                {2,"密码为空","dandan", "",400},
                {3,"正确的用户名密码","admin", "SjAHJH2XrbBgqgiJcxY/dQ==",200}
        };
    }


    /*
    这个demo主要是为了演示参数构造器的使用
     */


        //dataProvider名称需要与@DataProvider的name保持一致
        //使用parameters的意义在于，一些公共参数比如url，直接可以从testBase中获取到
        @Test(dataProvider = "testlogin",parameters ="")
        public void testlogin(int caseNum,String caseName ,String username, String password , int code) throws IOException {
            // .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
            //                    .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())  这三个的意义在于跳过ssl的认证
            //                    使用okHttpCline可以直接使用postman接口转换的代码内容
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                    .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                    .build();
            FormBody formBody = new FormBody.Builder().add("username",username).add("password",password)
                    .build();
            //这里的url用的是testBase中的url，直接读取配置文件
            Request request = new Request.Builder()
                    .url(url+"/login")
                    .post(formBody)
                    //todo 这里的times和加密方式后续需要优化的，动态生成，密钥使用
                    .addHeader("Times", "1657172548579")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            Response response = client.newCall(request).execute();
            String res = response.body().string();
            System.out.println(caseNum+":"+caseName+""+res);
            //断言判断code是否符合期望
            Assert.assertEquals(JSONObject.parseObject(res).get("error_code"),code);
        }


}
