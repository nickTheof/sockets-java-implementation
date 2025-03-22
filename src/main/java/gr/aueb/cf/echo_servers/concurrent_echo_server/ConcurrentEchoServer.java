package gr.aueb.cf.echo_servers.concurrent_echo_server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ConcurrentEchoServer implements Runnable {
    private final Socket socket;

    public ConcurrentEchoServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
                bw.flush();
            }
        } catch (IOException e) {
//            e.printStackTrace();
            System.err.println("Socket error: " + e.getMessage());
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (IOException e) {
//                e.printStackTrace();
                System.err.println("Socket error. Fail to close socket resource");
            }

        }
    }
}
