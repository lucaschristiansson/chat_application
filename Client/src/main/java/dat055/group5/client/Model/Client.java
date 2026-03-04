package dat055.group5.client.Model;
import dat055.group5.client.Driver;
import dat055.group5.client.RequestManager;
import dat055.group5.export.*;
import javafx.scene.image.Image;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

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

    public final String getUsername(){
        return model.getClientUser().getUsername();
    }

    public Channel getActiveChannel(){
        return this.model.getActiveChannel();
    }

    public void sendMessagePackage(String content, List<Image> images){
        List<byte[]> imageBytesList = new ArrayList<>();

        /* Used an AI agent here to understand how to convert JavaFX Image to buffer so that you can write it over the raw sockets*/
        if (images != null && !images.isEmpty()) {
            for (Image fxImage : images) {
                try {
                    BufferedImage bImage = SwingFXUtils.fromFXImage(fxImage, null);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    ImageIO.write(bImage, "png", stream);
                    imageBytesList.add(stream.toByteArray());
                } catch (IOException e) {
                    System.err.println("Failed to convert image: " + e.getMessage());
                }
            }
        }

        Message outgoingMessage = new Message(getUsername(), content, getActiveChannel().getChannelID(), imageBytesList);

        sendRequestAsync(
                driver.getMessageClientPacker().addMessage(outgoingMessage), (networkPackage) ->
                    driver.getMessageClientManager().addMessage((Message) networkPackage.getData())
        );
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
            sendNetworkPackage(request);
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
            sendNetworkPackage(networkPackage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ReadThread implements Runnable {
        private final ObjectInputStream reader;
        private final RequestManager requestManager;

        public ReadThread(ObjectInputStream reader, RequestManager requestManager) {
            this.reader = reader;
            this.requestManager = requestManager;
        }

        public void run() {
            try {
                while (true) {
                    Object obj = reader.readObject();
                    if (obj instanceof NetworkPackage networkPackage) {
                        boolean isResponse = requestManager.completeRequest(networkPackage.getID(), networkPackage);

                        if(!isResponse){
                            switch (networkPackage.getType()){
                                case PackageType.CREATE_MESSAGE -> {
                                    driver.getModel().addMessage((Message) networkPackage.getData());
                                }
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
 * add more javadocs
 * add channels
 *
 *
 * after that we can add functionality for new users
 */