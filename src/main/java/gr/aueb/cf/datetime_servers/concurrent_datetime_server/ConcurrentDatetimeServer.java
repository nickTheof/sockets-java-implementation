package gr.aueb.cf.datetime_servers.concurrent_datetime_server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalDateTime;


public class ConcurrentDatetimeServer extends Thread {
    private final Socket socket;

    public ConcurrentDatetimeServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))){
            bw.write(LocalDateTime.now().toString());
            bw.flush();
        } catch (IOException e) {
            System.out.println("Socket error. " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Socket error. Fail to close socket resource.");
            }
        }
    }
}
