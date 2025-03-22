package gr.aueb.cf.datetime_servers.iterative_datetime_server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class IterativeDateTimeServer extends Thread{
    @Override
    public void run() {

        try (ServerSocket servFd = new ServerSocket()) {
            servFd.bind(new InetSocketAddress("127.0.0.1", 13));
            System.out.println("Server started ...");
            while (true) {
                Socket connectedFd = servFd.accept();
                try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connectedFd.getOutputStream()))){
                    bw.write(LocalDateTime.now().toString());
                    bw.flush();
                } catch (IOException e) {
                    System.out.println("Socket error. " + e.getMessage());
                } finally {
                    try {
                        connectedFd.close();
                    } catch (IOException e) {
                        System.out.println("Socket error. Fail to close socket resource.");
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Server error. " + e.getMessage());
        }


    }
}
