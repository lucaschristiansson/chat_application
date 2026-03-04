package dat055.group5.client.Model.Packer;

import dat055.group5.export.*;
/**
 * Packs client request to {@link NetworkPackage} to send over network.
 */
public class MessageClientPacker implements MessagePacker <Integer> {
    @Override
    public NetworkPackage addMessage(Message message) {
        return new NetworkPackage(PackageType.CREATE_MESSAGE, message);
    }

    @Override
    public NetworkPackage getMessagesByChannel(Integer channel) {
        return new NetworkPackage(PackageType.GET_MESSAGES_BY_CHANNEL, channel);
    }

    @Override
    public void printMessages() {

    }
}
