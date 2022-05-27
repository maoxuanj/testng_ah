package com.test.api;

import com.alibaba.fastjson.JSONObject;
import com.test.client.RestfulClientGet;
import com.test.utils.JSONParser;
import org.apache.http.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class testGet {
    RestfulClientGet client;
    JSONObject responseBody;
    JSONParser jParser;
    int responseCode;
    String city;
    String url = "https://api.apishop.net/communication/phone/getLocationByPhoneNum?apiKey=nMke6NK29c40b1d1331690abb50b3eec8aa0808389b16c4&phoneNum=1861195236";
    @Test
    public void TestGetRequest() {

        //断言反馈中城市是否正确
        Assert.assertEquals(city, "北京");
        //断言反馈中的状态码是否正确
        Assert.assertEquals(responseCode, 200);
    }
    @BeforeClass
    public void beforeClass() throws ParseException, IOException {
        //发送请求，获取反馈
        client = new RestfulClientGet();
        client.getResponse(url);
        responseBody = client.getBodyInJSON();
        responseCode = client.getCodeInNumber();
        //调用JSONParser获取反馈中的城市信息
        jParser = new JSONParser();
        city = jParser.getCity(responseBody);
    }

}