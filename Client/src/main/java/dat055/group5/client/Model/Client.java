package dat055.group5.client.Model;
import dat055.group5.client.Driver;
import dat055.group5.client.RequestManager;
import dat055.group5.export.*;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Ansvarar för
 */
public class Client {
    private final RequestManager requestManager;
    private final Model model;
    private final Driver driver;

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private java.util.function.Consumer<Message> messageListener;

    /**
     *
     * @param driver
     * @param addr
     * @param port
     */


    public Client(Driver driver, String addr, int port) {
        this.driver = driver;
        this.requestManager = driver.getRequestManager();
        this.model = driver.getModel();

        try {
            socket = new Socket(addr, port);
            if(socket.isConnected()){
                System.out.println("Connected to server at " + addr + ":" + port);

            }else{
                throw new SocketException();
            }

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            new Thread(new ReadThread(inputStream, requestManager)).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMessageListener(java.util.function.Consumer<Message> listener) {
        this.messageListener = listener;
    }

    public final String getUsername(){
        return model.getClientUser().getUsername();
    }

    public Channel getActiveChannel(){
        return this.model.getActiveChannel();
    }

    public void sendMessagePackage(String content){
        sendRequestAsync(
                driver.getMessageClientPacker().addMessage(
                        new Message(getUsername(), content, getActiveChannel().getChannelID())
                ),
                (networkPackage) -> {
                    System.out.println(networkPackage.getData());
                    if(networkPackage.getType() == PackageType.CREATE_MESSAGE){
                        model.addMessage((Message) networkPackage.getData());
                    }
                }
        );
    }
    //TODO NetworkPackages ska flyttas till specifika metoder i Managers
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
            sendNetworkPackage(request);
            return future.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendRequestAsync(NetworkPackage networkPackage, java.util.function.Consumer<NetworkPackage> onSuccess) {
        System.out.println("sendreqasync: " + networkPackage.getData().toString());
        CompletableFuture<NetworkPackage> future = requestManager.registerRequest(networkPackage.getID());
        future.thenAccept(onSuccess);

        try {
            sendNetworkPackage(networkPackage);

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
                        System.out.println(networkPackage.getType() + " and " + networkPackage.getData().toString());
                        boolean isResponse = requestManager.completeRequest(networkPackage.getID(), networkPackage);

                        if(!isResponse){
                            switch (networkPackage.getType()){
                                case PackageType.CREATE_MESSAGE -> {
                                    if (Client.this.messageListener != null && networkPackage.getData() instanceof Message) {
                                        Client.this.messageListener.accept((Message) networkPackage.getData());
                                    }
                                }
                            }
                        }

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
                                model.addMessage(message);
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
                                // Channel channel = (Channel) networkPackage.getData();
                                // channelClientManager.getChannel(channel);
                                break;
                            }
                            case GET_MESSAGES_BY_CHANNEL: {
                                List<Message> messages = (List<Message>) networkPackage.getData();
                                break;
                            }
                            case GET_USERS: {

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

/**
 * add images support
 * add more javadocs
 * add channels
 *
 *
 * after that we can add functionality for new users
 */