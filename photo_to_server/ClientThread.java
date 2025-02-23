package com.example.photo_to_server;

import java.io.*;
import java.net.*;

public class ClientThread implements Runnable {
    private String serverIp;
    private int serverPort;
    private File imageFile;
    private int fileSizeInBytes;

    public ClientThread(String serverIp, int serverPort, File imageFile, int fileSizeInBytes) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.imageFile = imageFile;
        this.fileSizeInBytes = fileSizeInBytes;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(serverIp, serverPort);
            OutputStream outputStream = socket.getOutputStream();
            FileInputStream fileInputStream = new FileInputStream(imageFile);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.flush();
            fileInputStream.close();
            socket.close();

            System.out.println("Image sent successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}