package dat055.group5.client.Model.manager;

import dat055.group5.client.Driver;
import dat055.group5.client.Model.Model;
import dat055.group5.export.*;

import java.util.List;

/**
 * Helt oanvänd klass, vänligen ignorera.
 */
public class ChannelClientManager implements ChannelManager <Channel>{
    //denna klass är broken :)
    List<Channel> channels;
    User user;
    Model model;
    Driver driver;
    public ChannelClientManager(Driver driver) {
        this.driver = driver;
    }

    @Override
    public void addChannel(Channel channel) {
        model.addToChannels(channel);
    }

    @Override
    public void addUserToChannel(String username, int channelId) {
        model.addUserToActiveChannel(username, channelId);
    }

    @Override
    public void removeUserFromChannel(String username, int channelID) {
        //TODO
    }

    @Override
    public List<Channel> getAllChannelsForUser(String username) {
        //TODO
        return List.of();
    }

    @Override
    public List<String> getAllUsersInChannel(int channelID) {
        //TODO
        return List.of();
    }

    @Override
    public void getChannel(Channel channel) {
        //TODO: this method makes no sense.
        model.setActiveChannel(channel);
    }
}