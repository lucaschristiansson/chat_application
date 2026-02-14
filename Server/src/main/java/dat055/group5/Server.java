package dat055.group5;

import java.net.*;
import java.io.*;

public class Server {

    // Initialize socket and input stream
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private DataInputStream inputStream = null;

    // Constructor with port
    public Server(int port) {

        // Starts server and waits for a connection
        try
        {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket = serverSocket.accept();
            System.out.println("Client accepted");

            // Takes input from the client socket
            inputStream = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));

            String message = "";

            // Reads message from client until "Over" is sent
            while (!message.equals("Over"))
            {
                try
                {
                    message = inputStream.readUTF();
                    System.out.println(message);

                }
                catch(IOException i)
                {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");

            // Close connection
            socket.close();
            inputStream.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }


}