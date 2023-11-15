package JustTest;

import com.test.utils.ShellUtil;
import org.testng.annotations.Test;


public class linuxdemo {

    @Test(description = "连接linux，执行命令行")
    public void testlinux(){
        String result = ShellUtil.shellCommand("10.20.128.82","root","Ah@123456","cd redis2;ls");
//      注释掉的原因是ShellUtil.shellCommand中就有返回string了，如果再打印一次就会出现重复记录，所以就注释了
//        System.out.println(result);
    }


    @Test(description = "上传文件")
    public void testUpload(){
        String result = ShellUtil.Upload_linux("10.20.128.82","root","Ah@123456",10,"C:\\Users\\admin\\IdeaProjects\\Test_Edr\\5yue.txt","/root/aaa");
        //成功写入后，出现
        // Last login: Mon Jun  6 19:17:46 2022 from 10.20.28.192
        //[root@10-20-128-68 ~]#
        //上传文件可用于病毒文件打包上传
    }
}
