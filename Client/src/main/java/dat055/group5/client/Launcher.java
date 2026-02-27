package dat055.group5.client;
import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        Driver driver = new Driver();
        HelloApplication.setDriver(driver);
        Application.launch(HelloApplication.class, args);
        //driver.tests();
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
