package dat055.group5.export;

import java.util.List;
/**
 * Interface for necessary operations needed for
 * managing messages on server and client side.
 */
public interface MessageManager <C, C2>{
    boolean addMessage(Message message);
    C getMessagesByChannel(C2 channel);
}
