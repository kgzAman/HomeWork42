package edu.company.Tread;
import Client.EchoClient;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Connection extends Thread{
    private final Socket clientSocked;
    public ArrayList<EchoClient> list=new ArrayList<>();



    public Connection(Socket clientSocked) {
        this.clientSocked = clientSocked;
    }


    @Override
    public void run() {
            System.out.println("Подключен клиент:"+clientSocked);
            try (
                    clientSocked;
                    Scanner reader = getReader(clientSocked);
                    PrintWriter writer = getWriter(clientSocked);){
                sendResponse("Hello "+ clientSocked,writer);

                while (true){
                    String message = reader.nextLine();
                    if(isEmptyMsg(message)|| isQuitMsg(message)){
                        break;
                    }
                    sendResponse(message.toUpperCase(),writer);
                }
            }catch (NoSuchElementException ex ){
                System.out.println("Клиент закрыл соединение! ");
            }catch (IOException e ){
                System.out.printf("Клиент отключен: %s%n",clientSocked);
            }
        }

    private PrintWriter getWriter(Socket socket)throws IOException {
        OutputStream stream = socket.getOutputStream();
        return  new PrintWriter(socket.getOutputStream());
    }

    private Scanner getReader(Socket socket)throws  IOException{
        InputStream stream = socket.getInputStream();
        InputStreamReader input = new InputStreamReader(stream, StandardCharsets.UTF_8);
        return  new Scanner(input);
    }

    private boolean isQuitMsg(String message){
        return "bye".equals(message.toLowerCase());
    }

    private   boolean isEmptyMsg(String message){
        return message==null || message.isBlank();
    }

    private void sendResponse(String response,Writer writer)throws IOException{
        writer.write(response);
        writer.write(System.lineSeparator());
        writer.flush();
    }

    public static void sendToAll(String name, Socket socket, String message){
        .stream().filter(c -> c.getSocket() != socket).forEach(c -> {
            try {
                PrintWriter printWriter = Action.getWriter(c.getSocket());
                Action.sendResponse(name + ": " + message, printWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
}
