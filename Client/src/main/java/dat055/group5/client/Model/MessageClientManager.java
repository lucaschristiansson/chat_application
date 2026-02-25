package dat055.group5.client.Model;

import dat055.group5.export.Message;
import dat055.group5.export.MessageManager;

import java.util.List;

public class MessageClientManager implements MessageManager {
    @Override
    public boolean addMessage(Message message) {
        return false;
    }

    @Override
    public List<Message> getMessagesByChannel(int channelId) {
        return List.of();
    }

    @Override
    public void printMessages() {

    }
}
