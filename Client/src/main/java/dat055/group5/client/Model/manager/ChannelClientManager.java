package dat055.group5.client.Model.manager;

import dat055.group5.client.Driver;
import dat055.group5.client.Model.Model;
import dat055.group5.export.*;

import java.util.List;

public class ChannelClientManager implements ChannelManager <Channel>{
    List<Channel> channels;
    User user;
    Model model;
    Driver driver;
    public ChannelClientManager(Driver driver) {
        this.driver = driver;
    }

    public void login (String userName, String password) {
        this.user = new User(userName, password);
    }

    @Override
    public void addChannel(Channel channel) {
        model.addToChannels(channel);
    }

    @Override
    public void addUserToChannel(String username, int channelId) {
        model.addUserToActiveChannel(username, channelId);
    }

    @Override
    public void removeUserFromChannel(String username, int channelID) {
    }

    @Override
    public List<Channel> getAllChannelsForUser(String username) {
        return List.of();
    }

    @Override
    public List<String> getAllUsersInChannel(int channelID) {
        return List.of();
    }

    @Override
    public void getChannel(Channel channel) {
        model.setActiveChannel(channel);
    }
}