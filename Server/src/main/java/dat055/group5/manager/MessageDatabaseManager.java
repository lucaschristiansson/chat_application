/* TODO */
// Fix SQL queries.

package dat055.group5.manager;

import dat055.group5.Driver;
import dat055.group5.export.Message;
import dat055.group5.export.MessageManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dat055.group5.PortalConnection.getError;

public class MessageDatabaseManager implements MessageManager {
    private final dat055.group5.Driver driver;
    private final Connection connection;

    public MessageDatabaseManager(Driver driver){
        this.driver = driver;
        this.connection = driver.getPortalConnection().getConnection();
    }
    @Override
    public boolean addMessage(Message message){
        String sql = "INSERT INTO Messages (message_time, username, channel_id, content, image_path) VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setTimestamp(1, Timestamp.from(message.getTimestamp()));
            ps.setString(2, message.getSender());
            ps.setInt(3, message.getChannel());
            ps.setString(4, message.getContent());
            ps.setString(5, message.getImagePath());

            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println(getError(e));
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public List<Message> getMessagesByChannel(int channelId) {
        String query =
                "SELECT M.message_time, M.username, M.content, M.image_path " +
                "FROM Messages M " +
                "NATURAL JOIN Users U " +
                "WHERE M.channel_id = ? " +
                "ORDER BY M.message_time ASC";

        List<Message> messages = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, channelId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    messages.add(new Message(
                            rs.getString("username"),
                            rs.getString("content"),
                            rs.getTimestamp("message_time").toInstant(),
                            channelId,
                            rs.getString("image_path")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return messages;
    }

}
