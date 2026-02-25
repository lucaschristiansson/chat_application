package dat055.group5.Packer;

import dat055.group5.export.*;

import java.util.List;

public class ChannelServerPacker implements ChannelPacker <List<User>, List<Channel>, Channel> {
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
    public NetworkPackage removeUserFromChannel(String username, String channelID) {
        String result = String.join("/",username, channelID);
        return new NetworkPackage(PackageType.REMOVE_USER_FROM_CHANNEL, result);
    }

    @Override
    public NetworkPackage getAllChannelsForUser(List<Channel> list) {
        return new NetworkPackage(PackageType.GET_CHANNELS_FOR_USER, list);
    }

    @Override
    public NetworkPackage getAllUsersInChannel(List<User> list) {
        return new NetworkPackage(PackageType.GET_USER_IN_CHANNEL, list);
    }

    @Override
    public NetworkPackage getChannel(Channel channel) {
        return new NetworkPackage(PackageType.GET_CHANNEL, channel);
    }
}
