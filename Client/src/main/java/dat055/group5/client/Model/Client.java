package dat055.group5.client.Model;
import dat055.group5.export.Message;
import dat055.group5.export.NetworkPackage;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    //initialize socket and input/output streams
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Scanner scanner;

    public Client(String addr, int port) {
        try {
            socket = new Socket(addr, port);
            System.out.println("Connected to server at " + addr + ":" + port);

            scanner = new Scanner(System.in);

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            new Thread(new ReadThread(inputStream)).start();

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Type your message (or 'Over' to quit):");

        while (true) {
            try {
                if (scanner.hasNextLine()) {
                    String text = scanner.nextLine();

                    if (text.equals("Over")) {
                        break;
                    }

                    Message msg = new Message("user1", text, 1);
                    NetworkPackage networkPackage = new NetworkPackage("CreateMessage", msg);

                    outputStream.writeObject(networkPackage);
                    outputStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
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

    private class ReadThread implements Runnable {
        private ObjectInputStream reader;

        public ReadThread(ObjectInputStream reader) {
            this.reader = reader;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Object obj = reader.readObject();

                    if (obj instanceof Message) {
                        Message msg = (Message) obj;
                        System.out.println("\n[" + msg.getSender() + "]: " + msg.getContent());
                        System.out.print("> ");
                    }
                    else if (obj instanceof String) {
                        System.out.println("\n[Server]: " + obj);
                        System.out.print("> ");
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("\nConnection to server lost.");
            }
        }
    }
}

