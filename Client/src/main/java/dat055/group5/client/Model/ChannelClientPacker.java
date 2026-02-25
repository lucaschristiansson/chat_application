package dat055.group5.client.Model;

import dat055.group5.export.Channel;
import dat055.group5.export.ChannelPacker;
import dat055.group5.export.NetworkPackage;
import dat055.group5.export.PackageType;

import java.util.List;

public class ChannelClientPacker implements ChannelPacker <String, String> {

    @Override
    public NetworkPackage addChannel(Channel channel) {
        return new NetworkPackage(PackageType.CREATE_CHANNEL, channel);
    }

    @Override
    public NetworkPackage addUserToChannel(String username, String channelId) {
        String result = String.join("/",username, channelId);
        return new NetworkPackage(PackageType.ADD_USER_TO_CHANNEL, result);
    }

    @Override
    public NetworkPackage removeUserFromChannel(String username, String channelId) {
        String result = String.join("/",username, channelId);
        return new NetworkPackage(PackageType.REMOVE_USER_FROM_CHANNEL, result);
    }

    @Override
    public NetworkPackage getAllChannelsForUser(String username) {
        return new NetworkPackage(PackageType.GET_CHANNELS_FOR_USER, username);
    }

    @Override
    public NetworkPackage getAllUsersInChannel(String channelID) {
        return new NetworkPackage(PackageType.GET_USER_IN_CHANNEL, channelID);
    }
}
