package dat055.group5.Packer;


import dat055.group5.export.Message;
import dat055.group5.export.MessagePacker;
import dat055.group5.export.NetworkPackage;
import dat055.group5.export.PackageType;


import java.util.List;

public class MessageServerPacker implements MessagePacker<List<Message>> {
    @Override
    public NetworkPackage addMessage(Message message) {
        return new NetworkPackage(PackageType.CREATE_MESSAGE, message);
    }

    @Override
    public NetworkPackage getMessagesByChannel(List<Message> list) {
        return new NetworkPackage(PackageType.GET_MESSAGES_BY_CHANNEL, list);
    }

    @Override
    public void printMessages() {

    }
}
