package dat055.group5;

import dat055.group5.Packer.MessageServerPacker;
import dat055.group5.export.*;
import dat055.group5.manager.*;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Server for chat-application
 * Class run on separate thread, handles client connections on ports.
 * Instantiates {@link ClientHandler} for every new connection.
 */
public class Server extends Thread{

    private final Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());
    private ServerSocket serverSocket = null;
    private final Driver driver;

    /**
     * Initiates server and binds it to specific port.
     * @param port TCP port that the server listens to.
     * @param driver handles database-connections
     * @throws IOException if server fails to start
     */
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

    /**
     * NO USAGES ??
     * @param networkPackage
     */
    public void broadcast(NetworkPackage networkPackage) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                client.sendPackage(networkPackage);
                System.out.println(client);
            }
        }
    }

    /**
     * Sends package to all currently connected clients to handled channel.
     * @param networkPackage the sent object
     * @param channelID Identifier for the handled channel
     */
    public void broadcastToChannel(NetworkPackage networkPackage, Integer channelID) {

        Collection<String> usersInChannel = driver.getChannelDatabaseManager().getAllUsersInChannel(channelID);

        Set<String> channelUsersSet = new HashSet<>(usersInChannel);

        List<ClientHandler> targetClients = new ArrayList<>();

        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (channelUsersSet.contains(client.username)) {
                    targetClients.add(client);
                }
            }
        }

        for (ClientHandler target : targetClients) {
            target.sendPackage(networkPackage);
        }
    }

    /**
     * Removes client-connection
     * @param client
     */
    public void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("Client disconnected.");
    }


    /**
     * Handles communication with clients, instantiated for each client.
     * Handles incoming packages and validates user against database
     */
    private static class ClientHandler extends Thread {
        private final ObjectInputStream in;
        private ObjectOutputStream out;
        private final Socket clientSocket;
        private final Server server;
        private boolean verified = false;
        private String username;

        private final Driver driver;
        private final UserDatabaseManager userDatabaseManager;
        private final ChannelDatabaseManager channelDatabaseManager;
        private final MessageDatabaseManager messageDatabaseManager;

        /**
         * Instantiates new ClientHandler and sets up input and outputstreams.
         * @param socket
         * @param server
         * @param driver
         * @throws RuntimeException if initialization fails
         */
        public ClientHandler(Socket socket, Server server, Driver driver) {
            this.clientSocket = socket;
            this.server = server;
            this.driver = driver;
            try {
                this.in = new ObjectInputStream(socket.getInputStream());
                this.out = new ObjectOutputStream(socket.getOutputStream());

                this.userDatabaseManager = driver.getUserDatabaseManager();
                this.channelDatabaseManager = driver.getChannelDatabaseManager();
                this.messageDatabaseManager = driver.getMessageDatabaseManager();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Main loop for clienthandler, runs in a separate thread.
         * Method listens for {@link NetworkPackage}-objects arriving from clients while connection is established
         * and arriving object is not of type Login
         * Checks what type of package is sent and calls the designated methods for each case.
         * Handles connectivity-issues.
         */
        @Override
        public void run() {
            System.out.println("ClientHandler runs");
            while (!clientSocket.isClosed() && clientSocket.isConnected()) {
                try {
                    Object obj = in.readObject();

                    if (obj instanceof NetworkPackage networkPackage) {
                        if(!verified && !networkPackage.getType().equals(PackageType.LOGIN)
                                && !networkPackage.getType().equals(PackageType.CREATE_USER)) {
                            continue;
                        }
                        switch (networkPackage.getType()) {
                            case CREATE_CHANNEL: {
                                Channel channel = (Channel) networkPackage.getData();
                                Channel created = channelDatabaseManager.addChannel(channel);
                                NetworkPackage response = new NetworkPackage(
                                        networkPackage.getID(), PackageType.CREATE_CHANNEL, created);
                                sendPackage(response);
                                break;
                            }
                            case CREATE_USER: {
                                User user = (User) networkPackage.getData();
                                boolean success = userDatabaseManager.addUser(user);
                                NetworkPackage response = new NetworkPackage(
                                        networkPackage.getID(), PackageType.CREATE_USER, success);
                                sendPackage(response);
                                break;
                            }
                            case CREATE_MESSAGE: {
                                Message message = (Message) networkPackage.getData();
                                messageDatabaseManager.addMessage(message);
                                server.broadcastToChannel(networkPackage, message.getChannel());
                                break;
                            }
                            case ADD_USER_TO_CHANNEL: {
                                try {
                                    AddUserWithChannel userData = (AddUserWithChannel) networkPackage.getData();
                                    for (String u : userData.getUsernames()) {
                                        channelDatabaseManager.addUserToChannel(u, userData.getChannelID());
                                    }
                                    Channel addedChannel = channelDatabaseManager.getChannelById(userData.getChannelID());
                                    if (addedChannel != null) {
                                        NetworkPackage push = new NetworkPackage(PackageType.CHANNEL_ADDED, addedChannel);
                                        synchronized (server.clients) {
                                            for (ClientHandler c : server.clients) {
                                                if (userData.getUsernames().contains(c.username)) {
                                                    c.sendPackage(push);
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                            case REMOVE_USER_FROM_CHANNEL: {
                                AddUserWithChannel userData = (AddUserWithChannel) networkPackage.getData();
                                for (String username : userData.getUsernames()) {
                                    channelDatabaseManager.removeUserFromChannel(username, userData.getChannelID());
                                }
                                break;
                            }
                            case GET_CHANNELS_FOR_USER: {
                                String user = (String) networkPackage.getData();
                                NetworkPackage response = new NetworkPackage(
                                        networkPackage.getID(),
                                        PackageType.GET_CHANNELS_FOR_USER,
                                        channelDatabaseManager.getAllChannelsForUser(user));
                                sendPackage(response);
                                break;
                            }
                            case GET_MESSAGES_BY_CHANNEL: {
                                Integer channelID = (Integer) networkPackage.getData();
                                NetworkPackage response = new NetworkPackage(
                                        networkPackage.getID(),
                                        PackageType.GET_MESSAGES_BY_CHANNEL,
                                        messageDatabaseManager.getMessagesByChannel(channelID));
                                sendPackage(response);
                                break;
                            }
                            case GET_USERS: {
                                NetworkPackage response = new NetworkPackage(
                                        networkPackage.getID(), PackageType.GET_USERS,
                                        userDatabaseManager.getAllUsers());
                                sendPackage(response);
                                break;
                            }

                            case GET_USER_IN_CHANNEL:
                            case GET_CHANNEL: {
                                Integer channelID = (Integer) networkPackage.getData();
                                NetworkPackage response = new NetworkPackage(
                                        networkPackage.getID(),
                                        networkPackage.getType(),
                                        channelDatabaseManager.getAllUsersInChannel(channelID));
                                sendPackage(response);
                                break;
                            }
                            case LOGIN: {
                                User user = (User) networkPackage.getData();

                                NetworkPackage response = new NetworkPackage(networkPackage.getID(), PackageType.VERIFY, false);

                                if (userDatabaseManager.authenticateUser(user)) {
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
            server.removeClient(this);
        }

        /**
         * Sends {@link NetworkPackage} to connected client
         * Method is synchronized and buffer is cleared after sending to allow new sends.
         * @param message that is to be sent as a package
         * @throws IOException in case of connectivity-issues.
         */
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
