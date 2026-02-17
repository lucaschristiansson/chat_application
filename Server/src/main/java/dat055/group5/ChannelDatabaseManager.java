package dat055.group5;

import java.sql.Connection;

public class ChannelDatabaseManager {
    private final Driver driver;
    private final Connection connection;

    public ChannelDatabaseManager(Driver driver) {
        this.driver = driver;
        this.connection = driver.getPortalConnection().getConnection();
    }

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
