package edu.company.Tread;
import Client.EchoClient;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {
    private final int port;
    private final ExecutorService pool = Executors.newCachedThreadPool();



    EchoServer(int port) {
        this.port = port;
    }

    public void run() {
        try (ServerSocket server = new ServerSocket(port)) {
            while (!server.isClosed()) {
                Socket clientSocked = server.accept();
                pool.submit(new Connection(clientSocked));


            }
        } catch (IOException e) {
            var formatMsg = "Port is busy!";
            System.out.printf(formatMsg, port);
            e.printStackTrace();
        }
    }





}
