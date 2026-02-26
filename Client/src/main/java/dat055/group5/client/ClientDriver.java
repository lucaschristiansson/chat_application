package dat055.group5.client;

import dat055.group5.client.Model.Client;
import dat055.group5.client.Model.Model;
import dat055.group5.client.Model.Packer.ChannelClientPacker;
import dat055.group5.client.Model.Packer.MessageClientPacker;
import dat055.group5.client.Model.Packer.UserClientPacker;
import dat055.group5.export.Message;
import dat055.group5.export.User;

public class ClientDriver {
    private final RequestManager requestManager;
    private final Client client;
    private final MessageClientPacker messageClientPacker = new MessageClientPacker();
    private final ChannelClientPacker channelClientPacker = new ChannelClientPacker();
    private final UserClientPacker userClientPacker = new UserClientPacker();
    private final Model model;

    public ClientDriver(){
        this.requestManager = new RequestManager();
        this.client = new Client(this, "127.0.0.1", 5000);
        this.model = new Model(null); //TODO FETCH!!!!

    }


    public void tests(){
        Message unverified = new Message("user1", "unverifiedmsg", 1);
        Message verified = new Message("user1", "verifiedmsg", 1);


        client.sendRequestAsync(messageClientPacker.addMessage(unverified), //should not work
                (networkPackage) -> System.out.println(networkPackage.getData())
        );

        client.sendRequestAsync(userClientPacker.authenticateUser(new User("user1", "password1")),
                (networkPackage -> {} ));

        client.sendRequestAsync(messageClientPacker.addMessage(verified), //should work
                (networkPackage) -> System.out.println(networkPackage.getData())
        );
        client.sendRequestAsync(messageClientPacker.getMessagesByChannel(1),
                (networkPackage) -> System.out.println(networkPackage.getType()));


    }

    public RequestManager getRequestManager() {
        return requestManager;
    }

    public Client getClient() {
        return client;
    }

    public MessageClientPacker getMessageClientPacker() {
        return messageClientPacker;
    }

    public ChannelClientPacker getChannelClientPacker() {
        return channelClientPacker;
    }

    public UserClientPacker getUserClientPacker() {
        return userClientPacker;
    }

    public Model getModel() {
        return model;
    }
}
