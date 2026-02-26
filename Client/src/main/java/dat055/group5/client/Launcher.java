package dat055.group5.client;
import dat055.group5.client.Model.Client;
import dat055.group5.client.Model.Packer.ChannelClientPacker;
import dat055.group5.client.Model.Packer.MessageClientPacker;
import dat055.group5.client.Model.Packer.UserClientPacker;
import dat055.group5.export.Channel;
import dat055.group5.export.Message;
import javafx.application.Application;

import java.util.List;

import static java.awt.SystemColor.text;

public class Launcher {
    public static void main(String[] args) {
        //Client client = new Client("127.0.0.1", 5000);
        Application.launch(HelloApplication.class, args);
        // address and port are examples
        /*System.out.println("main runs");
        Message message = new Message("user1", "wdawd", 1);
        MessageClientPacker messageClientPacker = new MessageClientPacker();
        ChannelClientPacker channelClientPacker = new ChannelClientPacker();
        UserClientPacker userClientPacker = new UserClientPacker();

        client.sendRequestAsync(messageClientPacker.addMessage(message),
                (_) -> System.out.println("success!!")
        );
        client.sendRequestAsync(messageClientPacker.getMessagesByChannel(1),
                (_) -> System.out.println("success!!")
        );
*/
    }


}
