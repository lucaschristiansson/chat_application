package dat055.group5.client.Model.manager;

import dat055.group5.client.Driver;
import dat055.group5.export.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ChannelClientManager implements ChannelManager <Channel, List<Channel>, List<String>, Void, Void> {
    Driver driver;
    public ChannelClientManager(Driver driver) {
        this.driver = driver;
    }

    @Override
    public void addChannel(Channel channel) {
        driver.getModel().addToChannels(channel);
    }

    @Override
    public void addUserToChannel(String username, int channelId) {
        driver.getModel().addUserToActiveChannel(username, channelId);
    }

    @Override
    public void removeUserFromChannel(String username, int channelID) {
        driver.getModel().removeUserfromActiveChannel(username, channelID);
    }

    @Override
    public Void getAllChannelsForUser(List<Channel> channels) {
        driver.getModel().setChannels(FXCollections.observableArrayList(channels));
        return null;
    }

    @Override
    public Void getAllUsersInChannel(List<String> users) {
        driver.getModel().setUsersInActiveChannel(FXCollections.observableArrayList(users));
        return null;
    }

    /**
     * This is for sending a request for a channel and then this method is for after you have recived the channel from the server
     */
    @Override
    public void getChannel(Channel channel) {
        driver.getModel().setActiveChannel(channel);
    }
}