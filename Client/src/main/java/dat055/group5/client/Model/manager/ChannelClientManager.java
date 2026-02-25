package dat055.group5.client.Model.manager;

import dat055.group5.client.Model.Model;
import dat055.group5.export.User;
import dat055.group5.export.Channel;
import dat055.group5.export.ChannelManager;

import java.util.List;

public class ChannelClientManager implements ChannelManager <Channel>{
    List<Channel> channels;
    User user;
    Model model;

    public ChannelClientManager(User user, List<Channel> channels) {
        this.user = user;
        this.channels = channels;
    }

    public void login (String userName, String password) {
        this.user = new User(userName, password);
    }

    @Override
    public void addChannel(Channel channel) {
        model.addToChannelList(channel);
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