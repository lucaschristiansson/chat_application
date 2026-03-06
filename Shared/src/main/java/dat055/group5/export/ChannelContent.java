package dat055.group5.export;

import java.util.List;

/**
 * Container-class for all channel-related data
 * Can be used to fetch an entire channel including message-history.
 */
public class ChannelContent {

    Channel channel;
    List <Message> messages;
    List<User> usersInChannel;

    public ChannelContent(String channelName, Integer channelId, List <Message> messages, List <User> users) {
        this.channel = new Channel(channelId,channelName);
        this.messages = messages;
        this.usersInChannel = users;
    }

    public ChannelContent(Channel channel, List <Message> messages, List <User> users) {
        this.channel = channel;
        this.messages = messages;
        this.usersInChannel = users;
    }
}
