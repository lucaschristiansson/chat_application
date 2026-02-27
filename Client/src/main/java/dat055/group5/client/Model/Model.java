package dat055.group5.client.Model;

import dat055.group5.export.Channel;
import dat055.group5.export.Message;
import dat055.group5.export.User;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {
    private final ObjectProperty<Channel> activeChannel = new SimpleObjectProperty<>();
    private final ObservableList<Channel> channels = FXCollections.observableArrayList();
    private final ObservableList<Message> messages = FXCollections.observableArrayList();
    private final ObservableList<String> usersInActiveChannel = FXCollections.observableArrayList();
    private User clientUser;

    public Model (Channel channel, User user) {
        this.activeChannel.set(channel);
        this.clientUser = user;
    }

    public Channel getActiveChannel(){
        return activeChannel.get();
    }

    public void setActiveChannel(Channel channel) {
        this.activeChannel.set(channel);
    }

    public ObjectProperty<Channel> getActiveChannelProperty(){
        return activeChannel;
    }

    public void addMessage(Message message) {
        Platform.runLater(() -> {
            this.messages.add(message);
        });
    }

    public void addToChannels(Channel channel) {
        channels.add(channel);
    }

    public void addUserToActiveChannel(String username, int channelId) {
        if (this.activeChannel.get().getChannelID() == channelId)
            usersInActiveChannel.add(username);
    }

    public ObservableList<Channel> getChannels() {
        return channels;
    }

    public ObservableList<Message> getMessages() {
        return messages;
    }

    public ObservableList<String> getUsersInActiveChannel() {
        return usersInActiveChannel;
    }

    public User getClientUser() {
        return clientUser;
    }
    public void setClientUser(User user){
        this.clientUser = user;
    }
}
