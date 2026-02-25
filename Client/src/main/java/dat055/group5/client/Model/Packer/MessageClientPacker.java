package dat055.group5.client.Model.Packer;

import dat055.group5.export.*;

public class MessageClientPacker implements MessagePacker <Integer> {
    @Override
    public NetworkPackage addMessage(Message message) {
        return new NetworkPackage(PackageType.CREATE_MESSAGE, message);
    }

    @Override
    public NetworkPackage getMessagesByChannel(Integer channel) {
        return new NetworkPackage(PackageType.GET_MESSAGES_BY_CHANNEL, channel);
    }

    //DELETE MESSAGE?

    @Override
    public void printMessages() {

    }
}
