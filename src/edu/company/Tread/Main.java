package edu.company.Tread;

public class Main {

    public static void main(String[] args) {

        EchoServer echoServer = new EchoServer(8787);
        echoServer.run();
    }


}
