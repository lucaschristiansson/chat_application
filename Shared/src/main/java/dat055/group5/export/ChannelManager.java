package dat055.group5.export;

import java.util.List;

public interface ChannelManager {
    boolean addChannel (Channel channel);
    boolean addUserToChannel(String username, int ChannelId);
    boolean removeUserFromChannel(String username, int channelID);
    List<Channel> getAllChannelsForUser(String username);
    public List<String> getAllUsersInChannel(int channelID);
}
