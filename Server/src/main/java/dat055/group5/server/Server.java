package dat055.group5.server;

import dat055.group5.Driver;
import dat055.group5.export.*;

import java.net.*;
import java.io.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Server extends Thread{

    // https://www.geeksforgeeks.org/java/collections-synchronizedset-method-in-java-with-examples/
    private final Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());
    private ServerSocket serverSocket = null;
    private final Driver driver;

    public Server(int port, Driver driver) throws IOException{
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);
        this.driver = driver;
    }

    @Override
    public void run() {
        while (true) {
            ClientHandler clientHandler = null;
            try {
                clientHandler = new ClientHandler(serverSocket.accept(), this, driver);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            clients.add(clientHandler);
            clientHandler.start();
            System.out.println("relooping! thus its not blocking in while loop");
        }
    }

    public void broadcast(NetworkPackage networkPackage) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                client.sendPackage(networkPackage);
                System.out.println(client);
            }
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("Client disconnected.");
    }

}
