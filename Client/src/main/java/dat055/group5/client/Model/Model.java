package dat055.group5.client.Model;

import dat055.group5.export.Channel;
import dat055.group5.export.Message;
import dat055.group5.export.User;

import java.util.List;
import java.util.SimpleTimeZone;

public class Model {
    private Channel activeChannel;
    private List<Channel> channels;
    private List<Message> messages;
    private List<String> usersInActiveChannel;
    private User clientUser;

    public Model (Channel channel) {
        this.activeChannel = channel;
    }

    public void setActiveChannel(Channel channel) {
        this.activeChannel = channel;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public void addToChannelList (Channel channel) {
        channels.add(channel);
    }

   public void addUserToActiveChannel(String username, int channelId) {
        if (this.activeChannel.getChannelID() == channelId)
            usersInActiveChannel.add(username);
    }

}
