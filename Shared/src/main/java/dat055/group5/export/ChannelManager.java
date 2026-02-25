package dat055.group5.export;

import java.util.List;

public interface ChannelManager <C>{
    void addChannel (Channel channel);
    void addUserToChannel(String username, int ChannelId);
    void removeUserFromChannel(String username, int channelID);
    List<Channel> getAllChannelsForUser(String username);
    List<String> getAllUsersInChannel(int channelID);
    void getChannel (C channel);
}
