package edu.company.Tread;
import Client.EchoClient;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Connection implements Runnable {
    private final Socket clientSocked;
    private static ArrayList<EchoClient> list = new ArrayList<>();


    public Connection(Socket clientSocked) {
        this.clientSocked = clientSocked;
    }

    public static void handle(Socket socket) {
        String username = "Client" + socket.getPort();
        System.out.println("Подключен клиент:" + socket);
        try (
                socket;
                Scanner reader = Connection.getReader(socket);
                PrintWriter writer = getWriter(socket);) {
            Connection.sendResponse("Hello " + username, writer);

            while (true) {
                String message = reader.nextLine();
                sendToAll(username, socket, message);
                if (Connection.isEmptyMsg(message) || Connection.isQuitMsg(message)) {
                    break;
                }
//                CsendToAll(username,clientSocked,message);
//                Connection.sendResponse(message.toUpperCase(), writer);
            }
        } catch (NoSuchElementException ex) {
            System.out.println("Клиент закрыл соединение! ");
        } catch (IOException e) {
            System.out.printf("Клиент отключен: %s%n", socket);
        }

    }


    public static PrintWriter getWriter(Socket socket) throws IOException {
        OutputStream stream = socket.getOutputStream();
        return new PrintWriter(stream);
    }

    public static Scanner getReader(Socket socket) throws IOException {
        InputStream stream = socket.getInputStream();
        InputStreamReader input = new InputStreamReader(stream, StandardCharsets.UTF_8);
        return new Scanner(input);
    }

    public static boolean isQuitMsg(String message) {
        return "bye".equals(message.toLowerCase());
    }

    public static boolean isEmptyMsg(String message) {
        return message == null || message.isBlank();
    }

    public static void sendResponse(String response, Writer writer) throws IOException {
        writer.write(response);
        writer.write(System.lineSeparator());
        writer.flush();
    }

    public static void sendToAll(String name, Socket socket, String message) {
        list.stream().filter(c -> c.getSocket() != socket).forEach(c -> {
            try {
                PrintWriter printWriter = getWriter(c.getSocket());
                sendResponse(name + ": " + message, printWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void run() {
        list.add(new EchoClient(clientSocked));
        handle(clientSocked);

    }
}