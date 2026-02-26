package dat055.group5.export;

import java.util.List;

public interface MessageManager {
    boolean addMessage(Message message);
    List<Message> getMessagesByChannel(int channelId);
}
