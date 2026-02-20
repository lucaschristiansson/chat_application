package dat055.group5;

import dat055.group5.export.*;
import dat055.group5.manager.*;

import java.net.*;
import java.io.*;

public class Server {

    private ServerSocket serverSocket = null;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);
        while (true) {
            new ClientHandler(serverSocket.accept()).start();
            System.out.println("relooping! thus its not blocking in while loop");
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    private static class ClientHandler extends Thread {
        private final ObjectInputStream input;
        private final Socket clientSocket;

        private final UserDatabaseManager userDatabaseManager;
        private final ChannelDatabaseManager channelDatabaseManager;
        private final MessageDatabaseManager messageDatabaseManager;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            try {
                this.input = new ObjectInputStream(socket.getInputStream());

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
                    NetworkPackage networkPackage = (NetworkPackage) input.readObject();

                    switch (networkPackage.getType()) {
                        case "CreateChannel" : {
                            Channel channel = (Channel) networkPackage.getData();
                            channelDatabaseManager.addChannel(channel);
                        }
                        case "CreateUser":{
                            User user = (User) networkPackage.getData();
                            userDatabaseManager.addUser(user);
                        }
                        case "CreateMessage" : {
                            Message message = (Message) networkPackage.getData();
                            messageDatabaseManager.addMessage(message);
                        }
                        case "AddUserToChannel" : {
                            try{
                                AddUserWithChannel userData = (AddUserWithChannel) networkPackage.data;
                                for(String username : userData.getUsernames()){
                                    channelDatabaseManager.addUserToChannel(username, userData.getChannelID());
                                }
                            } catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                        case "RemoveUserFromChannel" : {
                            AddUserWithChannel userData = (AddUserWithChannel) networkPackage.data;
                            for(String username : userData.getUsernames()){
                                channelDatabaseManager.removeUserFromChannel(username, userData.getChannelID());
                            }
                        }
                        case "GetChannels" : {
                            String username = (String) networkPackage.getData();
                            channelDatabaseManager.getAllChannelsForUser(username);
                        }
                        case "GetMessages" : {
                            Integer channelID = (Integer) networkPackage.getData();
                            messageDatabaseManager.getMessagesByChannel(channelID);
                        }
                        case "GetUsers" : {
                            userDatabaseManager.getUsers();
                        }
                        case "GetUsersInChannel" : {
                            Integer channel_id = (Integer) networkPackage.getData();
                            channelDatabaseManager.getAllUsersInChannel(channel_id);
                        }
                        case "Login" : {
                            User user = (User) networkPackage.getData();
                            userDatabaseManager.authenticateUser(user);
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Client disconnected or error: " + e.getMessage());
                    break;
                }
            }
        }
    }

}
