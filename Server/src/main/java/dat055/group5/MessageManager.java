package dat055.group5;

import java.sql.*;
import java.time.Instant;

import static dat055.group5.PortalConnection.getError;

public class MessageManager {
    private final Driver driver;
    private Connection connection;

    public MessageManager(Driver driver){
        this.driver = driver;
        this.connection = driver.getPortalConnection().getConnection();
    }


    public void addMessage(String sender_name, int channel_id, String content, String image_url){
        String sql= "INSERT INTO Messages VALUES(?, ?, ?, ?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setTimestamp(1, Timestamp.from(Instant.now()));
            ps.setString(2, sender_name);
            ps.setInt(3, channel_id);
            ps.setString(4, content);
            ps.setString(5, image_url);

            ps.executeUpdate();
        } catch (SQLException e) {
            getError(e);
            e.printStackTrace();
        }
    }
    public void printMessages() {
        String query = "SELECT M.time, M.sender_name, M.content, M.image_url, C.name AS channel_name FROM Messages M JOIN Channels C ON M.channel_id=C.id ORDER BY time ASC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String time = rs.getString("time");
                String sender = rs.getString("sender_name");
                String content = rs.getString("content");
                String image = rs.getString("image_url");
                String channel = rs.getString("channel_name");

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
