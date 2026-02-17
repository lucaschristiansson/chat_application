package dat055.group5.client.Model;

import dat055.group5.export.User;
import dat055.group5.export.Channel;

import java.util.List;

public class ChannelManager {
    List<Channel> channels;
    User user;

    public ChannelManager(User user, List<Channel> channels) {
        this.user = user;
        this.channels = channels;
    }

    public void login (String userName, String password) {
        this.user = new User(userName, password);
    }

    public void fetchChannels() {
        //this.channels = "From server"
    }
}