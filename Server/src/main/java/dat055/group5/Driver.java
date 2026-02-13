package dat055.group5;

import java.sql.SQLException;

public class Driver {
    private final PortalConnection portalConnection;
    private final ChannelDatabaseManager channelDatabaseManager;
    private final MessageDatabaseManager messageDatabaseManager;
    private final UserDatabaseManager userDatabaseManager;

    public Driver() {
        try {
            this.portalConnection = new PortalConnection(this);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        this.channelDatabaseManager = new ChannelDatabaseManager(this);
        this.messageDatabaseManager = new MessageDatabaseManager(this);
        this.userDatabaseManager = new UserDatabaseManager(this);
    }

    void main(){
        messageDatabaseManager.addMessage("user1", 1, "yello", null);
        messageDatabaseManager.printMessages();
    }

    public PortalConnection getPortalConnection() {
        return portalConnection;
    }

    public ChannelDatabaseManager getChannelDatabaseManager() {
        return channelDatabaseManager;
    }

    public UserDatabaseManager getUserDatabaseManager() {
        return userDatabaseManager;
    }
}
