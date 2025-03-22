package gr.aueb.cf.echo_servers.concurrent_echo_server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentEchoServerApp {
    private static final int PORT = 7;
    private static final int THREAD_POOL_SIZE = 10;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        try (ServerSocket servFd = new ServerSocket()) {
            servFd.bind(new InetSocketAddress("127.0.0.1", PORT));
            System.out.println("Server is running in port " + PORT + " ...");
            while (true) {
                Socket connectedFd = servFd.accept();
                executorService.execute(new ConcurrentEchoServer(connectedFd));
            }
        } catch (IOException e) {
            System.out.println("Server Error. " + e.getMessage());
        } finally {
            executorService.shutdown();
            System.out.println("Server closed ...");
        }
    }
}
