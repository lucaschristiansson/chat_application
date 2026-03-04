package dat055.group5.client;

import dat055.group5.client.Model.Client;
import dat055.group5.client.Model.Model;
import dat055.group5.client.Model.Packer.ChannelClientPacker;
import dat055.group5.client.Model.Packer.MessageClientPacker;
import dat055.group5.client.Model.Packer.UserClientPacker;
import dat055.group5.client.Model.manager.ChannelClientManager;
import dat055.group5.client.Model.manager.MessageClientManager;
import dat055.group5.client.Model.manager.UserClientManager;

import java.util.List;

public class Driver {
    private final RequestManager requestManager;
    private final Client client;
    private final MessageClientPacker messageClientPacker = new MessageClientPacker();
    private final ChannelClientPacker channelClientPacker = new ChannelClientPacker();
    private final UserClientPacker userClientPacker = new UserClientPacker();
    private final ChannelClientManager channelClientManager;
    private final MessageClientManager messageClientManager;
    private final UserClientManager userClientManager;
    private final Model model;

    @SuppressWarnings("unchecked")
    public Driver(){
        this.requestManager = new RequestManager();
        this.model = new Model(null, null); //TODO FETCH!!!!
        this.client = new Client(this, "127.0.0.1", 4000);
        this.channelClientManager = new ChannelClientManager(this);
        this.messageClientManager = new MessageClientManager(this);
        this.userClientManager = new UserClientManager(this);



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

    public ChannelClientManager getChannelClientManager() {
        return channelClientManager;
    }

    public MessageClientManager getMessageClientManager() {
        return messageClientManager;
    }

    public UserClientManager getUserClientManager() {
        return userClientManager;
    }
}
