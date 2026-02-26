package dat055.group5.client.Model.manager;

import dat055.group5.client.Model.Model;
import dat055.group5.export.Message;
import dat055.group5.export.MessageManager;

import java.util.List;

public class MessageClientManager implements MessageManager {
    Model model;

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
