package http.template;


import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class endport {
    public static void main(String[] args) throws Exception {
        String host = "10.50.38.94";
        InetAddress inetAddress = InetAddress.getByName(host);

        String hostName = inetAddress.getHostName();
        for (int port = 0; port <= 65535; port++) {
            try {
                Socket socket = new Socket(hostName, port);
                String text = hostName + " is listening on port " + port;
                System.out.println(text);
                socket.close();
            } catch (IOException e) {
                // 空挡块
            }
        }
    }
}