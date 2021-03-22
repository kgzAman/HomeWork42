package edu.company.Tread;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {
        private final int port;
        private final ExecutorService pool = Executors.newCachedThreadPool();

        EchoServer(int port) {
            this.port = port;
        }


        public void  run(){
            try (ServerSocket server = new ServerSocket(port)){
             while (!server.isClosed()){
                 Socket clientSocked = server.accept();
                 pool.submit(()->handle(clientSocked));

             }
            } catch (IOException e) {
                var formatMsg = "Port is busy!";
                System.out.printf(formatMsg,port);
                e.printStackTrace();
            }
        }

        private void handle(Socket socket) {
            System.out.printf("Подключен клиент: %s%n", socket );
            try (
                    socket;
                    Scanner reader = getReader(socket);
                    PrintWriter writer = getWriter(socket);){
                    sendResponse("Привет "+ socket,writer);
                    while (true){
                        String message = reader.nextLine();
                        if(isEmptyMsg(message)||isQiutMsg(message)){
                            break;
                        }
                        sendResponse(message.toUpperCase(),writer);
                    }
            }catch (NoSuchElementException ex ){
                System.out.println("Клиент закрыл соединение! ");
            }catch (IOException e ){
                System.out.printf("Клиент отключен: %s%n",socket);
            }

        }

        private static PrintWriter getWriter(Socket socket)throws IOException{
            OutputStream stream = socket.getOutputStream();
            return  new PrintWriter(stream);
        }

        private static Scanner getReader(Socket socket)throws  IOException{
            InputStream stream = socket.getInputStream();
            InputStreamReader input = new InputStreamReader(stream, StandardCharsets.UTF_8);
            return  new Scanner(input);
        }

        private static boolean isQiutMsg(String message){
            return "bye".equals(message.toLowerCase());
        }
        private static boolean isEmptyMsg(String message){
            return message==null || message.isBlank();
        }
        private  static void sendResponse(String response,Writer writer)throws IOException{
            writer.write(response);
            writer.write(System.lineSeparator());
            writer.flush();
        }

    }
