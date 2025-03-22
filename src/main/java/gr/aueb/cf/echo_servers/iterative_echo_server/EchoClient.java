package gr.aueb.cf.echo_servers.iterative_echo_server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) {
        final int PORT = 7;
        String message;
        try (Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), PORT);
             Scanner scanner = new Scanner(System.in);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
            while (true){
                System.out.println("Please enter your message (type bye for exit) ...");
                message = scanner.nextLine();
                bw.write(message);
                bw.newLine();
                bw.flush();
                String serverResponse = br.readLine();
                System.out.println("Echo: " + serverResponse);
                if ("bye".equalsIgnoreCase(message)){
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Client Socket Error. " + e.getMessage());
        }
    }
}
