使用说明手册（图文）见
https://www.yuque.com/g/maoxuanjie/zn8c04/gacg2lb6cc3dcm4p/collaborator/join?token=efT30n2kzWXVl6ak# 《testng使用说明和FAQ手册》





 部署的终端：
10.50.38.213   mxjauto/123456
路径：C:\Users\mxjauto\Desktop\Test_Edr
打开方式：选中pom.xml 右键，Edit with IntelliJ IDEA....

打开后的效果如下：



使用：
选中需要执行的配置脚本 test_smoke.xml  Run'..\test_smoker.xml'


运行中样式：


运行结束后结果图：
执行成功，失败，跳过数量统计均显示


在test-output1目录下，可以看到生成一个新的html（测试报告汇总）：最下方的为最新一条



配置：
测试报告路径与名称
输出的测试报告路径可以自定义test→java→Listener→ExtenTestNGReporterListenerOld
OUTPUT_FOLDER修改输出路径
FILE_NAME修改输出的报告名称


中心与终端的地址与账号密码
resources→EDR→V3_0_1_2.propertices
其中：url为中心地址
linux为linux的终端ip，linux_username是账号,linux_passwor是密码
windows为windows终端ip——一般对应的windows终端开启IIS服务，后续测试中使用



产品名称与版本
MyTest下的testbase12
pathName就是读取propertices路径的设置，一般不做修改。


如图：




写法说明：
1.执行前的初始化
@BeforeClass 方法
首先Init()读取testBase中的变量赋值
http_user_init_other 是为了拿到token值在http方法中使用
client初始化，使用okhttpclinet初始化，后续使用时比较简单
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
    mediaType_text = MediaType.parse("text/plain");
}

2.用例写法
V1：testng参数说明
parameters是为了读取testBase中申明的变量类型与赋值
priority指的是执行的顺序，一般从小到大依次执行，可以有跨越（只有1、3没有2也是可以的）。重复时需要调整（可执行）
description描述：类似于注解，没有实际作用，更多是为了其他人看懂为啥，做啥
group分组：可以增加分组概念，仅执行P1分组用例等
@Test(parameters = "",priority = 3,description = "运维日志类型")
public void get_risk() throws IOException, InterruptedException {

    request1 = RequestUtil.requestGet(url + "/operationlog/get_log_event", token);
    JSONObject result = TestBase.ResultHttp(request1);
    Assert.assertTrue(AssertUtil.ifsuccess(result));
}



V2：http类型请求使用说明
1）对于get方法，仅需要requestGet后面拼接url即可，token就是初始化时获得的token
JSONObject result = TestBase.ResultHttp(request1);是为了获取结果，返回结果类似：		{"error_code":200,"message":"success"}，也会有data值的情况
Assert.assertTrue(AssertUtil.ifsuccess(result));是断言，判断这个接口是否正确调用，返回的error_code是否200 或者message是否success

2）对于post/put方法
 RequestBody body 中，对于请求参数进行一个赋值，然后RequestUtil.requestPost1方法即可
对于RequestBody的获取，可以借助postman工具（后续备注中说明）
@Test(parameters = "", priority = 5,description = "新增基线检查")
public void add_task() throws IOException {
    RequestBody body = RequestBody.create(mediaType,"{\"name\":"+"\""+name_delete+"\",\"nodes\":["+"\""+node_id+"\"],\"ployIds\":[2,303],\"time\":{\"type\":\"day\",\"day\":[0],\"time\":\"00:00:00\"},\"params\":{},\"type\":1}");
    request = RequestUtil.requestPost1(url + "base_line/save",body, token);
    JSONObject result = TestBase.ResultHttp(request);
    Assert.assertTrue(AssertUtil.ifsuccess(result));

}

3）对于requestDELETE方法
url拼接的时候带入需要删除的任务/数据 ID,一般在查询时回去id，然后按顺序执行时塞入ID即可
@Test(parameters = "", priority = 8,description = "删除基线检查")
public void delete_task() throws IOException {

    request = RequestUtil.requestDELETE(url + "/task/delete_task?ids[]="+schedule_id_delete, token);
    JSONObject result = TestBase.ResultHttp(request);
    Assert.assertTrue(AssertUtil.ifsuccess(result));


}



封装与常用的方法：
AssertUtil 封装了断言的相关，判断是否调用成功（后续还会继续增加，失败抛出异常等）
GetidUtil  主要是去JSONObject中拿到ID一类的写起来代码显得很丑很难看，做了一个封装。
RequestUtil 把http几个常用的get,post,put,delete合起来封装了一下，简化实际用例代码内容
SftpFileUtil 有终端远程相关的用例，使用到这块（网上抄的+写了一丢丢）。远程和上传文件给linux
SkipHttpsUtil okhttp请求跳过sll证书认证的东西，尽量不动。




备注：
结合postman工具获取body
在网页network中找到对应方法，邮件copy→copy as cURL(bash)



然后打开postman 左上角File→Import

在Raw text中，粘贴刚才复制的内容，Continue

右侧点卡</>，语言选择Java-OkHttp,RequestBody复制即可



获取ID的写法：
@Test(parameters = "", priority = 6,description = "查看列表，新增的任务出现在列表中")
public void list_task() throws IOException {
    request = RequestUtil.requestGet(url + "/task/get_task_list?limit=20&offset=0&order=&sort=", token);
    JSONObject result = TestBase.ResultHttp(request);
    Assert.assertTrue(AssertUtil.ifsuccess(result));
    schedule_id =GetidUtil.getId_new(name,"name",result);
    schedule_id_delete = GetidUtil.getId_new(name_delete,"name",result);
}
schedule_id和schedule_id_delete就得到了
