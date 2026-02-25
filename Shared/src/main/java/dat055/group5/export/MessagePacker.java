package dat055.group5.export;

import java.util.List;

public interface MessagePacker <S>{
    NetworkPackage addMessage(Message message);
    NetworkPackage getMessagesByChannel(S list);
    void printMessages();
}
