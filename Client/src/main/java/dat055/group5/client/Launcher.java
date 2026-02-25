package dat055.group5.client;
import dat055.group5.client.Model.Client;
import dat055.group5.client.Model.Packer.MessageClientPacker;
import dat055.group5.export.Message;
import javafx.application.Application;

import static java.awt.SystemColor.text;

public class Launcher {
    public static void main(String[] args) {
        //Application.launch(HelloApplication.class, args);
        // address and port are examples
        System.out.println("main runs");
        Client client = new Client("127.0.0.1", 5000);
        Message message = new Message("user1", "wdawd", 1);
        MessageClientPacker messageClientPacker = new MessageClientPacker();
        client.sendNetworkPackage(messageClientPacker.addMessage(message));
    }


}
