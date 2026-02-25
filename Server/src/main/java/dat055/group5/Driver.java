package dat055.group5;

import dat055.group5.Packer.ChannelServerPacker;
import dat055.group5.Packer.MessageServerPacker;
import dat055.group5.Packer.UserServerPacker;
import dat055.group5.manager.*;

import java.sql.SQLException;

public class Driver {
    private final PortalConnection portalConnection;
    private final ChannelDatabaseManager channelDatabaseManager;
    private final MessageDatabaseManager messageDatabaseManager;
    private final UserDatabaseManager userDatabaseManager;
    private final MessageServerPacker messageServerPacker;
    private final ChannelServerPacker channelServerPacker;
    private final UserServerPacker userServerPacker;

    public Driver() {
        try {
            Server server = new Server(5000, this);
            server.start();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            this.portalConnection = new PortalConnection(this);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        this.channelDatabaseManager = new ChannelDatabaseManager(this);
        this.messageDatabaseManager = new MessageDatabaseManager(this);
        this.userDatabaseManager = new UserDatabaseManager(this);
        this.messageServerPacker = new MessageServerPacker();
        this.channelServerPacker = new ChannelServerPacker();
        this.userServerPacker = new UserServerPacker();
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

    public MessageDatabaseManager getMessageDatabaseManager() {
        return messageDatabaseManager;
    }

    public MessageServerPacker getMessageServerPacker() {
        return messageServerPacker;
    }

    public ChannelServerPacker getChannelServerPacker() {
        return channelServerPacker;
    }

    public UserServerPacker getUserServerPacker() {
        return userServerPacker;
    }
}
