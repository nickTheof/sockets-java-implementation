package gr.aueb.cf.ftp_servers.concurrent_ftp_server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConcurrentFtpServer extends Thread {
    private static final Logger LOGGER = Logger.getLogger(ConcurrentFtpServer.class.getName());
    private final Socket socket;

    public ConcurrentFtpServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

            String filename;
            while ((filename = br.readLine()) != null) {
                if ("bye".equalsIgnoreCase(filename.trim())) {
                    LOGGER.info("Client disconnected.");
                    break;
                }

                Path file = Path.of("./src/main/Resources/files/" + filename);

                if (Files.exists(file)) {
                    try (BufferedReader fileReader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                        String data;
                        while ((data = fileReader.readLine()) != null) {
                            bw.write(data);
                            bw.newLine();
                            bw.flush();
                        }
                    }
                } else {
                    bw.write("The requested file does not exist.\n");
                }
                bw.write("END\n");
                bw.flush();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error in socket thread: " + e.getMessage(), e);
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Error closing socket.", e);
            }
        }
    }
}
