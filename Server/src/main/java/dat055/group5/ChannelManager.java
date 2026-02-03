package dat055.group5;

import java.sql.Connection;

public class ChannelManager {
    private final Driver driver;
    private Connection connection;

    public ChannelManager(Driver driver) {
        this.driver = driver;
        connection=driver.getPortalConnection().getConnection();
    }



}
