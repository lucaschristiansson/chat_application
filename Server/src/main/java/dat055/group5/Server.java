package dat055.group5;

import dat055.group5.export.User;

import java.net.*;
import java.io.*;

public class Server {

    // Initialize socket and input stream
    private ServerSocket serverSocket = null;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);
        while (true)
            new ClientHandler(serverSocket.accept()).start();
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    private static class ClientHandler extends Thread {
        private final ObjectInputStream input;
        private Socket clientSocket;
        private UserDatabaseManager userDatabaseManager;
        private ChannelDatabaseManager channelDatabaseManager;
        private MessageDatabaseManager messageDatabaseManager;


        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            try {
                this.input = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            while (clientSocket.isConnected()) {

                NetworkPackage networkPackage = null;
                try {
                    networkPackage = (NetworkPackage) input.readObject();

                    /*switch (networkPackage.getType()) {
                        case "addUserToChannel": {
                            try{
                                //
                                User userData = (User) networkPackage.data;
                                userDatabaseManager.addUser(userData);
                            } catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                        case "addUser":{
                            User user = networkPackage.getData();
                            userDatabaseManager.addUser();
                        }

                    }*/
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        }

    }
}

//README
// DETTA SKA ALLTSÅ EVENTUELLT LIGGA I SHARED SÅ ATT VI KAN FORTFARANDE SKICKA OBJEKT AV ALLT I NETWORKPKG
//SÅ EN SÅNHÄR KLASS FÖR ALLA CASES SOM INNEFATTAR FLERA KLASSER
//VAD TRO NI MACUS O EDWINZ?
// i aint reading allat
//TL:DR
// Shit got crazy
 /*
package dat055.group5.shared; // DETTA ÄR FEL???

import java.io.Serializable;

public class ChannelMemberRequest implements Serializable {
    private int userId;
    private int channelId;

    public ChannelMemberRequest(int userId, int channelId) {
        this.userId = userId;
        this.channelId = channelId;
    }

    // Getters
    public int getUserId() { return userId; }
    public int getChannelId() { return channelId; }
}

  */