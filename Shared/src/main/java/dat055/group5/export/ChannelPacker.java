package dat055.group5.export;

import java.util.List;

/**
 * Interface to pack channel-related operations to {@link NetworkPackage}
 * @param <C> type of data for users
 * @param <U> type of data for users in channel
 * @param <I> type of data for channel
 */
public interface ChannelPacker <C, U, I> {
    NetworkPackage addChannel (Channel channel);
    NetworkPackage addUserToChannel(String username, String channelId);
    NetworkPackage removeUserFromChannel(String username, String channelID);
    NetworkPackage getAllChannelsForUser(C usernameOrList);
    NetworkPackage getAllUsersInChannel(U channelIDOrList);

    NetworkPackage getChannel (I channelId);
}
