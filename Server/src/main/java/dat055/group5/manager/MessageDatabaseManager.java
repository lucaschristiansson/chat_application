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
    public void addMessage(Message message){
        String sql = "INSERT INTO Messages (message_time, username, channel_id, content, image_path) VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setTimestamp(1, Timestamp.from(message.getTimestamp()));
            ps.setString(2, message.getSender());
            ps.setInt(3, message.getChannel());
            ps.setString(4, message.getContent());
            ps.setString(5, message.getImagePath());

            if (ps.executeUpdate() > 0) {
                System.out.println("Message: "+ message.getContent() + " added successfully.");
            }
        } catch (SQLException e) {
            System.err.println(getError(e));
            e.printStackTrace();
        }
    }
    @Override
    public List<Message> getMessagesByChannel(int channelId) { //DENNA HAR FEL KOLUMN NAMN
        String query =
                "SELECT M.message_time, M.username, M.content, M.imagePath " +
                "FROM Messages M " +
                "NATURAL JOIN Users U " +
                "WHERE M.channelID = ? " +
                "ORDER BY M.messageTime ASC";

        List<Message> messages = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, channelId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    messages.add(new Message(
                            rs.getString("username"),
                            rs.getString("content"),
                            rs.getTimestamp("messageTime").toInstant(),
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
    public void printMessages() { //DENNA HAR FEL KOLUMN NAMN
        String query =
                "SELECT M.message_Time, M.username, M.content, M.imagePath, C.channelName " +
                "FROM Messages M " +
                "NATURAL JOIN Channels C " +
                "ORDER BY M.messageTime ASC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String time = rs.getString("messageTime");
                String sender = rs.getString("username");
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
