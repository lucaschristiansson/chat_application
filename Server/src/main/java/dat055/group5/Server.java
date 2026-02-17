package dat055.group5;

import java.net.*;
import java.io.*;

import dat055.group5.export.NetworkPackage;

public class Server {

    // Initialize socket and input stream
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

        //@Override
        public void run() {
            System.out.println("ClientHandler runs");
            while (clientSocket.isConnected()) {
                NetworkPackage networkPackage = null;
                try {
                    String message = ((String) input.readObject());
                    synchronized (System.out) {
                        System.out.println(message);
                        System.out.flush();
                    }
                    //networkPackage = (NetworkPackage) input.readObject();

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
                    //dont care
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
            //after while
            try{
                //input.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
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