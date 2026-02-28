package dat055.group5.manager;

import dat055.group5.Driver;
import dat055.group5.export.Channel;
import dat055.group5.export.ChannelManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ChannelDatabaseManager implements ChannelManager <Channel, String, Integer, List<Channel>, List<String>> {
    private final Driver driver;
    private final Connection connection;

    public ChannelDatabaseManager(Driver driver) {
        this.driver = driver;
        this.connection = driver.getPortalConnection().getConnection();
    }
    @Override
    public void addChannel(Channel channel) {
        String sql = "INSERT INTO Channels (channel_id, channel_name) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, channel.getChannelID());
            ps.setString(2, channel.getChannelName());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void addUserToChannel(String username, int channelID) {
        String sql = "INSERT INTO UsersInChannel (username, channel_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setInt(2, channelID);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void removeUserFromChannel(String username, int channelID) {
        String sql = "DELETE FROM UsersInChannel WHERE username = ? AND channel_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setInt(2, channelID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    @Override
    public List<Channel> getAllChannelsForUser(String username) {
        String sql = "SELECT * FROM Channels NATURAL JOIN UsersInChannel WHERE username = ?";
        List<Channel> channels = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);

            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    channels.add(new Channel(rs.getInt("channel_id"),rs.getString("channel_name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return channels;
    }
    @Override
    public List<String> getAllUsersInChannel(Integer channelID) {
        String sql = "SELECT username FROM UsersInChannel WHERE channel_id = ?";
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

    @Override
    public void getChannel(Channel channel) {

    }
}