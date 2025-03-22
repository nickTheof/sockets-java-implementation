package gr.aueb.cf.ftp_servers.concurrent_ftp_server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConcurrentFtpServerApp {
    private static final int PORT = 20;
    private static final int MAX_THREAD_POOL = 10;
    private static final Logger LOGGER = Logger.getLogger(ConcurrentFtpServerApp.class.getName());

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD_POOL);

        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress("127.0.0.1", PORT));
            LOGGER.info("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                executorService.execute(new ConcurrentFtpServer(clientSocket));
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Server error: " + e.getMessage(), e);
        } finally {
            executorService.shutdown();
            LOGGER.info("Server stopped.");
        }
    }
}
