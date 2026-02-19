package dat055.group5.manager;

import dat055.group5.Driver;
import dat055.group5.export.Channel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ChannelDatabaseManager {
    private final Driver driver;
    private final Connection connection;

    public ChannelDatabaseManager(Driver driver) {
        this.driver = driver;
        this.connection = driver.getPortalConnection().getConnection();
    }
    public boolean AddChannel(int channelID, String channelName) {
        String sql = "INSERT INTO Channels (channelID, channelName) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, channelID);
            ps.setString(2, channelName);

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean AddUserToChannel(String username, int channelID) {
        String sql = "INSERT INTO UsersInChannel (username, channelID) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setInt(2, channelID);

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean RemoveUserFromChannel(String username, int channelID) {
        String sql = "DELETE FROM UsersInChannel WHERE username = ? AND channelID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setInt(2, channelID);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

    public List<Channel> GetAllChannelsForUser(String username) {
        String sql = "SELECT * FROM Channels NATURAL JOIN UsersInChannel WHERE username = ?";
        List<Channel> channels = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);

            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    channels.add(new Channel(rs.getInt("channelID"),rs.getString("channelName")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return channels;
    }

}