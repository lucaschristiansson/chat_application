package dat055.group5.client.Model;

import dat055.group5.export.User;
import dat055.group5.export.Channel;
import dat055.group5.export.ChannelManager;

import java.util.List;

public class ChannelClientManager implements ChannelManager {
    List<Channel> channels;
    User user;

    public ChannelClientManager(User user, List<Channel> channels) {
        this.user = user;
        this.channels = channels;
    }

    public void login (String userName, String password) {
        this.user = new User(userName, password);
    }

    public void fetchChannels() {
        //this.channels = "From server"
    }

    @Override
    public boolean addChannel(Channel channel) {
        
    }

    @Override
    public boolean addUserToChannel(String username, int ChannelId) {
        return false;
    }

    @Override
    public boolean removeUserFromChannel(String username, int channelID) {
        return false;
    }

    @Override
    public List<Channel> getAllChannelsForUser(String username) {
        return List.of();
    }

    @Override
    public List<String> getAllUsersInChannel(int channelID) {
        return List.of();
    }
}