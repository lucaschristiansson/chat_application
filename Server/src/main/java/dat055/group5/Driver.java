package dat055.group5;

import java.sql.SQLException;

public class Driver {
    private final PortalConnection portalConnection;
    private final ChannelManager channelManager;
    private final MessageManager messageManager;

    public Driver() {
        try {
            this.portalConnection = new PortalConnection(this);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        this.channelManager = new ChannelManager(this);
        this.messageManager = new MessageManager(this);
    }

    void main(){
        messageManager.addMessage("user1", 1, "yello", null);
        messageManager.printMessages();
    }

    public PortalConnection getPortalConnection() {
        return portalConnection;
    }

    public ChannelManager getChannelManager() {
        return channelManager;
    }
}
