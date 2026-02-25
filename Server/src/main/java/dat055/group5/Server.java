package dat055.group5;

import dat055.group5.export.*;
import dat055.group5.manager.*;

import java.net.*;
import java.io.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Server {

    // https://www.geeksforgeeks.org/java/collections-synchronizedset-method-in-java-with-examples/
    private final Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());
    private ServerSocket serverSocket = null;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);
        while (true) {
            ClientHandler clientHandler = new ClientHandler(serverSocket.accept(), this);
            clients.add(clientHandler);
            clientHandler.start();
            System.out.println("relooping! thus its not blocking in while loop");
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("Client disconnected.");
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    private static class ClientHandler extends Thread {
        private final ObjectInputStream in;
        private ObjectOutputStream out;
        private final Socket clientSocket;
        private final Server server;
        private boolean verified = false;
        private String username;

        private final UserDatabaseManager userDatabaseManager;
        private final ChannelDatabaseManager channelDatabaseManager;
        private final MessageDatabaseManager messageDatabaseManager;

        public ClientHandler(Socket socket, Server server) {
            this.clientSocket = socket;
            this.server = server;
            try {
                this.in = new ObjectInputStream(socket.getInputStream());
                this.out = new ObjectOutputStream(socket.getOutputStream());

                Driver driver = new Driver();
                this.userDatabaseManager = new UserDatabaseManager(driver);
                this.channelDatabaseManager = new ChannelDatabaseManager(driver);
                this.messageDatabaseManager = new MessageDatabaseManager(driver);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            System.out.println("ClientHandler runs");
            while (!clientSocket.isClosed() && clientSocket.isConnected()) {
                try {
                    Object obj = in.readObject();
                    if (obj instanceof NetworkPackage) {
                        NetworkPackage networkPackage = (NetworkPackage) obj;

                        switch (networkPackage.getType()){
                            case Actions.AUTH -> {
                                User user = (User) networkPackage.getData();

                                NetworkPackage response = new NetworkPackage(Actions.VERIFY, false);

                                response.setID(networkPackage.getID());

                                if(userDatabaseManager.authenticateUser(user)){
                                    username = user.getUsername();
                                    verified = true;
                                    response.setData(true);
                                    System.out.println("User " + username + " authenticated.");
                                } else {
                                    System.out.println("Authentication failed for " + user.getUsername());
                                }
                                sendPackage(response);
                            }
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Client disconnected or error: " + e.getMessage());
                    break;
                }
            }
        }

        public void sendPackage(NetworkPackage message) {
            try {
                synchronized (out) {
                    out.writeObject(message);
                    out.flush();
                    out.reset();
                }
            } catch (IOException e) {
                System.err.println("Error sending message to client: " + e.getMessage());
            }
        }
    }

}
