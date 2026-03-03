package dat055.group5.export;

import java.util.List;
/**
 * Interface to pack message-related operations to {@link NetworkPackage}
 * @param <S> type of data for messages
 */
public interface MessagePacker <S>{
    NetworkPackage addMessage(Message message);
    NetworkPackage getMessagesByChannel(S list);
    void printMessages();
}
