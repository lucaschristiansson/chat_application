package dat055.group5.server;

import dat055.group5.Driver;
import dat055.group5.export.*;
import dat055.group5.manager.ChannelDatabaseManager;
import dat055.group5.manager.MessageDatabaseManager;
import dat055.group5.manager.UserDatabaseManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread{
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

    @Override
    public void run() {
        System.out.println("ClientHandler runs");
        while (!clientSocket.isClosed() && clientSocket.isConnected()) {
            try {
                Object obj = in.readObject();
                /*TODO*/
                // Create a enum for the different types of network packages
                if (obj instanceof NetworkPackage networkPackage) {
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
                            List<Message> messages = messageDatabaseManager.getMessagesByChannel(channelID);
                            NetworkPackage sendNetworkPackage = driver.getMessageServerPacker().getMessagesByChannel(messages);
                            sendPackage(sendNetworkPackage);
                            break;
                        }
                        case GET_USERS: {
                            userDatabaseManager.getUsers();
                            break;
                        }
                        case GET_USER_IN_CHANNEL: {
                            Integer channel_id = (Integer) networkPackage.getData();
                            List<String> messages = channelDatabaseManager.getAllUsersInChannel(channel_id);
                            break;
                        }
                        case LOGIN: {
                            User user = (User) networkPackage.getData();

                            NetworkPackage response = new NetworkPackage(PackageType.VERIFY, false);

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
        server.removeClient(this);
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