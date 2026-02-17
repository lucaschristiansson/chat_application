package dat055.group5.client.Model;
import java.io.*;
import java.net.*;

public class Client {
    //initialize socket and input/output streams
    private Socket socket = null;
    private BufferedReader bufferReader = null;
    private DataOutputStream outputStream = null;

    public Client(String addr, int port){
        //Establish a connection
        try {
            socket = new Socket(addr, port);
            System.out.println("Connected");

            // Takes input from terminal
            bufferReader = new BufferedReader(new InputStreamReader(System.in));

            // Sends output to the socket
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        //String to read message from input
        String message = "";

        // Keep reading until "Over" is input
        while (!message.equals("Over")) {
            try {
                message = bufferReader.readLine();
                outputStream.writeObject(message);
            }
            catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

        //close the connection
        try{
            bufferReader.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

