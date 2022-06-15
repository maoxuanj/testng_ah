package com.test.utils;

import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.test.utils.ShellUtil.ShellSession.openChannelSftp;

public class ShellUtil {

    private static final Map<String, ShellSession> SHELL_SESSION_MAP = new HashMap<>();//sessionMap
    static final JSch jsch = new JSch();//session构造器
    private static final Integer TIME_OUT_HOUR = 1;//超时时间
    private static final Integer DEFAULT_PORT = 22;//默认连接端口
    private static final String DEFAULT_CHANNEL_TYPE = "exec"; //默认类别

    private static ExecutorService executorQueue = Executors.newSingleThreadExecutor();

    public static void runExecute(Runnable paramRunnable) {
        executorQueue.submit(paramRunnable);
    }


    public static final String Upload_linux(final String host , final String username,final String password,final int DEF_WAIT_SECONDS,final String sourcefile
    ,final String dstfilepath){
        Session session = ShellSession.openSession(host, username, password, DEF_WAIT_SECONDS);
        ChannelShell openChannelShell = ShellSession.openChannelShell(session);
        openChannelShell.setInputStream(System.in);
        openChannelShell.setOutputStream(System.out);
        ChannelSftp openChannelSftp = openChannelSftp(session);
        try {
           openChannelSftp.put(sourcefile, dstfilepath, ChannelSftp.OVERWRITE);
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public static final String shellCommand(final String address, final String username, final String password, final String command) {
        return ShellUtil.shellCommand(address, username, password, ShellUtil.DEFAULT_CHANNEL_TYPE, command);
    }

    public static final String shellCommand(final String address, final String username, final String password,
                                            final String channelType, final String command) {
        return ShellUtil.shellCommand(address, username, password, ShellUtil.DEFAULT_PORT, channelType, command);
    }


    public static final String shellCommand(final String address, final String username, final String password,
                                            final Integer port, final String channelType, final String command) {
        if (!ShellUtil.checkNotNull(address, username, password, port, channelType, command))
            throw new NullPointerException("params cannot be null");

        if (!ShellUtil.SHELL_SESSION_MAP.containsKey(address + ":" + username))
            ShellUtil.SHELL_SESSION_MAP.put(address + ":" + username, new ShellUtil.ShellSession(address, username, password, port));

        ShellSession shellSession = ShellUtil.SHELL_SESSION_MAP.get(address + ":" + username);
        shellSession.updateLastExecTime();//更新操作时间

        //清除超时session实例
        ShellUtil.SHELL_SESSION_MAP.entrySet().removeIf(
                entry -> entry.getValue().lastExecTime.getTime() + ShellUtil.TIME_OUT_HOUR * 60 * 60 * 1000 < System.currentTimeMillis());

        return shellSession == null ? null : shellSession.execute(channelType, command);
    }



    private static final Boolean checkNotNull(Object... objects) {
        if (objects == null)
            return false;
        for (Object obj : objects) {
            if (obj == null)
                return false;
        }
        return true;
    }




    static class ShellSession {
    Session session;
    Date lastExecTime;

    private ShellSession(String address, String username, String password, Integer port) {
        try {
            session = ShellUtil.jsch.getSession(username, address, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");//去掉连接确认的
            session.connect(30000);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public void updateLastExecTime() {
        lastExecTime = Calendar.getInstance().getTime();
    }

    private String execute(String channelType, String command) {
        if (this.session == null)
            return null;
        Channel channel = null;
        //InputStream input = null;
        BufferedReader bufferedReader = null;
        String resp = "";
        try {
            channel = this.session.openChannel(channelType);
            ((ChannelExec) channel).setCommand(command);

            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            channel.connect();

            bufferedReader = new BufferedReader(new InputStreamReader(channel.getInputStream()));

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                resp += line + "\n";
            }
            if (resp != null && !resp.equals("")) {
                resp = resp.substring(0, resp.length() - 1);
            }
            System.out.println(resp);
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
        return resp;
    }




        /**
         * 创建服务器连接
         *
         * @param host
         *            主机
         * @param user
         *            用户
         * @param password
         *            密码
         * @param waitSeconds
         *            等待秒数
         * @return
         */
        private static Session openSession(String host, String user, String password, int waitSeconds) {
            Session session = null;
            try {
                JSch jsch = new JSch();
                session = jsch.getSession(user, host, DEFAULT_PORT);
                noCheckHostKey(session);
                session.setPassword(password);
                // 这个设置很重要，必须要设置等待时长为大于等于2分钟
                session.connect(waitSeconds * 1000);
                if (!session.isConnected()) {
                    throw new IOException("We can't connection to[" + host + "]");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return session;
        }

        /**
         * 不作检查主机键值
         *
         * @param session
         */
        private static void noCheckHostKey(Session session) {
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
        }

        /**
         * 连接shell
         *
         * @param session
         *            session
         * @return {@link ChannelShell}
         */
        private static ChannelShell openChannelShell(Session session) {
            ChannelShell channel = null;
            try {
                channel = (ChannelShell) session.openChannel("shell");
                channel.setEnv("LANG", "en_US.UTF-8");
                channel.setAgentForwarding(false);
                channel.setPtySize(500, 500, 1000, 1000);
                channel.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (channel == null) {
                throw new IllegalArgumentException("The channle init was wrong");
            }
            return channel;
        }

        /**
         * 连接sftp
         *
         * @param session
         * @return {@link ChannelSftp}
         */
        static ChannelSftp openChannelSftp(Session session) {
            ChannelSftp channel = null;
            try {
                channel = (ChannelSftp) session.openChannel("sftp");
                channel.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return channel;
        }
    }
}

