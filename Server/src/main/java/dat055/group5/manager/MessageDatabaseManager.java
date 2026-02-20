/* TODO */
// Fix SQL queries.

package dat055.group5.manager;

import dat055.group5.Driver;
import dat055.group5.export.Message;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dat055.group5.PortalConnection.getError;

public class MessageDatabaseManager {
    private final dat055.group5.Driver driver;
    private final Connection connection;

    public MessageDatabaseManager(Driver driver){
        this.driver = driver;
        this.connection = driver.getPortalConnection().getConnection();
    }

    public boolean addMessage(Message message){
        String sql = "INSERT INTO Messages (timestamp, sender, channelID, content, imagePath) VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setTimestamp(1, Timestamp.from(message.getTimestamp()));
            ps.setString(2, message.getSender());
            ps.setInt(3, message.getChannel());
            ps.setString(4, message.getContent());
            ps.setString(5, message.getImagePath());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(getError(e));
            e.printStackTrace();
        }
        return false;
    }

    public List<Message> getMessagesByChannel(int channelId) {
        String query =
                "SELECT M.timestamp, M.sender, M.content, M.imagePath " +
                "FROM Messages M " +
                "NATURAL JOIN Users U" +
                "WHERE M.channelID = ? " +
                "ORDER BY M.timestamp ASC";

        List<Message> messages = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, channelId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    messages.add(new Message(
                            rs.getString("sender"),
                            rs.getString("content"),
                            rs.getTimestamp("timestamp").toInstant(),
                            channelId,
                            rs.getString("imagePath")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return messages;
    }

    /* Debug function, might not be used again */
    public void printMessages() {
        String query =
                "SELECT M.timestamp, M.sender, M.content, M.imagePath, C.channelName " +
                "FROM Messages M " +
                "JOIN Channels C ON M.channelID = C.channelID " +
                "ORDER BY M.timestamp ASC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String time = rs.getString("timestamp");
                String sender = rs.getString("sender");
                String content = rs.getString("content");
                String image = rs.getString("imagePath");
                String channel = rs.getString("channelName");

                System.out.println("--------------------------------");
                System.out.println(content);

                if (image != null) {
                    System.out.println("[Image: " + image + "]");
                }

                System.out.println("In " + channel + " from " + sender + " @ " + time);
            }
            System.out.println("--------------------------------\n");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
