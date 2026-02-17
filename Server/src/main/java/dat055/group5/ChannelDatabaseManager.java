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
    public void AddUsersToChannel(String channel_id, List<String> user_ids) {
        String sql = "INSERT INTO UsersInChannel VALUES (?, ?)";
        for(String user : user_ids){
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, user);
                ps.setString(1, channel_id);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> getUsersInChannel(String channel_id){
        String sql = "SELECT * FROM UsersInChannel UC JOIN Channels C ON UC.channel = C.id WHERE UC.channel=?";

        List<String> users = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, channel_id);

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    users.add(rs.getString("username"));
                }
            }

        } catch (SQLException e) {
            getError(e);
            e.printStackTrace();
        }

        return users;
    }



}
