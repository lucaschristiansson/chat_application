package dat055.group5.client.Model;
import java.io.*;
import java.net.*;

public class Client {
    //initialize socket and input/output streams
    private Socket s = null;
    private BufferedReader d = null;
    private DataOutputStream out = null;

    public Client(String addr, int port){
        //Establish a connection
        try {
            s = new Socket(addr, port);
            System.out.println("Connected");

            // Takes input from terminal
            d = new BufferedReader(new InputStreamReader(System.in));

            // Sends output to the socket
            out = new DataOutputStream(s.getOutputStream());
        } catch (IOException i) {
            System.out.println(i);
            return;
        }

        //String to read message from input
        String m = "";

        // Keep reading until "Over" is input
        while (!m.equals("Over")) {
            try {
                m = d.readLine();
                out.writeUTF(m);
            }
            catch (IOException e) {
                System.out.println(e);
                break;
            }
        }

        //close the connection
        try{
            d.close();
            out.close();
            s.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

