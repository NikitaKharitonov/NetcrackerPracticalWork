package net.server.parallel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class BinaryServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        //ArrayList<BinaryClientThread> threads = new ArrayList<>();
        int id = 0;
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Connected to the client");
            BinaryClientThread thread = new BinaryClientThread(socket);
            System.out.println("Created the client thread with the id " + id++);
            thread.start();
            //threads.add(thread);
        }

    }
}
