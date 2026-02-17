package dat055.group5;

import dat055.group5.export.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ChannelDatabaseManager {
    private final Driver driver;
    private final Connection connection;

    public ChannelDatabaseManager(Driver driver) {
        this.driver = driver;
        this.connection = driver.getPortalConnection().getConnection();
    }
    public void AddChannel(int channel_id, String channel_name) {
        String sql = "INSERT INTO Channels VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, channel_id);
            ps.setString(2, channel_name);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
