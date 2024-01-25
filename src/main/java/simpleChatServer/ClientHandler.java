package simpleChatServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private BufferedReader reader;
    private Socket socket;
    private PrintWriter writer;

    public ClientHandler(Socket clientSocket, PrintWriter writer) { // По созданному соединению получили поток и прочитали в буфферед ридер
        try {
            socket = clientSocket;
            this.writer = writer;
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(inputStreamReader);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        System.out.println("Стартовал поток " + Thread.currentThread().getName());
        String message;
        try {
            while ((message = reader.readLine()) != null) { // пока сообщния есть, читаем
                System.out.println("Read " + message);
                returnTheMessage(message);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void returnTheMessage(String message) {
        try {
            writer.println(message);
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
