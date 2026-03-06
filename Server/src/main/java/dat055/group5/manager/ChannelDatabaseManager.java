package dat055.group5.manager;

import dat055.group5.Driver;
import dat055.group5.export.Channel;
import dat055.group5.export.ChannelManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Communicates with SQL database
 */
public class ChannelDatabaseManager implements ChannelManager <Channel, String, Integer, List<Channel>, List<String>> {

    private final Driver driver;
    private final Connection connection;

    public ChannelDatabaseManager(Driver driver) {
        this.driver = driver;
        this.connection = driver.getPortalConnection().getConnection();
    }

    /**
     * Adds a channel to the SQL database.
     * @param channel
     */
    @Override
    public Channel addChannel(Channel channel) {
        String sql = "INSERT INTO Channels (channel_name) VALUES (?) RETURNING channel_id";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, channel.getChannelName());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int generatedId = rs.getInt("channel_id");
                    return new Channel(generatedId, channel.getChannelName());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds a user to specified channel in the SQL database.
     * @param username
     * @param channelID
     */
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

    /**
     * Removes user from the channel table in SQL database
     */
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

    /**
     * Retrieves all channels that includes the specified user
     * from the SQL database.
     * @return list of channels
     */
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

    /**
     * Retrieves all users in specified channel
     * from SQL database.
     * @return list of users
     */
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
    public void getChannel(Channel channel) {}

    public Channel getChannelById(int channelID) {
        String sql = "SELECT channel_id, channel_name FROM Channels WHERE channel_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, channelID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Channel(rs.getInt("channel_id"), rs.getString("channel_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}