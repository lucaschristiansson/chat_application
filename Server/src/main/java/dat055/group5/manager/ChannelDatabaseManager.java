package dat055.group5.manager;

import dat055.group5.Driver;
import dat055.group5.export.Channel;
import dat055.group5.export.ChannelManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ChannelDatabaseManager implements ChannelManager {
    private final Driver driver;
    private final Connection connection;

    public ChannelDatabaseManager(Driver driver) {
        this.driver = driver;
        this.connection = driver.getPortalConnection().getConnection();
    }
    @Override
    public boolean addChannel(Channel channel) {
        String sql = "INSERT INTO Channels (channelID, channelName) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, channel.getChannelID());
            ps.setString(2, channel.getChannelName());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean addUserToChannel(String username, int channelID) {
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
    @Override
    public boolean removeUserFromChannel(String username, int channelID) {
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
    @Override
    public List<Channel> getAllChannelsForUser(String username) {
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
    @Override
    public List<String> getAllUsersInChannel(int channelID) {
        String sql = "SELECT username FROM UsersInChannel WHERE channelID = ?";
        List<String> users = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, channelID);

            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(rs.getString("username"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}