package http.virus;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.utils.RequestUtil;
import com.test.utils.ShellUtil;
import com.test.utils.SkipHttpsUtil;
import http.TestBase;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.test.utils.SftpFileUtil.inputFile_linux;

public class process extends testBase12 {
    OkHttpClient client;
    MediaType mediaType;
    Request request1;
    String asjson;
    String name;
    String node_id;
    @BeforeClass()
    public void beforeClass123() throws IOException {
        //调用teseBase中的Init方法，对url进行赋值，无需每次更新url
        init();
        TestBase.http_user_init();
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
        mediaType= MediaType.parse("application/json;charset=UTF-8");
    }
    //获取自定义查杀路径
    @Test(parameters ="",priority=1)
    public void history_path() throws IOException {


        request1 = RequestUtil.requestGet(url+"/virus_killing/antiav_kill/history_path",token);
        JSONObject result = TestBase.ResultHttp(request1);
        //如果是初次登陆，没有自定义查杀路径则新增自定义查杀路径，默认为/usr/mxj1
        if (result.getJSONArray("data").size()>0){
            asjson = result.getJSONArray("data").get(0).toString();
        }
        //如果是初次登陆，没有自定义查杀路径则新增自定义查杀路径，默认为/usr/mxj1
        else{
            asjson = "/usr/mxj1/";
        }

         System.out.println(asjson);
    }


    //病毒查杀流程
    @Test(parameters ="",dependsOnMethods = "history_path",priority=2)
    public void start_custom_scan() throws IOException, InterruptedException {

       // RequestBody body = RequestBody.create(mediaType, "{\"ids\":[\"0c87bfc92e342172430acfa8763b266cf7f2605cc53b8bf6ee17e191ded4e9ed\"],\"paths\":[{\"id\":\"d0b144f6-bd21-41f3-947c-d83ee3b79908\",\"check\":0,\"path\":\"C:\\\\Users\\\\Administrator\\\\Desktop\\\\virus.zip\",\"idx\":0},{\"path\":\"/usr/mxj1/\",\"check\":1,\"idx\":1}]}");
        RequestBody body = RequestBody.create(mediaType,"{\"ids\":[\"989e4d6fb36aad6999075fb4a2701a60b257059dd033fb7bd9994ccb5b0a7950\"],\"paths\":["+asjson+"]}");

        request1 = RequestUtil.requestPut(url+"/virus_killing/antiav_kill/start_custom_scan",token,body);
        JSONObject result = TestBase.ResultHttp(request1);
        //执行完成后等待一段时间，等待扫描状态更新。比较好的写法是不断去调用查询接口，后续优化
        Thread.sleep(3000);

    }
    //对应终端执行病毒查杀（自定义路径，较快出结果）第一次应该没有病毒记录
    //获取列表状态，查看本次病毒查杀是否完成
    @Test(parameters ="",priority=3)
    public void list_node() throws IOException, InterruptedException {
        request1 = RequestUtil.requestGet(url+"/virus_killing/antiav_kill/list_node?limit=20&offset=0&order=&sort=",token);
        JSONObject result = TestBase.ResultHttp(request1);
//        System.out.println(JSONchange(result.get("data").toString(),"list"));
        for (int i=0;i<result.getJSONObject("data").getJSONArray("list").size();i++){
            name = result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("newName").toString();
            //System.out.println(name);
            if(name.equals(linux_name)){
                node_id = result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("id").toString();
                System.out.println(node_id);
                for (int num = 0;num < 50 ;num++){
                    if(result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("scanStatus").toString().equals("扫描完成")){
                        break;
                    }else {
                        num++;
                    }

                }
            }else{
                Thread.sleep(3000);
                i ++;
            }
        }
    }

    //查看病毒查杀结果
    @Test(parameters ="",dependsOnMethods = "list_node",priority=4)
    public void read_page_scan_file() throws IOException {


        request1 = RequestUtil.requestGet(url+"/virus_killing/antiav/read_page_scan_file?limit=20&offset=0&node_id="+node_id,token);
        JSONObject result = TestBase.ResultHttp(request1);
    }

    //首先连接一台终端
    @Test(description = "连接linux，执行命令行",priority=5)
    public void testlinux(){
        String result = ShellUtil.shellCommand(linux,linux_username,linux_password,"cd /usr;ls");

    }

    //向终端的特定文件夹下xdome传送病毒文件
    //此处使用了sftp传送文件，然后ls验证是否传送成功
    @Test(description = "连接linux，上传病毒",priority=6)
    public void linux_xdome() throws Exception {
        inputFile_linux(linux,linux_username,linux_password);
        String result = ShellUtil.shellCommand(linux,linux_username,linux_password,"cd /usr;ls");

    }

    //对应终端执行病毒查杀（自定义路径，较快出结果）

    @Test(parameters ="",dependsOnMethods = "history_path",priority=7)
    public void start_custom_scan_second() throws IOException, InterruptedException {
         RequestBody body = RequestBody.create(mediaType, "{\"ids\":[\"0c87bfc92e342172430acfa8763b266cf7f2605cc53b8bf6ee17e191ded4e9ed\"],\"paths\":["+asjson+"]}");
//        RequestBody body = RequestBody.create(mediaType,"{\"ids\":["+node_id+"],\"paths\":["+asjson+"]}");

        request1 = RequestUtil.requestPut(url+"/virus_killing/antiav_kill/start_custom_scan",token,body);
        JSONObject result = TestBase.ResultHttp(request1);
        //执行完成后等待一段时间，等待扫描状态更新。比较好的写法是不断去调用查询接口，后续优化
        //这里写等待10s的原因是怕状态未更新，拿到的是前一次的结果，所以等待时间加成10秒
        Thread.sleep(10000);

    }
    //获取扫描状态
    @Test(parameters ="",priority=8)
    public void list_node_second() throws IOException, InterruptedException {
        request1 = RequestUtil.requestGet(url+"/virus_killing/antiav_kill/list_node?limit=20&offset=0&order=&sort=",token);
        JSONObject result = TestBase.ResultHttp(request1);
//        System.out.println(JSONchange(result.get("data").toString(),"list"));
        for (int i=0;i<result.getJSONObject("data").getJSONArray("list").size();i++){
            name = result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("newName").toString();
            //System.out.println(name);
            if(name.equals(linux_name)){
                node_id = result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("id").toString();
                System.out.println(node_id);
                for (int num = 0;num < 50 ;num++){
                    if(result.getJSONObject("data").getJSONArray("list").getJSONObject(i).get("scanStatus").toString().equals("扫描完成")){
                        break;
                    }else {
                        num++;
                    }

                }
            }else{
                Thread.sleep(3000);
                i ++;
            }
        }
    }
    //查看病毒查杀结果
    @Test(parameters ="",priority=9)
    public void read_page_scan_file_second() throws IOException {
//      单独用例执行时需要打开注释
//      node_id = "0c87bfc92e342172430acfa8763b266cf7f2605cc53b8bf6ee17e191ded4e9ed";
        request1 = RequestUtil.requestGet(url+"/virus_killing/antiav/read_page_scan_file?limit=20&offset=0&node_id="+node_id,token);
        JSONObject result = TestBase.ResultHttp(request1);
        asjson = result.getJSONObject("data").getJSONArray("list").toString();
        System.out.println(asjson);
    }
    //处理病毒（删除病毒）
    @Test(parameters ="",dependsOnMethods = "read_page_scan_file_second",priority=10)
    public void fix_intelligent_items() throws IOException {

       // RequestBody body = RequestBody.create(mediaType, "{\"list\":[{\"desc\":\"HEUR/AGEN.1210240\",\"path\":\"/usr/mxj1/lightnews.exe\",\"hash\":\"8c797094b1c8e8f5d657db7089b15916\"}],\"node_id\":\"0c87bfc92e342172430acfa8763b266cf7f2605cc53b8bf6ee17e191ded4e9ed\"}");
        RequestBody body = RequestBody.create(mediaType,"{\"list\":"+asjson+",\"node_id\":\""+node_id+"\"}");
        //这里格式稍微注意下，可以通过打印出来的方式鉴别下入参的问题，是否保持一致。后续优化就是asjons为空的时候不执行，做个非空检验
        //System.out.println("{\"list\":"+asjson+",\"node_id\":\""+node_id+"\"}");
        request1 = RequestUtil.requestPut(url+"/virus_killing/antiav/fix_intelligent_items",token,body);
        JSONObject result = TestBase.ResultHttp(request1);
    }

    //再次查看病毒查杀结果，此时理论上应该没有病毒了
    @Test(parameters ="",priority=10)
    public void read_page_scan_file_third() throws IOException {
//      单独用例执行时需要打开注释
//        node_id = "0c87bfc92e342172430acfa8763b266cf7f2605cc53b8bf6ee17e191ded4e9ed";
        request1 = RequestUtil.requestGet(url+"/virus_killing/antiav/read_page_scan_file?limit=20&offset=0&node_id="+node_id,token);
        JSONObject result = TestBase.ResultHttp(request1);
        //list长度等于0，表明没有病毒了
        Assert.assertTrue(result.getJSONObject("data").getJSONArray("list").size()==0);
    }


    //防护日志中查看，扫描记录，后续优化就是size+1，现在先有记录即可



    //操作日志中查看，有一条处理记录
}
