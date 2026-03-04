package dat055.group5.export;

import java.util.List;
/**
 * Interface for necessary operations needed for
 * managing messages on server and client side.
 */
public interface MessageManager {
    boolean addMessage(Message message);
    List<Message> getMessagesByChannel(int channelId);
}
