package gr.aueb.cf.datetime_servers.concurrent_datetime_server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentDatetimeServerApp {
    private static final int PORT = 13;
    private static final int MAX_NUM_THREADS = 100;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_NUM_THREADS);
        try (ServerSocket servFd = new ServerSocket()) {
            servFd.bind(new InetSocketAddress("127.0.0.1", PORT));
            System.out.println("Server started ...");
            for (; ; ) {
                Socket connectedFd = servFd.accept();
                executorService.execute(new ConcurrentDatetimeServer(connectedFd));
            }
        } catch (IOException e) {
            System.out.println("Server error. " + e.getMessage());
        } finally {
            executorService.shutdown();
            System.out.println("Server stopped ...");
        }
    }
}
