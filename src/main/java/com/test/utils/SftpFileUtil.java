package com.test.utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.*;
import java.util.Properties;

public class SftpFileUtil {
    public static void inputFile_linux() throws Exception {

        //声明JSCH对象
        JSch jSch = new JSch();
        //获取一个Linux会话
        Session session = jSch.getSession("root", "10.50.38.71", 22);
        //设置登录密码
        session.setPassword("123456");
        //关闭key的检验
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        session.setConfig(sshConfig);
        //连接Linux
        session.connect();
        //通过sftp的方式连接
        ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
        //上传文件
        File file = new File("bingduku/lightnews.exe");
        InputStream inputStream = new FileInputStream(file);
        channel.put(inputStream, "/usr/mxj1/lightnews.exe");
//        //下载文件
//        OutputStream out = new FileOutputStream("d:\4.txt");
//        channel.get("/root/file/1.txt", out);
        //关闭流
        inputStream.close();
//        out.close();
    }
}
