package dat055.group5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dat055.group5.PortalConnection.getError;


public class ChannelDatabaseManager {
    private final Driver driver;
    private final Connection connection;

    public ChannelDatabaseManager(Driver driver) {
        this.driver = driver;
        this.connection = driver.getPortalConnection().getConnection();
    }

    public void CreateChannel(String channel_name) {//denna ska ta ett channelobjekt
        String sql = "INSERT INTO Channels VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, channel_name);

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
