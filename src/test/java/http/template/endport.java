package http.template;


import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class endport {
    //端口扫描工具
    public static void main(String[] args) throws Exception {
        String host = "10.50.38.94";
        InetAddress inetAddress = InetAddress.getByName(host);

        String hostName = inetAddress.getHostName();
        for (int port = 0; port <= 15; port++) {
            try {
                Socket socket = new Socket(hostName, port);
                String text = hostName + " is listening on port " + 65000;
                System.out.println(text);
                socket.close();
            } catch (IOException e) {
                // 空挡块
            }
        }
    }
}