package dat055.group5;

import dat055.group5.export.*;
import dat055.group5.manager.*;

import java.net.*;
import java.io.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Server extends Thread{

    // https://www.geeksforgeeks.org/java/collections-synchronizedset-method-in-java-with-examples/
    private final Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());
    private ServerSocket serverSocket = null;


    public Server(int port) throws IOException{
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);
    }

    @Override
    public void run() {
        while (true) {
            ClientHandler clientHandler = null;
            try {
                clientHandler = new ClientHandler(serverSocket.accept(), this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            clients.add(clientHandler);
            clientHandler.start();
            System.out.println("relooping! thus its not blocking in while loop");
        }
    }

    public void broadcast(Message message) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                client.sendMessage(message);
                System.out.println(client);
            }
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("Client disconnected.");
    }



    private static class ClientHandler extends Thread {
        private final ObjectInputStream in;
        private ObjectOutputStream out;
        private final Socket clientSocket;
        private final Server server;

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
                    /*TODO*/
                    // Create a enum for the different types of network packages
                    if (obj instanceof NetworkPackage) {
                        NetworkPackage networkPackage = (NetworkPackage) obj;
                        switch (networkPackage.getType()) {
                            case CREATE_CHANNEL: {
                                Channel channel = (Channel) networkPackage.getData();
                                channelDatabaseManager.addChannel(channel);
                                break;
                            }
                            case CREATE_USER: {
                                User user = (User) networkPackage.getData();
                                userDatabaseManager.addUser(user);
                                break;
                            }
                            case CREATE_MESSAGE: {
                                Message message = (Message) networkPackage.getData();
                                messageDatabaseManager.addMessage(message);
                                break;
                            }
                            case ADD_USER_TO_CHANNEL: {
                                try{
                                    AddUserWithChannel userData = (AddUserWithChannel) networkPackage.getData();
                                    for(String username : userData.getUsernames()){
                                        channelDatabaseManager.addUserToChannel(username, userData.getChannelID());
                                    }
                                } catch(Exception e){
                                    e.printStackTrace();
                                }
                                break;
                            }
                            case REMOVE_USER_FROM_CHANNEL: {
                                AddUserWithChannel userData = (AddUserWithChannel) networkPackage.getData();
                                for(String username : userData.getUsernames()){
                                    channelDatabaseManager.removeUserFromChannel(username, userData.getChannelID());
                                }
                                break;
                            }
                            case GET_CHANNELS_FOR_USER: {
                                String username = (String) networkPackage.getData();
                                channelDatabaseManager.getAllChannelsForUser(username);
                                break;
                            }
                            case GET_MESSAGES_BY_CHANNEL: {
                                Integer channelID = (Integer) networkPackage.getData();
                                messageDatabaseManager.getMessagesByChannel(channelID);
                                break;
                            }
                            case GET_USERS: {
                                userDatabaseManager.getUsers();
                                break;
                            }
                            case GET_USER_IN_CHANNEL: {
                                Integer channel_id = (Integer) networkPackage.getData();
                                channelDatabaseManager.getAllUsersInChannel(channel_id);
                                break;
                            }
                            case LOGIN: {
                                User user = (User) networkPackage.getData();
                                userDatabaseManager.authenticateUser(user);
                                break;
                            }
                        }
                    }



                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Client disconnected or error: " + e.getMessage());
                    break;
                }
            }
            server.removeClient(this);
        }

        public void sendMessage(Message message) {
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
