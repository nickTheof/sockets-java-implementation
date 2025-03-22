package gr.aueb.cf.ftp_servers.concurrent_ftp_server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientFtpServer {
    private static final int PORT = 20;
    private static final Logger LOGGER = Logger.getLogger(ClientFtpServer.class.getName());

    public static void main(String[] args) {
        try (Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), PORT);
             Scanner scanner = new Scanner(System.in);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

            while (true) {
                System.out.print("Enter the filename (or type 'bye' to exit): ");
                String fileName = scanner.nextLine().trim();

                if ("bye".equalsIgnoreCase(fileName)) {
                    bw.write("bye\n");
                    bw.flush();
                    LOGGER.info("Client exiting...");
                    break;
                }

                bw.write(fileName);
                bw.newLine();
                bw.flush();

                String responseData;
                while ((responseData = br.readLine()) != null) {
                    if ("END".equals(responseData)) break;
                    System.out.println(responseData);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Client Socket Error: " + e.getMessage(), e);
        }
    }
}
