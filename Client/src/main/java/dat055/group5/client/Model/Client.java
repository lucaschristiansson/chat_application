package dat055.group5.client.Model;
import dat055.group5.client.RequestManager;
import dat055.group5.export.*;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Client {

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Scanner scanner;
    private final RequestManager requestManager = new RequestManager();

    public Client(String addr, int port) {
        try {
            socket = new Socket(addr, port);
            System.out.println("Connected to server at " + addr + ":" + port);

            scanner = new Scanner(System.in);

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            new Thread(new ReadThread(inputStream, requestManager)).start();

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Type your message (or 'Over' to quit):");

        while (true) {
            if (scanner.hasNextLine()) {
                String username = scanner.nextLine();
                String password = scanner.nextLine();

                User user = new User(username, password);

                NetworkPackage input = sendRequestBlocking(Actions.AUTH, user);
                if (input != null && (boolean)input.getData()) {
                    System.out.println("Login Successful!");
                    break;
                } else {
                    System.out.println("Login Failed.");
                }
            }
        }

        try {
            if (scanner != null) scanner.close();
            if (outputStream != null) outputStream.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public NetworkPackage sendRequestBlocking(Actions type, Object payload) {
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

    public void sendRequestAsync(Actions type, Object data, java.util.function.Consumer<NetworkPackage> onSuccess) {
        NetworkPackage request = new NetworkPackage(type, data);

        CompletableFuture<NetworkPackage> future = requestManager.registerRequest(request.getID());
        future.thenAccept(response -> onSuccess.accept(response));

        try {
            outputStream.writeObject(request);
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

        @Override
        public void run() {
            try {
                while (true) {
                    Object obj = reader.readObject();

                    if (obj instanceof NetworkPackage) {
                        NetworkPackage msg = (NetworkPackage) obj;

                        boolean isResponse = requestManager.completeRequest(msg.getID(), msg);
                    }
                    else if (obj instanceof Message) {
                        Message msg = (Message) obj;
                        System.out.println("\n[" + msg.getSender() + "]: " + msg.getContent());
                        System.out.print("> ");
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("\nConnection to server lost.");
            }
        }
    }
}

