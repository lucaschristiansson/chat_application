package dat055.group5.client.Model.manager;

import dat055.group5.client.Driver;
import dat055.group5.export.Channel;
import dat055.group5.export.Message;
import dat055.group5.export.MessageManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;

import java.util.List;

public class MessageClientManager implements MessageManager<Void, List<Message>> {

    private final Driver driver;

    public MessageClientManager(Driver driver){
        this.driver = driver;
    }

    /**
     * Adds message to the local UI
     * @param message
     * @return true
     */
    @Override
    public boolean addMessage(Message message) {
        Channel activeChannel = driver.getModel().getActiveChannel();
        if (activeChannel != null && activeChannel.getChannelID().equals(message.getChannel())) {
            driver.getModel().addMessage(message);
        }
        return true;
    }

    /**
     * Replaces the current message list with the given messages.
     * @param messages full list of messages for the requested channel
     * @return null
     */
    @Override
    public Void getMessagesByChannel(List<Message> messages) {
        Platform.runLater(() -> {
            driver.getModel().getMessages().setAll(FXCollections.observableArrayList(messages));
        });
        return null;
    }

}
