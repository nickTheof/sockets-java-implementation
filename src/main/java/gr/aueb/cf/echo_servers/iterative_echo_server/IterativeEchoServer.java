package gr.aueb.cf.echo_servers.iterative_echo_server;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class IterativeEchoServer implements Runnable {

    @Override
    public void run() {
        try (ServerSocket servFd = new ServerSocket()) {
            servFd.bind(new InetSocketAddress("127.0.0.1", 7));
            System.out.println("Server started ...");
            while (true) {
                Socket socket = servFd.accept();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))){
                    String message;
                    while ((message = br.readLine()) != null) {
                        bw.write(message);
                        bw.newLine();
                        bw.flush();
                    }
                }
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Server error. " + e.getMessage());
        }
    }

}
