package master;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    static Vector<ClientHandler> connectedClients = new Vector<>();
    static int clientCount = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Server started. Listening for clients...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected: " + clientSocket);
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

            ClientHandler clientHandler = new ClientHandler(clientSocket, "client " + clientCount, dis, dos);
            connectedClients.add(clientHandler);
            clientHandler.start();

            clientCount++;
        }
    }
}

class ClientHandler extends Thread {
    private final Socket clientSocket;
    private final String clientName;
    private final DataInputStream dis;
    private final DataOutputStream dos;

    public ClientHandler(Socket clientSocket, String clientName, DataInputStream dis, DataOutputStream dos) {
        this.clientSocket = clientSocket;
        this.clientName = clientName;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String receivedMessage = dis.readUTF();
                System.out.println(clientName + ": " + receivedMessage);

                // Forward the message to other clients
                for (ClientHandler otherClient : Server.connectedClients) {
                    if (!otherClient.clientName.equals(clientName)) {
                        otherClient.dos.writeUTF(clientName + ": " + receivedMessage);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
