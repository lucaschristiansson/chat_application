package dat055.group5.export;

import java.util.List;

public interface ChannelPacker <C, U, I> {
    NetworkPackage addChannel (Channel channel);
    NetworkPackage addUserToChannel(String username, String channelId);
    NetworkPackage removeUserFromChannel(String username, String channelID);
    NetworkPackage getAllChannelsForUser(C usernameOrList);
    NetworkPackage getAllUsersInChannel(U channelIDOrList);

    NetworkPackage getChannel (I channelId);
}
