package com.test.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RestfulClientPost {
    CloseableHttpClient httpclientpost;
    HttpPost httpPost;
    CloseableHttpResponse httpResponse;
    int responseCode;
    JSONObject responseBody;
    HashMap<String, String> responseHeads;

    //通过httpclient获取请求的反馈

    //通过httpclient获取post请求的反馈
    public void sendPost(String url, List<NameValuePair> params, HashMap<String, String> headers) throws ClientProtocolException, IOException{
        httpclientpost = HttpClients.createDefault();

        //创建post请求对象
        httpPost = new HttpPost(url);
        httpResponse = httpclientpost.execute(httpPost);
        //设置请求主体格式
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        //设置头部信息
        Set<String> set = headers.keySet();
        for(Iterator<String> iterator = set.iterator(); iterator.hasNext();){
            String key = iterator.next();
            String value = headers.get(key);
            httpPost.addHeader(key, value);
        }
        httpResponse = httpclientpost.execute(httpPost);
    }


    //以JSON格式获取到反馈的主体
    public JSONObject getBodyInJSON() throws ParseException, IOException{
        HttpEntity entity;
        String entityToString;
        entity = httpResponse.getEntity();
        entityToString = EntityUtils.toString(entity);
        responseBody = JSON.parseObject(entityToString);

        System.out.println("This is your response body" + responseBody);

        return responseBody;
    }

    //以哈希图的方式获取到反馈头部
    public HashMap<String, String> getHeaderInHash(){
        Header[] headers;
        headers = httpResponse.getAllHeaders();

        responseHeads = new HashMap<String, String>();

        for(Header header:headers){
            responseHeads.put(header.getName(), header.getValue());
        }

        System.out.println("This is your response header" + responseHeads);

        return    responseHeads;
    }

    //获取反馈状态码
    public int getCodeInNumber(){
        responseCode = httpResponse.getStatusLine().getStatusCode();

        System.out.println("This is your response code" + responseCode);

        return responseCode;
    }

//
//    /**
//     * 跳过证书效验的sslcontext
//     * @return
//     * @throws Exception
//     */
//    private static SSLContext createIgnoreVerifySSL() throws Exception {
//        SSLContext sc = SSLContext.getInstance("TLS");
//
//        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
//        X509TrustManager trustManager = new X509TrustManager() {
//            @Override
//            public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
//                                           String paramString) throws CertificateException {
//            }
//            @Override
//            public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
//                                           String paramString) throws CertificateException {
//            }
//
//            @Override
//            public boolean isClientTrusted(X509Certificate[] x509Certificates) {
//                return false;
//            }
//
//            @Override
//            public boolean isServerTrusted(X509Certificate[] x509Certificates) {
//                return false;
//            }
//
//            @Override
//            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                return null;
//            }
//        };
//        sc.init(null, new TrustManager[] { trustManager }, null);
//        return sc;
//    }
//
//    /**
//     * 构造RestTemplate
//     * @return
//     * @throws Exception
//     */
//    private static RestTemplate getRestTemplate() throws Exception {
//        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//        //超时
//        factory.setConnectionRequestTimeout(5000);
//        factory.setConnectTimeout(5000);
//        factory.setReadTimeout(5000);
//        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
//                createIgnoreVerifySSL(),
//                // 指定TLS版本
//                null,
//                // 指定算法
//                null,
//                // 取消域名验证
//                new HostnameVerifier(){
//
//
//
//                    @Override
//                    public boolean verify(String string, SSLSession ssls) {
//                        return true;
//                    }
//
//                    @Override
//                    public boolean verify(String s, String s1) {
//                        return false;
//                    }
//                }
//        );
//        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
//                .build();
//        factory.setHttpClient(httpClient);
//        RestTemplate restTemplate = new RestTemplate(factory);
//        // 解决中文乱码问题
//        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
//        return restTemplate;
//    }

}