package http.template;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.utils.RequestUtil;
import com.test.utils.ShellUtil;
import com.test.utils.SkipHttpsUtil;
import http.TestBase;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static com.test.utils.SftpFileUtil.inputFile_linux_withpath;

public class porcess extends testBase12{
    //登录url
    OkHttpClient client;
    MediaType mediaType;

    MediaType mediaType_text;
    Request request1;
    String templateId;
    String templateId_testallconfig;
    JSONObject mainConfig;
    JSONObject mainConfig_new;
    String templateId_create;
    String nodeId;
    String path1;
    String fileName;
    @BeforeMethod()
    public void beforeClass123() throws IOException {
        //调用teseBase中的Init方法，对url进行赋值，无需每次更新url
        init();
        TestBase.http_user_init_other();
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
        mediaType= MediaType.parse("application/json;charset=UTF-8");
        mediaType_text = MediaType.parse("text/plain");
        path1 = "danjituozhan";
        fileName = "neiwang.py";
    }
    //列举测试模板
    @Test(parameters ="",priority=1)
    public void list_template() throws IOException {
        templateId = "没有匹配值";
        request1 = RequestUtil.requestGet(url+"/template/list_template ",token);
        JSONObject result = TestBase.ResultHttp(request1);
        for(int i=0;i<result.getJSONObject("data").getJSONArray("list").size();i++){
            if(result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("name").equals("审计模板")){
                templateId = result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("id").toString();
            }
        }
        System.out.println(templateId);
    }
    //获取业务模板的配置

    @Test(parameters ="",priority=2)
    public void get_template_config() throws IOException {
        //request1 = RequestUtil.requestGet(url+"/template/get_template_config?templateId="+templateId,token);
        request1 = RequestUtil.requestGet(url+"/template/get_template_config?templateId="+templateId,token);
        JSONObject result = TestBase.ResultHttp(request1);
        mainConfig = result.getJSONObject("data").getJSONObject("mainConfig");
        System.out.println(mainConfig);
    }

    //以业务模板为模型创建一个新的策略
    @Test(parameters ="",dependsOnMethods = "get_template_config",priority=3)
    public void save() throws IOException {
        RequestBody body = RequestBody.create(mediaType,"{\"name\":\"5555\",\"note\":\"\",\"mainConfig\":"+mainConfig+"}");
        request1 = RequestUtil.requestPost1(url+"/template/save",body,token);
        JSONObject result = TestBase.ResultHttp(request1);
        templateId_create = result.get("data").toString();
    }
    //查看新建策略配置,与默认模板一致
    @Test(parameters ="",priority=4)
    public void get_template_config_new() throws IOException {
//        templateId_create = "e5fc7625-eda1-4f09-a6f9-3c6858b7e0c4";
        request1 = RequestUtil.requestGet(url+"/template/get_template_config?templateId="+templateId_create,token);
        JSONObject result = TestBase.ResultHttp(request1);
        mainConfig_new = result.getJSONObject("data").getJSONObject("mainConfig");
        //Assert.assertEquals(mainConfig,mainConfig_new);
    }

    //获取当前模板绑定终端列表,新建的模板绑定列表应该为空，长度为0
    @Test(parameters ="",priority=5)
    public void list_node_by_template() throws IOException {
//        templateId_create = "e5fc7625-eda1-4f09-a6f9-3c6858b7e0c4";
        request1 = RequestUtil.requestGet(url+"/template/list_node_by_template?templateId="+templateId_create+"&offset=0&limit=10000",token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertEquals(result.getJSONObject("data").get("total").toString(),"0");
    }

    //获取当前模板可绑定终端列表
    @Test(parameters ="",priority=6)
    public void list_all_node_by_template() throws IOException {
        nodeId = "123456";
//        templateId_create = "e5fc7625-eda1-4f09-a6f9-3c6858b7e0c4";
        request1 = RequestUtil.requestGet(url+"/template/list_all_node_by_template?key=&limit=20&offset=0&templateId="+templateId_create,token);
        JSONObject result = TestBase.ResultHttp(request1);
        for(int i=0;i<result.getJSONObject("data").getJSONArray("list").size();i++){
            result.getJSONObject("data").getJSONArray("list").getJSONObject(i);
            if(result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("new_name").equals(linux_name)){
                nodeId =result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("id").toString();
                break;
            }
        }
    }

    //将终端X绑定到新创建的模板上
    @Test(parameters ="",priority=7)
    public void bind() throws IOException {
         RequestBody  body = RequestBody.create(mediaType,"{\"templateId\":"+"\""+templateId_create+"\",\"nodeIds\":[\"989e4d6fb36aad6999075fb4a2701a60b257059dd033fb7bd9994ccb5b0a7950\"]}");
          request1 = RequestUtil.requestPost1(url+"/template/bind",body,token);
        JSONObject result = TestBase.ResultHttp(request1);
    }

    //打开渗透追踪-单机拓展
    @Test(parameters ="",priority=8)
    public void save_new_template_config() throws IOException {

    //这里的config如果直接写出来会报错字符串过长，后续可以考虑在配置类里面写这个然后读取配置类
        RequestBody  body = RequestBody.create(mediaType,"{\"id\":"+"\""+templateId_create+"\",\"name\":\"55556\",\"note\":\"\",\"mainConfig\":"+mainConfig+"}");
        request1 = RequestUtil.requestPost1(url+"/template/save",body,token);
        JSONObject result = TestBase.ResultHttp(request1);
    }
    //将单机拓展程序文件传送到终端上
    @Test(description = "连接linux，单机拓展程序文件",priority=9)
    public void linux_xdome() throws Exception {
        inputFile_linux_withpath(linux,linux_username,linux_password,path1,fileName);
        String result = ShellUtil.shellCommand(linux,linux_username,linux_password,"cd /usr/mxj1;ls");

    }

    //导入一个模板——由于模板json过长，使用导入功能可以方便支持配置多种自定义模板
    @Test(description = "导入模板",priority=10)
    public void import_model() throws Exception {
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file","bingduku/templates.zip",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("bingduku/templates.zip")))
                .build();
        request1 = RequestUtil.requestPost1(url+"/template/import",body,token);
        JSONObject result = TestBase.ResultHttp(request1);
    }
    //检查导入是否成功，列表新增一个名称为test_all_config的策略，获取Id
    @Test(parameters ="",priority=11)
    public void list_template_testallconfig() throws IOException {
        templateId_testallconfig = "没有匹配值";
        request1 = RequestUtil.requestGet(url+"/template/list_template ",token);
        JSONObject result = TestBase.ResultHttp(request1);
        for(int i=0;i<result.getJSONObject("data").getJSONArray("list").size();i++){
            if(result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("name").equals("test_all_config")){
                templateId_testallconfig = result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("id").toString();
            }
        }
        System.out.println(templateId_testallconfig);
    }

    //将终端X绑定到新创建的模板上_allconfig
    @Test(parameters ="",priority=12)
    public void bind_allconfig() throws IOException {
        RequestBody  body = RequestBody.create(mediaType,"{\"templateId\":"+"\""+templateId_testallconfig+"\",\"nodeIds\":[\"989e4d6fb36aad6999075fb4a2701a60b257059dd033fb7bd9994ccb5b0a7950\"]}");
        request1 = RequestUtil.requestPost1(url+"/template/bind",body,token);
        JSONObject result = TestBase.ResultHttp(request1);
    }

    //异常操作触发
    @Test(parameters ="",priority=13)
    public void test_config() throws Exception {
        ShellUtil.shellCommand(linux,linux_username,linux_password,"cd /usr;mkdir mxj2");
        ShellUtil.shellCommand(linux,linux_username,linux_password,"cd /usr/mxj1;touch mxj123.txt");
        ShellUtil.shellCommand(linux,linux_username,linux_password,"cd /usr/mxj2;touch mxj.txt");
        ShellUtil.shellCommand(linux,linux_username,linux_password,"cd /usr;mkdir mxj3;ls");
        ShellUtil.shellCommand(linux,linux_username,linux_password,"curl "+linux+"/?id=1 and 1= 1");
        ShellUtil.shellCommand(linux,linux_username,linux_password,"curl "+linux+"/?alert('test')");
        ShellUtil.shellCommand(linux,linux_username,linux_password,"curl "+linux+"/?redirectAction:test");
        ShellUtil.shellCommand(linux,linux_username,linux_password,"curl "+linux+"/1.html");
        ShellUtil.shellCommand(linux,linux_username,"error_password","ls");
        Runtime.getRuntime().exec("cmd /c start " + "http://"+linux+"/?id=1%20and%201=1");//SQL注入
        Runtime.getRuntime().exec("cmd /c start " + "http://"+linux+"/?alert('test')");//XSS攻击
        Runtime.getRuntime().exec("cmd /c start " + "http://"+linux+"/?php://input");//应用程序漏洞
        Runtime.getRuntime().exec("cmd /c start " + "http://"+linux+"/1.html");//自定义规则
        Runtime.getRuntime().exec("cmd /c start " + "http://"+linux+"/a.jpg/a.php");//文件解析防护
        Runtime.getRuntime().exec("cmd /c start " + "http://"+linux+"/com1");//畸形文件
        Runtime.getRuntime().exec("cmd /c start " + "http://"+linux+"/a.log");//敏感信息防泄漏
        Runtime.getRuntime().exec("cmd /k cd C:\\!AppDatasbak &&" +"echo hello >> text.txt" );//诱饵文件
        Runtime.getRuntime().exec("cmd /c cd C:\\mxj1 &&" +"echo hello >> text.txt" );//防篡改
        Runtime.getRuntime().exec("cmd /c xcopy D:\\test D:\\zhuomian\\muma\\wenjian1" );//触发渗透追踪——优化就是把文件上传到项目中
    }

    //查看是否产生日志

    //关闭渗透追踪-单机拓展

    //解除绑定终端

    //删除策略模板
    @Test(parameters ="",priority=14)
    public void delete() throws IOException {
//        templateId_create = "e5fc7625-eda1-4f09-a6f9-3c6858b7e0c4";
        request1 = RequestUtil.requestDELETE(url+"/template/del?templateIds[]="+templateId_create,token);
        JSONObject result = TestBase.ResultHttp(request1);

//        request1 = RequestUtil.requestDELETE(url+"/template/del?templateIds[]="+templateId_testallconfig,token);
//         result = TestBase.ResultHttp(request1);
    }
}
