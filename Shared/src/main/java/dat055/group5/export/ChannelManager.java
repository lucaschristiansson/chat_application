package dat055.group5.export;

import java.util.List;

public interface ChannelManager <C>{
    void addChannel (C channel);
    void addUserToChannel(String username, int channelID);
    void removeUserFromChannel(String username, int channelID);
    List<C> getAllChannelsForUser(String username);
    List<String> getAllUsersInChannel(int channelID);
    void getChannel (C channel);
}
