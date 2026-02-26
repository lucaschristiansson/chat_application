package dat055.group5.client.Model.manager;

import dat055.group5.client.Driver;
import dat055.group5.client.Model.Model;
import dat055.group5.export.Message;
import dat055.group5.export.MessageManager;

import java.util.List;

public class MessageClientManager implements MessageManager {
    Model model;
    private final Driver driver;


    public MessageClientManager(Driver driver){
        this.driver = driver;
    }


    @Override
    public boolean addMessage(Message message) {
        model.addMessage(message);
        return true;
    }

    @Override
    public List<Message> getMessagesByChannel(int channelId) {
        return List.of();
    }

}
