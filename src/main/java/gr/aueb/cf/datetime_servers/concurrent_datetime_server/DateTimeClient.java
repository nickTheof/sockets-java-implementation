package gr.aueb.cf.datetime_servers.concurrent_datetime_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class DateTimeClient {
    public static void main(String[] args) {
        Socket socket = null;
        final int PORT = 13;
        final String HOST = "127.0.0.1";
        StringBuilder sb = new StringBuilder();

        try {
            InetAddress inetAddress = InetAddress.getByName(HOST);
            socket = new Socket(inetAddress, PORT);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                System.out.println("Local Server Date Time: " + sb);
            }
        } catch (IOException e) {
            System.out.println("Client socket error. " + e.getMessage());
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.out.println("Socket error. Fail to close socket resource");
            }
        }
    }
}
