package com.test.api;

import com.alibaba.fastjson.JSONObject;
import com.test.client.RestfulClientPost;
import com.test.utils.JSONParser;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class testPost {
    RestfulClientPost client;
    JSONObject responseBody;
    JSONParser jParser;
    int responseCode;
    String city;
    String url = "https://api.apishop.net/communication/phone/getLocationByPhoneNum";
    String postBody;
    @Ignore
    @Test
    public void testPostRequest() {
        //断言反馈中城市信息是否正确
        Assert.assertEquals(city, "北京");
        //断言反馈的状态码是否正确
        Assert.assertEquals(responseCode, 200);
    }
    @BeforeClass
    public void beforeClass() throws ClientProtocolException, IOException {
        client = new RestfulClientPost();
        //用NameValuePair的list来添加请求主体参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("apiKey", "nMke6NK29c40b1d******b3eec8aa0808389b16c4"));
        params.add(new BasicNameValuePair("phoneNum", "1861196136"));
       //用哈希图准备请求头部信息
        HashMap<String, String> hashHead = new HashMap<String, String>();
        hashHead.put("Content-Type", "application/x-www-form-urlencoded");
       //传参发送post请求并接收反馈
        client.sendPost(url, params, hashHead);

        responseBody = client.getBodyInJSON();
        responseCode = client.getCodeInNumber();

        System.out.println(responseBody);
        jParser = new JSONParser();
        city = jParser.getCity(responseBody);
    }

}