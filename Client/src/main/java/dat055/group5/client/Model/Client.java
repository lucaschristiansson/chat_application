package dat055.group5.client.Model;
import dat055.group5.client.RequestManager;
import dat055.group5.export.*;
import dat055.group5.client.Model.manager.ChannelClientManager;
import dat055.group5.client.Model.manager.MessageClientManager;
import dat055.group5.client.Model.manager.UserClientManager;
import dat055.group5.export.*;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Client {
    //initialize socket and input/output streams
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Scanner scanner;
    ChannelClientManager channelClientManager;
    MessageClientManager messageClientManager;
    UserClientManager userClientManager;
    private final RequestManager requestManager = new RequestManager();

    public Client(String addr, int port) {
        try {
            socket = new Socket(addr, port);
            if(socket.isConnected()){
                System.out.println("Connected to server at " + addr + ":" + port);

            }else{
                throw new SocketException();
            }

            scanner = new Scanner(System.in);

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            new Thread(new ReadThread(inputStream, requestManager)).start();

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Type your message (or 'Over' to quit):");
        /*
        while (true) {
            if (scanner.hasNextLine()) {
                String username = scanner.nextLine();
                String password = scanner.nextLine();

                User user = new User(username, password);

                    Message msg = new Message("user1", text, 1);
                    NetworkPackage networkPackage = new NetworkPackage(PackageType.CREATE_MESSAGE, msg);

                    outputStream.writeObject(networkPackage);
                    outputStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
                NetworkPackage input = sendRequestBlocking(Actions.AUTH, user);
                if (input != null && (boolean)input.getData()) {
                    Syst ReadThread implements Runnable em.out.println("Login Successful!");
                    break;
                } else {
                    System.out.println("Login Failed.");
                }
            }
        }

        try {
            if (scanner  ReadThread implements Runnable != null) scanner.close();
            if (outputStream != null) outputStream.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

     */
    }

    public void sendNetworkPackage(NetworkPackage networkPackage){
        try {
            outputStream.writeObject(networkPackage);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public NetworkPackage sendRequestBlocking(PackageType type, Object payload) {
        NetworkPackage request = new NetworkPackage(type, payload);
        CompletableFuture<NetworkPackage> future = requestManager.registerRequest(request.getID());
        try {
            outputStream.writeObject(request);
            outputStream.flush();
            return future.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendRequestAsync(NetworkPackage networkPackage, java.util.function.Consumer<NetworkPackage> onSuccess) {


        CompletableFuture<NetworkPackage> future = requestManager.registerRequest(networkPackage.getID());
        future.thenAccept(onSuccess);

        try {
            outputStream.writeObject(networkPackage);
            outputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ReadThread implements Runnable {
        private ObjectInputStream reader;
        private RequestManager requestManager;

        public ReadThread(ObjectInputStream reader, RequestManager requestManager) {
            this.reader = reader;
            this.requestManager = requestManager;
        }

        public void run() {
            try {
                while (true) {
                    Object obj = reader.readObject();
                    if (obj instanceof NetworkPackage networkPackage) {
                        switch (networkPackage.getType()) {
                            case CREATE_CHANNEL: {
                                Channel channel = (Channel) networkPackage.getData();
                                break;
                            }
                            case CREATE_USER: {
                                User user = (User) networkPackage.getData();
                                break;
                            }
                            case CREATE_MESSAGE: {
                                Message message = (Message) networkPackage.getData();
                                break;
                            }
                            case ADD_USER_TO_CHANNEL: {
                                try {
                                    AddUserWithChannel userData = (AddUserWithChannel) networkPackage.getData();
                                    for (String username : userData.getUsernames()) {

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                            case REMOVE_USER_FROM_CHANNEL: {
                                AddUserWithChannel userData = (AddUserWithChannel) networkPackage.getData();
                                for (String username : userData.getUsernames()) {

                                }
                                break;
                            }
                            case GET_CHANNELS_FOR_USER: {
                                Channel channel = (Channel) networkPackage.getData();
                                channelClientManager.getChannel(channel);
                                break;
                            }
                            case GET_MESSAGES_BY_CHANNEL: {
                                List<Message> messages = (List<Message>) networkPackage.getData();
                                break;
                            }
                            case GET_USERS: {

                                break;
                            }
                            case GET_USER_IN_CHANNEL: {
                                Integer channel_id = (Integer) networkPackage.getData();
                                break;
                            }
                            case LOGIN: {

                                //TODO FIX CLIENT LOGIN
                                User user = (User) networkPackage.getData();
                                break;
                            }
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("\nConnection to server lost.");
                e.printStackTrace();
            }
        }
    }
}

