package http.template;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.utils.*;
import http.TestBase;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.test.utils.SftpFileUtil.inputFile_linux_withpath;

public class template_porcess extends testBase12{
    //登录url
    OkHttpClient client;
    MediaType mediaType;

    MediaType mediaType_text;
    Request request1;
    String templateId;
    String templateId_testallconfig;

    String templateId_test_ttt;

    String templateId_test_test;

    String templateId_test_blackprocess;
    JSONObject mainConfig;
    JSONObject mainConfig_new;
    String templateId_create;
    String nodeId;
    String path1;
    String fileName;
    List nodeIds;
    String templateId_sys;
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
        Assert.assertTrue(AssertUtil.ifsuccess(result));
        for(int i=0;i<result.getJSONObject("data").getJSONArray("list").size();i++){
            if(result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("name").equals("审计模板")){
                templateId = result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("id").toString();
            }
        }
        templateId_sys = templateId;
        System.out.println(templateId);
    }
    //获取业务模板的配置

    @Test(parameters ="",priority=2)
    public void get_template_config() throws IOException {
        //request1 = RequestUtil.requestGet(url+"/template/get_template_config?templateId="+templateId,token);
        request1 = RequestUtil.requestGet(url+"/template/get_template_config?templateId="+templateId,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
        mainConfig = result.getJSONObject("data").getJSONObject("mainConfig");
        System.out.println(mainConfig);
    }

    //以业务模板为模型创建一个新的策略
    @Test(parameters ="",priority=3)
    public void save() throws IOException {
        RequestBody body = RequestBody.create(mediaType,"{\"name\":\"6666\",\"note\":\"\",\"mainConfig\":"+mainConfig+"}");
        request1 = RequestUtil.requestPost1(url+"/template/save",body,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
        templateId_create = result.get("data").toString();
    }
    //查看新建策略配置,与默认模板一致
    @Test(parameters ="",priority=4)
    public void get_template_config_new() throws IOException {
//        templateId_create = "e5fc7625-eda1-4f09-a6f9-3c6858b7e0c4";
        request1 = RequestUtil.requestGet(url+"/template/get_template_config?templateId="+templateId_create,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
        mainConfig_new = result.getJSONObject("data").getJSONObject("mainConfig");
        //Assert.assertEquals(mainConfig,mainConfig_new);
    }

    //获取当前模板绑定终端列表,新建的模板绑定列表应该为空，长度为0
    @Test(parameters ="",priority=5)
    public void list_node_by_template() throws IOException {
//        templateId_create = "e5fc7625-eda1-4f09-a6f9-3c6858b7e0c4";
        request1 = RequestUtil.requestGet(url+"/template/list_node_by_template?templateId="+templateId_create+"&offset=0&limit=10000",token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
        Assert.assertEquals(result.getJSONObject("data").get("total").toString(),"0");
    }

    //获取当前模板可绑定终端列表
    @Test(parameters ="",priority=6)
    public void list_all_node_by_template() throws IOException {
        nodeIds =new ArrayList<>();
        request1 = RequestUtil.requestGet(url+"/template/list_all_node_by_template?key=&limit=20&offset=0&templateId="+templateId_create,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
        for(int i=0;i<result.getJSONObject("data").getJSONArray("list").size();i++){
            result.getJSONObject("data").getJSONArray("list").getJSONObject(i);
            nodeIds.add("\""+result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("id")+"\"");
            if(result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("intranet_ip").equals(linux)){
                nodeId =result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("id").toString();
                //注释：防止绑定终端时少绑定
                //                break;
            }
        }
    }

    //将终端X绑定到新创建的模板上
    @Test(parameters ="",priority=7)
    public void bind() throws IOException {
         RequestBody  body = RequestBody.create(mediaType,"{\"templateId\":"+"\""+templateId_create+"\",\"nodeIds\":[\""+nodeId+"\"]}");
          request1 = RequestUtil.requestPost1(url+"/template/bind",body,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    //打开渗透追踪-单机拓展
    @Test(parameters ="",priority=8)
    public void save_new_template_config() throws IOException {

    //这里的config如果直接写出来会报错字符串过长，后续可以考虑在配置类里面写这个然后读取配置类
        RequestBody  body = RequestBody.create(mediaType,"{\"id\":"+"\""+templateId_create+"\",\"name\":\"55556\",\"note\":\"\",\"mainConfig\":"+mainConfig+"}");
        request1 = RequestUtil.requestPost1(url+"/template/save",body,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }
    //将单机拓展程序文件传送到终端上   linux单机拓展不生效，该用例可不执行
   // @Test(description = "连接linux，单机拓展程序文件",priority=9)
    public void linux_xdome() throws Exception {
        inputFile_linux_withpath(linux,linux_username,linux_password,path1,fileName);
        String result = ShellUtil.shellCommand(linux,linux_username,linux_password,"cd /usr;ls");

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
        Assert.assertTrue(AssertUtil.ifsuccess(result));
        body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file","bingduku/templates3.zip",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("bingduku/templates3.zip")))
                .build();
        request1 = RequestUtil.requestPost1(url+"/template/import",body,token);
        JSONObject result2 = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result2));

        body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file","bingduku/templates4.zip",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("bingduku/templates4.zip")))
                .build();
        request1 = RequestUtil.requestPost1(url+"/template/import",body,token);
        JSONObject result3 = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result2));

        body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file","bingduku/templates5.zip",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("bingduku/templates5.zip")))
                .build();
        request1 = RequestUtil.requestPost1(url+"/template/import",body,token);
        JSONObject result4 = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result2));

    }
    //检查导入是否成功，列表新增一个名称为test_all_config的策略，获取Id
    @Test(parameters ="",priority=11)
    public void list_template_testallconfig() throws IOException {
        templateId_testallconfig = "没有匹配值";
        templateId_test_ttt = "没有匹配值";
        templateId_test_test = "没有匹配值";
        templateId_test_blackprocess = "没有匹配值";
        request1 = RequestUtil.requestGet(url+"/template/list_template ",token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
        for(int i=0;i<result.getJSONObject("data").getJSONArray("list").size();i++){
            if(result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("name").equals("test_all_config")){
                templateId_testallconfig = result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("id").toString();
            }
        }
        for(int i=0;i<result.getJSONObject("data").getJSONArray("list").size();i++){
            if(result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("name").equals("ttt")){
                templateId_test_ttt = GetidUtil.getId_new("ttt","name",result);
            }
        }
        for(int i=0;i<result.getJSONObject("data").getJSONArray("list").size();i++){
            if(result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("name").equals("test_test")){
                templateId_test_test = GetidUtil.getId_new("test_test","name",result);
            }
        }
        for(int i=0;i<result.getJSONObject("data").getJSONArray("list").size();i++){
            if(result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("name").equals("test_blackprocess")){
                templateId_test_blackprocess = GetidUtil.getId_new("test_blackprocess","name",result);
            }
        }
        System.out.println(templateId_testallconfig);
    }

    //将终端X绑定到新创建的模板上_allconfig
    @Test(parameters ="",priority=12)
    public void bind_allconfig() throws IOException {
        System.out.println("{\"templateId\":"+"\""+templateId_test_ttt+"\",\"nodeIds\":"+nodeIds+"}");
        RequestBody  body = RequestBody.create(mediaType,"{\"templateId\":"+"\""+templateId_testallconfig+"\",\"nodeIds\":"+nodeIds+"}");
        request1 = RequestUtil.requestPost1(url+"/template/bind",body,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }

    //异常操作触发
    @Test(parameters ="",priority=13)
    public void test_config() throws Exception {

        ShellUtil.shellCommand(linux,linux_username,linux_password,"cd /usr;mkdir mxj1;mkdir mxj2");
        //文件访问监控触发
        ShellUtil.shellCommand(linux,linux_username,linux_password,"cd /usr/mxj1;touch mxj123.txt");
        //数据销毁
        ShellUtil.shellCommand(linux,linux_username,linux_password,"cd /usr/mxj1;rm -rf mxj123.txt");
        //网页防篡改
        ShellUtil.shellCommand(linux,linux_username,linux_password,"cd /usr/mxj2;touch mxj.txt");
        ShellUtil.shellCommand(linux,linux_username,linux_password,"cd /usr;mkdir mxj4;ls");
        //事件响应触发
        ShellUtil.shellCommand(linux,linux_username,linux_password,"cd /usr/mxj4;touch mxj.txt");
        //数据销毁
        ShellUtil.shellCommand(linux,linux_username,linux_password,"cd /usr/mxj4;rm -rf mxj.txt");
        //入侵检测
        ShellUtil.shellCommand(linux,linux_username,linux_password,"chmod u+x sshd");
        //异常登陆审计触发
        ShellUtil.shellCommand(linux,linux_username,"error_password","ls");
        ShellUtil.shellCommand(linux,linux_username,"123456","ls");
        ShellUtil.shellCommand(linux,linux_username,"654321","ls");
        ShellUtil.shellCommand(linux,linux_username,"88856","ls");
        ShellUtil.shellCommand(linux,linux_username,"987655","ls");
        ShellUtil.shellCommand(linux,linux_username,"9636521","ls");
        ShellUtil.shellCommand(linux,linux_username,"88888","ls");
        ShellUtil.shellCommand(linux,linux_username,"error_password","ls");
        //数据销毁
        ShellUtil.shellCommand(linux,linux_username,linux_password,"cd /usr/mxj2;rm -rf mxj.txt");

//端口扫描 先注释，发现端口扫描完后终端离线了
//        String host = linux;
//        InetAddress inetAddress = InetAddress.getByName(host);
//        String hostName = inetAddress.getHostName();
//        for (int port = 0; port <= 5; port++) {
//            try {
//                Socket socket = new Socket(hostName, port);
//                String text = hostName + " is listening on port " + port;
//                System.out.println(text);
//                socket.close();
//            } catch (IOException e) {
//                // 空挡块
//            }
//        }
//        System.out.println("这里是因为要做网站漏洞防护的，对应的终端需要安装iis服务,后续优化");
        Runtime.getRuntime().exec("cmd /c start " + "http://"+windows+"/?id=1%20and%201=1");//SQL注入
        Runtime.getRuntime().exec("cmd /c start " + "http://"+windows+"/?alert('test')");//XSS攻击
        Runtime.getRuntime().exec("cmd /c start " + "http://"+windows+"/?php://input");//应用程序漏洞
        Runtime.getRuntime().exec("cmd /c start " + "http://"+windows+"/1.html");//自定义规则
        Runtime.getRuntime().exec("cmd /c start " + "http://"+windows+"/a.jpg/a.php");//文件解析防护
        Runtime.getRuntime().exec("cmd /c start " + "http://"+windows+"/com1");//畸形文件
        Runtime.getRuntime().exec("cmd /c start " + "http://"+windows+"/a.log");//敏感信息防泄漏
        Runtime.getRuntime().exec("cmd /c start " + "http://"+windows+"/");//敏感信息防泄漏
        Runtime.getRuntime().exec("cmd /k cd C:\\!AppDatasbak &&" +"echo hello >> text.txt" );//诱饵文件
        Runtime.getRuntime().exec("cmd /c cd C:\\Program Files (x86)\\Java &&" +"echo hello >> text.txt" );//防篡改
        Runtime.getRuntime().exec("cmd /c cd C:\\Program Files (x86)\\Java &&" +"del text.txt" );//防篡改
        Runtime.getRuntime().exec("cmd /c xcopy D:\\test D:\\zhuomian\\muma\\wenjian1" );//触发渗透追踪——优化就是把文件上传到项目中
        Runtime.getRuntime().exec("cmd /c start " + "http://"+windows+"/?id=1%20and%201=1");//SQL注入
        Runtime.getRuntime().exec("cmd /c start " + "http://"+windows+"/?php://input");//应用程序漏洞
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
        Assert.assertTrue(AssertUtil.ifsuccess(result));
//        request1 = RequestUtil.requestDELETE(url+"/template/del?templateIds[]="+templateId_testallconfig,token);
//         result = TestBase.ResultHttp(request1);
    }



    @Test(parameters ="",description = "绑定终端到另一套策略中，触发其余操作",priority=15)
    public void import_model_new() throws Exception {


        RequestBody  body = RequestBody.create(mediaType,"{\"templateId\":"+"\""+templateId_test_test+"\",\"nodeIds\":"+nodeIds+"}");
        request1 = RequestUtil.requestPost1(url+"/template/bind",body,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }


    @Test(parameters ="",description = "",priority=16)
    public void test_config_web() throws Exception {
        ShellUtil.shellCommand(linux,linux_username,linux_password,"curl "+ windows+"/"); //网站黑名单+进程白名单触发
        Runtime.getRuntime().exec("cmd /c start " + "http://"+windows+"/1.html");//自定义规则
        //文件访问监控触发
        ShellUtil.shellCommand(linux,linux_username,linux_password,"cd /usr/mxj1;touch mxj123.txt");
        //数据销毁
        ShellUtil.shellCommand(linux,linux_username,linux_password,"cd /usr/mxj1;rm -rf mxj123.txt");
        Runtime.getRuntime().exec("cmd /c cd C:\\Program Files (x86)\\DBAppSecurity &&" +"echo hello >> text.txt" );//防篡改
        Runtime.getRuntime().exec("cmd /c cd C:\\Program Files (x86)\\DBAppSecurity &&" +"del text.txt" );//防篡改
    }

    @Test(parameters ="",description = "绑定终端到test_blackprocess中，触发网站访问控制和进程黑名单操作",priority=17)
    public void import_model_blackprocess() throws Exception {
        RequestBody  body = RequestBody.create(mediaType,"{\"templateId\":"+"\""+templateId_test_blackprocess+"\",\"nodeIds\":"+nodeIds+"}");
        request1 = RequestUtil.requestPost1(url+"/template/bind",body,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }


    @Test(parameters ="",description = "",priority=18)
    public void test_config_blackprocess() throws Exception {
        Runtime.getRuntime().exec("cmd /c start " +"http://"+ windows+"/");//网站访问控制
        ShellUtil.shellCommand(linux,linux_username,linux_password,"curl "+ windows+"/"); //进程黑名单触发
        //进程黑名单触发
        ShellUtil.shellCommand(linux,linux_username,linux_password,"cd /usr/mxj4;touch mxj123.txt");
        //数据销毁
        ShellUtil.shellCommand(linux,linux_username,linux_password,"cd /usr/mxj4;rm -rf mxj123.txt");
    }


    @Test(parameters ="",description = "绑定终端到ttt",priority=20)
    public void import_model_ttt() throws Exception {


        RequestBody  body = RequestBody.create(mediaType,"{\"templateId\":"+"\""+templateId_test_ttt+"\",\"nodeIds\":"+nodeIds+"}");
        request1 = RequestUtil.requestPost1(url+"/template/bind",body,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }



    //@Test(parameters ="",description = "绑定到默认模板中，减少一堆日志情况",priority=30)
    public void band_sys() throws Exception {


        RequestBody  body = RequestBody.create(mediaType,"{\"templateId\":"+"\""+templateId_sys+"\",\"nodeIds\":"+nodeIds+"}");
        request1 = RequestUtil.requestPost1(url+"/template/bind",body,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));
    }


    @Test(description = "导入一个全部都是关闭的模板",priority=22)
    public void import_model_close() throws Exception {
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file","bingduku/close.zip",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("bingduku/close.zip")))
                .build();
        request1 = RequestUtil.requestPost1(url+"/template/import",body,token);
        JSONObject result = TestBase.ResultHttp(request1);
        Assert.assertTrue(AssertUtil.ifsuccess(result));

    }


}
