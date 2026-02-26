package dat055.group5.client;
import dat055.group5.client.Model.Client;
import dat055.group5.client.Model.Packer.ChannelClientPacker;
import dat055.group5.client.Model.Packer.MessageClientPacker;
import dat055.group5.client.Model.Packer.UserClientPacker;
import dat055.group5.export.Channel;
import dat055.group5.export.Message;
import dat055.group5.export.User;
import javafx.application.Application;

import java.util.List;

import static java.awt.SystemColor.text;

public class Launcher {
    //private final RequestManager requestManager = new RequestManager();

    public static void main(String[] args) {
        //Application.launch(HelloApplication.class, args);
        // address and port are examples
        ClientDriver driver = new ClientDriver();
        driver.tests();





    }


}
