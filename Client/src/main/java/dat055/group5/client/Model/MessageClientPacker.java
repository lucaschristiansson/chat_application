package dat055.group5.client.Model;

import dat055.group5.export.Message;
import dat055.group5.export.MessagePacker;
import dat055.group5.export.NetworkPackage;
import dat055.group5.export.PackageType;

import java.util.List;

public class MessageClientPacker implements MessagePacker <Message> {
    @Override
    public NetworkPackage addMessage(Message message) {
        return new NetworkPackage(PackageType.CREATE_MESSAGE, message);
    }

    @Override
    public NetworkPackage getMessagesByChannel(Message message) {
        return new NetworkPackage(PackageType.GET_MESSAGES_BY_CHANNEL, message);
    }

    @Override
    public void printMessages() {

    }
}
