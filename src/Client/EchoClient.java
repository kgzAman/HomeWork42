package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EchoClient {
    private final int port;
    private final String host;

    private EchoClient(String host, int port ) {
        this.host = host;
        this.port = port;
    }
    public static EchoClient connectTo(int port){
        var localHost="127.0.0.1";
        return new EchoClient(localHost,port);
    }
    public void run(){
        System.out.printf("Enter 'Bye' to exit%n%n%n");
        try (var socket = new Socket(host,port)) {

            Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            InputStream in = socket.getInputStream();
            var scanner1 = new Scanner(new InputStreamReader(in, StandardCharsets.UTF_8));

            try (scanner;writer;in;scanner1){
                while (true){
                    System.out.println("Enter message: ");
                    String message = scanner.nextLine();
                    writer.write(message);
                    writer.write(System.lineSeparator());
                    writer.flush();

                    String message1 = scanner1.nextLine().strip();
                    System.out.printf("Got: %s%n",message1);
                    if("bye".equals(message1.toLowerCase())){
                        System.out.println("Bye bye");
                        return;
                    }
                }

            }catch (NoSuchElementException ex){
                System.out.printf("Connection dropped!%n");
            }
        }catch (IOException e ){
            var mas = "Can't connect to %s:%s!%n";
            System.out.printf(mas,host,port);

        }
    }
}