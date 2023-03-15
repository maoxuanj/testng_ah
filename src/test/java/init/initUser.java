package init;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.utils.RequestUtil;
import com.test.utils.SkipHttpsUtil;
import http.TestBase;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class initUser extends testBase12 {
    static OkHttpClient client;
    //第一次进入——默认admin账号登陆，密码已更新为Admin1234
    Request request1;
    String userName;
    MediaType mediaType;
    List userLicenses;
    @BeforeMethod()
    public void beforeClass123() throws IOException {
        init();
        TestBase.admin_login_other();
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
        mediaType= MediaType.parse("application/json;charset=UTF-8");
        userLicenses=new ArrayList<>();
        userLicenses.add("{\"limit\":10,\"bindModel\":null,\"plus\":0,\"plusType\":0,\"id\":\"1\",\"name\":\"EDR-MODULE-PC\",\"endTime\":\"2023-09-30\"}");
        userLicenses.add("{\"limit\":10,\"bindModel\":null,\"plus\":0,\"plusType\":0,\"id\":\"13\",\"name\":\"WPT-EE-ALL\",\"endTime\":\"2023-09-30\"}");
        userLicenses.add("{\"limit\":10,\"bindModel\":null,\"plus\":0,\"plusType\":0,\"id\":\"2\",\"name\":\"EDR-MODULE-SERVER\",\"endTime\":\"2023-09-30\"}");
        userLicenses.add("{\"limit\":10,\"bindModel\":null,\"plus\":0,\"plusType\":0,\"id\":\"24\",\"name\":\"DAS-CWPP-AG-Workstation\",\"endTime\":\"2023-09-30\"}");
        userLicenses.add("{\"limit\":10,\"bindModel\":null,\"plus\":0,\"plusType\":0,\"id\":\"25\",\"name\":\"DAS-CWPP-AG-Workload\",\"endTime\":\"2023-09-30\"}");
        userLicenses.add("{\"limit\":10,\"bindModel\":null,\"plus\":1,\"plusType\":0,\"id\":\"3\",\"name\":\"EDR-MODULE-SERVER-WEB\",\"endTime\":\"2023-09-30\"}");
    }

    @Test(parameters ="",description = "第一次登陆用户初始化创建")
    public void list_template() throws IOException {

        request1 = RequestUtil.requestGet(url+"/user/list_user?limit=20&offset=0&order=&sort=",token_admin);
        JSONObject result = TestBase.ResultHttp(request1);
        //查看用户列表，是否存在user_name租户
        if(result.getJSONObject("data").getJSONArray("list").size()>0){
            //理论上肯定＞0，有一个admin账户的
            for(int i=0;i<result.getJSONObject("data").getJSONArray("list").size();i++){
                userName = result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("realName").toString();
                System.out.println(userName);
                if(userName.equals(user_name)){
                    System.out.println("有"+user_name+"租户啦");
                    break;
                }else{
                    System.out.println("这个租户不是"+user_name);
                }
            }
        }
        //不存在时，创建user_name租户，密码为Admin1234
        if(!userName.equals(user_name)){
            System.out.println("需要创建"+user_name);
            RequestBody body = RequestBody.create(mediaType,"{\"userName\":"+"\""+user_name+"\",\"password\":\"er5DhVpsBHEQRHHzjveixA==\",\"beUsed\":1,\"changePwd\":1,\"loginTimes\":1000,\"failCount\":5,\"neverTimeout\":1440,\"isLock\":15,\"licNum\":0,\"tel\":\"\",\"roleId\":\"1\",\"lessUser\":\"\",\"roleName\":\"租户管理员\",\"userLicenses\":"+userLicenses+",\"role\":2,\"userid\":\"\"}");
            request1 = RequestUtil.requestPost2(url+"/user/save",body,token_admin);
            JSONObject result_add = TestBase.ResultHttp(request1);
        }
        TestBase.http_user_init_other();
        System.out.println("登陆成功");
    }




}
