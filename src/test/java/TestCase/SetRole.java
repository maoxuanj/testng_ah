package TestCase;


import com.test.utils.SkipHttpsUtil;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



/*
 * @auth:MAOXJ
 * @return: 测试数据
 * @parame:
 *
 */






public class SetRole {


    CloseableHttpResponse response=null;
    String entityStr = null;
    //登录url
    String url ="https://10.50.38.48/menu/setRole";

    @Test()
    public void skipSSL() throws Exception {
        SkipHttpsUtil.sendHttpPost(url,"aaab");
    }



    @Test(invocationCount = 1, threadPoolSize = 0)
    public void JdLoginTest() throws IOException {


        // 获取连接客户端工具
        CloseableHttpClient httpClient=HttpClients.createDefault();
        // 创建POST请求对象
        HttpPost httpPost=new HttpPost(url);
        System.setProperty("javax.net.ssl.trustStore", "C:/.keystore");
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

        // httpPost.addHeader post请求 header
        httpPost.addHeader("Content-Type","application/json;charset=UTF-8");
        //httpPost.addHeader("User-Agent:","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.122 Safari/537.36");
//        List<Header> headerList= Lists.newArrayList();
//        headerList.add(new BasicHeader(HttpHeaders.CONTENT_TYPE,"application/x-www-form-urlencoded; charset=UTF-8"));
//        headerList.add(new BasicHeader(HttpHeaders.USER_AGENT,"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.122 Safari/537.36"));
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }};

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
        }

        //参数封装对象
        List<NameValuePair> params=new ArrayList<NameValuePair>();


        try{
            params.add(new BasicNameValuePair("id"," "));
            params.add(new BasicNameValuePair("ids","0"));
            params.add(new BasicNameValuePair("name","TTTST"));
//            params.add(new BasicNameValuePair("loginType","c"));
//            params.add(new BasicNameValuePair("loginname","zhangsan"));
//            params.add(new BasicNameValuePair("nloginpwd","Y2nHBYQ/YvBZMVxQMbWxt+TTs+cW8rrvbW1CYlmX61xfoLx7tgSpbgxZ8+/HCgefeAXphJVmefDdN/3d3UQLnFiyl7GSkGjZQNU4pw+9+202NovcR6q9G/haGpNKp/5h+Xs+J7BrUfKXvkmdmKS0fIs7ly0K+OY/BHKcYg="));
//            params.add(new BasicNameValuePair("authcode","107fdb562e3240356c3ff97310e7"));
//            params.add(new BasicNameValuePair("pubKey","MIGfMA0GCSqGSIb3QUAA4GNADCBiQKBgQDC7kw8r6tq43pwApYvkJ5laljaN9BZb21TAIfT/vexbobzH7Q8SUdP5uDPXEBKzOjx2L28y7Xs1d9v3tdPfKI2LR7PAzWBmDMn8riHrDDNpUpJnlAGUqJG9ooPn8j7YNpcxCa1iybOlc2kEhmJn5uwoanQq+CA6agNkqly2H4j6wIDAQAB"));
//            params.add(new BasicNameValuePair("sa_token","B68C442BE6457547E701208059007998C0A52BF7A8EAD6AD256C8D009D929F000C9DF4192A57234E3EA6F615E156C81EFA53D580517BB9357FB9516A01E25761124AE9AF7B3CFA3C38D38484A734CB58C286401C2DEC2A5DFF3C9E856280AF80D4851C9B0239587771E8DC06B46454644D4908F4DC165CB70D86EAC7276BFBE489FFE2324EDDC5F71043BFB99B3D6E238B1AE9E67C3F297E0993B8497B1287640777CF4FFBA52FF032510AD19D7F371541C798742CB4378E5DD2119BADE9078310468AF8436A2B88593A92EEAF16FCFD55CD7F121B58D7A9A833D74068FACC5A6D6D8C3D1A850245F0742DEEC12BACF0FF9D5853FFDF1B37AC6A5E676DC635896AFD884D0BBB8A490E57234DF65A76AF189908F4AB80AEA36E56F6DD110EF7D36D119BB77F0B65774780348FFE859A68D2E0B3A3CDDAFD1BEFCD401530D536C8EF68B618969FC2FFD658FE0BA7BC2E7250F9CCDBB8F9AF360FC293F294A7279EA70043E860784C2E2CF11181C44561794A32AADDB2AC37B1294C08E7B63C85E6561F138195ECCF28EA0F08FB5A16DB7A20814DD914FC0C8A12BF29FFC4F73DD39361EBA1A849BB25B9F5957589347E205573754EA468D809CCCA698BFAB16373516DC8F5FEE8A24C2306850D601D6827C161F1A83057E0F93A97A0C034E"));
//            params.add(new BasicNameValuePair("seqSid","312437999206350"));
//            params.add(new BasicNameValuePair("useSlideAuthCode","1"));
//            params.add(new BasicNameValuePair("_t","_t"));








            // 使用URL实体转换工具
            UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(params, "UTF-8");


            httpPost.setEntity(entityParam);
            // 执行请求
            response=httpClient.execute(httpPost);
            // 获得响应的实体对象
            HttpEntity entity=response.getEntity();
            // 使用Apache提供的工具类进行转换成字符串
            int code =response.getStatusLine().getStatusCode();
            System.out.println("StatusCode: " + code);
            //断言
            Assert.assertEquals(200,code);
            entityStr=EntityUtils.toString(entity,"UTF-8");


            System.out.println("接口返回结果是:="+entityStr);


        }catch(Exception e){
            e.printStackTrace();
        }finally {


            //释放资源
            if(httpClient!=null) {
                httpClient.close();
            }
            if (response!=null){
                response.close();
            }
        }




    }


}



