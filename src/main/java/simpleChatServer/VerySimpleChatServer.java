package simpleChatServer;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class VerySimpleChatServer {

    final int port;

    public VerySimpleChatServer(int port) {
        this.port = port;
    }

    public void go() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                Thread t = new Thread(new ClientHandler(clientSocket, writer));
                t.start();
                System.out.println("got a connection");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
