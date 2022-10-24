package http.virus;

import MyTest.testBase12;
import com.alibaba.fastjson.JSONObject;
import com.test.utils.GetidUtil;
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

public class virus_process extends testBase12 {
    OkHttpClient client;
    MediaType mediaType;
    Request request1;
    String asjson;
    String path;
    String path_del_backup;
    String name;
    String node_id;
    String virus_id;
    String virus_id_del_backup;
    String down_virus;

    @BeforeClass()
    public void beforeClass123() throws IOException {
        //调用teseBase中的Init方法，对url进行赋值，无需每次更新url
        init();
        TestBase.http_user_init_other();
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(SkipHttpsUtil.getSSLSocketFactory(), SkipHttpsUtil.getX509TrustManager())
                .hostnameVerifier(SkipHttpsUtil.getHostnameVerifier())
                .build();
        mediaType= MediaType.parse("application/json;charset=UTF-8");
        request1 = RequestUtil.requestGet(url+"/virus_killing/antiav_kill/list_node?limit=20&offset=0&order=&sort=",token);
        JSONObject result = TestBase.ResultHttp(request1);
        node_id = GetidUtil.getId_new(linux,"ip",result);
    }
    //获取自定义查杀路径
    @Test(parameters ="",priority=1)
    public void history_path() throws IOException {


        request1 = RequestUtil.requestGet(url+"/virus_killing/antiav_kill/history_path",token);
        JSONObject result = TestBase.ResultHttp(request1);
        //如果是初次登陆，没有自定义查杀路径则新增自定义查杀路径，默认为/usr/mxj1
        if (result.getJSONArray("data").size()>0){
            path = result.getJSONArray("data").get(0).toString();
            asjson = result.getJSONArray("data").get(0).toString();
        }
        //如果是初次登陆，没有自定义查杀路径则新增自定义查杀路径，默认为/usr/mxj1
        else{
            path = "/home/";
            asjson = "{\"path\":\"/home/\",\"check\":1,\"idx\":null}";
        }

         System.out.println(asjson);
    }


    //病毒查杀流程
    @Test(parameters ="",priority=2)
    public void start_custom_scan() throws IOException, InterruptedException {
        //System.out.println("{\"ids\":["+"\""+node_id+"\"],\"paths\":["+asjson+"]}");
        //System.out.println("{\"ids\":["+"\""+node_id+"\"],\"paths\":[{\"path\":\"/home/\",\"check\":1,\"idx\":null}]}");
        //RequestBody body = RequestBody.create(mediaType,"{\"ids\":["+"\""+node_id+"\"],\"paths\":[{\"path\":\"/home/\",\"check\":1,\"idx\":null}]}");
        RequestBody body = RequestBody.create(mediaType,"{\"ids\":["+"\""+node_id+"\"],\"paths\":["+asjson+"]}");
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
    @Test(parameters ="",priority=4)
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
        String result = ShellUtil.shellCommand(linux,linux_username,linux_password,"cd /home;ls;cp lightnews.exe lightnews11.exe;");
    }

    //对应终端执行病毒查杀（自定义路径，较快出结果）

    @Test(parameters ="",priority=7)
    public void start_custom_scan_second() throws IOException, InterruptedException {
        RequestBody body = RequestBody.create(mediaType,"{\"ids\":["+"\""+node_id+"\"],\"paths\":["+asjson+"]}");
        request1 = RequestUtil.requestPut(url+"/virus_killing/antiav_kill/start_custom_scan",token,body);
        JSONObject result = TestBase.ResultHttp(request1);
        //执行完成后等待一段时间，等待扫描状态更新。比较好的写法是不断去调用查询接口，后续优化
        Thread.sleep(3000);

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
    @Test(parameters ="",priority=10)
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
    public void read_page_scan_file_third() throws IOException, InterruptedException {
//      单独用例执行时需要打开注释
//        node_id = "0c87bfc92e342172430acfa8763b266cf7f2605cc53b8bf6ee17e191ded4e9ed";
        request1 = RequestUtil.requestGet(url+"/virus_killing/antiav/read_page_scan_file?limit=20&offset=0&node_id="+node_id,token);
        JSONObject result = TestBase.ResultHttp(request1);
        //list长度等于0，表明没有病毒了
        Assert.assertTrue(result.getJSONObject("data").getJSONArray("list").size()==0);
        Thread.sleep(3000);
        Thread.sleep(3000);
    }


    @Test(parameters ="",priority=11,description = "查看隔离区理论有病毒")
    public void  antiav_kill_detail() throws IOException {
//      单独用例执行时需要打开注释
//        node_id = "0c87bfc92e342172430acfa8763b266cf7f2605cc53b8bf6ee17e191ded4e9ed";
        System.out.println(node_id);
        request1 = RequestUtil.requestGet(url+"/virus_killing/antiav_kill/get_detail?limit=20&offset=0&order=&sort=&nodeId="+node_id+"&type=1",token);
        JSONObject result = TestBase.ResultHttp(request1);
        if(result.getJSONObject("data").getJSONArray("list").size()>0){
            virus_id = result.getJSONObject("data").getJSONArray("list").getJSONObject(0).get("id").toString();
            path = result.getJSONObject("data").getJSONArray("list").getJSONObject(0).get("path").toString();
            virus_id_del_backup = result.getJSONObject("data").getJSONArray("list").getJSONObject(1).get("id").toString();
            path_del_backup = result.getJSONObject("data").getJSONArray("list").getJSONObject(1).get("path").toString();

        }
        else{
            virus_id = "没有病毒";
            path ="没有病毒";
            virus_id_del_backup = "没有病毒";
            path_del_backup ="没有病毒";
            System.out.println("没有病毒样本");
        }
    }


    @Test(parameters ="",priority=12,description = "提取病毒")
    public void export_file() throws IOException {
//      单独用例执行时需要打开注释
//        node_id = "0c87bfc92e342172430acfa8763b266cf7f2605cc53b8bf6ee17e191ded4e9ed";
        request1 = RequestUtil.requestGet(url+"/virus_killing/antiav_kill/export_file?nodeId="+node_id+"&paths[]="+virus_id,token);
        JSONObject result = TestBase.ResultHttp(request1);
        down_virus = result.get("data").toString();
    }


    //@Test(parameters ="",priority=13,description = "下载提取的病毒样本")
    public void export_file_antiav() throws IOException {
//      单独用例执行时需要打开注释
//        node_id = "0c87bfc92e342172430acfa8763b266cf7f2605cc53b8bf6ee17e191ded4e9ed";
        request1 = RequestUtil.requestGet(url+"/virus_killing/antiav_kill/export_file_antiav?nodeId="+node_id+"&paths[]="+virus_id,token);
        JSONObject result = TestBase.ResultHttp(request1);

    }


    @Test(parameters ="",priority=14,description = "恢复隔离区文件")
    public void restore() throws IOException {

        //RequestBody body = RequestBody.create(mediaType,"{\"node_id\":\"aff71548b1e51fa02558c40094f3ce6b42a7560994e50de90f8c5b55b515aeb1\",\"paths\":[\"/home/lightnews.exe\"]}");
       //System.out.println("{\"node_id\":"+"\""+node_id+"\",\"paths\":["+"\""+path+"\"]}");
        RequestBody body = RequestBody.create(mediaType,"{\"node_id\":"+"\""+node_id+"\",\"paths\":["+"\""+path+"\"]}");
        request1 = RequestUtil.requestPut(url+"/virus_killing/antiav/restore",token,body);
        JSONObject result = TestBase.ResultHttp(request1);

    }

    @Test(parameters ="",priority=15,description = "删除隔离区文件")
    public void del_backup() throws IOException {

        //RequestBody body = RequestBody.create(mediaType,"{\"node_id\":\"aff71548b1e51fa02558c40094f3ce6b42a7560994e50de90f8c5b55b515aeb1\",\"paths\":[\"/home/lightnews.exe\"]}");
        RequestBody body = RequestBody.create(mediaType,"{\"node_id\":"+"\""+node_id+"\",\"paths\":["+"\""+path_del_backup+"\"]}");
        request1 = RequestUtil.requestPut(url+"/virus_killing/antiav/del_backup",token,body);
        JSONObject result = TestBase.ResultHttp(request1);

    }


    //添加信任区

    @Test(parameters ="",priority=16,description = "查看信任区列表，理论无数据")
    public void  get_detail_trust() throws IOException {
//      单独用例执行时需要打开注释
//        node_id = "0c87bfc92e342172430acfa8763b266cf7f2605cc53b8bf6ee17e191ded4e9ed";
        System.out.println(node_id);
        request1 = RequestUtil.requestGet(url+"/virus_killing/antiav_kill/get_detail?limit=20&offset=0&order=&sort=&nodeId="+node_id+"&type=2",token);
        JSONObject result = TestBase.ResultHttp(request1);
    }

    @Test(parameters ="",priority=17,description = "添加白名单")
    public void  ignore() throws IOException {
        RequestBody body = RequestBody.create(mediaType,"{\"node_id\":"+"\""+node_id+"\",\"type\":\"3\",\"paths\":["+"\""+path+"\"]}");
        request1 = RequestUtil.requestPut(url+"/virus_killing/antiav/ignore",token,body);
        JSONObject result = TestBase.ResultHttp(request1);
    }

    //重新扫描，原先病毒不会出现



    @Test(parameters ="",priority=18,description = "第三次扫描")
    public void start_custom_scan_third() throws IOException, InterruptedException {
        RequestBody body = RequestBody.create(mediaType,"{\"ids\":["+"\""+node_id+"\"],\"paths\":["+asjson+"]}");
        request1 = RequestUtil.requestPut(url+"/virus_killing/antiav_kill/start_custom_scan",token,body);
        JSONObject result = TestBase.ResultHttp(request1);
        //执行完成后等待一段时间，等待扫描状态更新。比较好的写法是不断去调用查询接口，后续优化
        Thread.sleep(3000);

        //执行完成后等待一段时间，等待扫描状态更新。比较好的写法是不断去调用查询接口，后续优化
        //这里写等待10s的原因是怕状态未更新，拿到的是前一次的结果，所以等待时间加成10秒
        Thread.sleep(10000);

    }



    @Test(parameters ="",priority=19,description = "删除白名单")
    public void  cancel_ignore() throws IOException {
        RequestBody body = RequestBody.create(mediaType,"{\"node_id\":"+"\""+node_id+"\",\"paths\":["+"\""+path+"\"]}");
        request1 = RequestUtil.requestPut(url+"/virus_killing/antiav/cancel_ignore",token,body);
        JSONObject result = TestBase.ResultHttp(request1);
    }



}
