package dat055.group5;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dat055.group5.export.User;

import static dat055.group5.PortalConnection.getError;

public class UserDatabaseManager {
    private final Driver driver;
    private final Connection connection;

    public UserDatabaseManager(Driver driver){
        this.driver = driver;
        this.connection = driver.getPortalConnection().getConnection();
    }

    public List<String> getUsers(){
        String sql = "SELECT * FROM Users";

        List<String> users = new ArrayList<>();

        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                users.add(rs.getString("username"));
            }

        } catch (SQLException e) {
            getError(e);
            e.printStackTrace();
        }
        return users;
    }
    public List<String> getUsersInChannel(String channel_id){
        String sql = "SELECT * FROM UsersInChannel UC JOIN Channels C ON UC.channel = C.id WHERE UC.channel=?";

        List<String> users = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    System.out.println("Login was successful! User exists");
                }else{
                    System.out.println("Invalid username or password");
                }
            }

        } catch (SQLException e) {
            getError(e);
            e.printStackTrace();
        }

        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                users.add(rs.getString("username"));
            }

        } catch (SQLException e) {
            getError(e);
            e.printStackTrace();
        }
        return users;
    }


    public void addUser(User user){
        String sql = "INSERT INTO Users VALUES (?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            ps.executeUpdate();
        } catch (SQLException e) {
            getError(e);
            e.printStackTrace();
        }
    }

    public void authenticateUser(User user){
        String sql = "SELECT 1 FROM Users WHERE username =? AND password =?";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    System.out.println("Login was successful! User exists");
                }else{
                    System.out.println("Invalid username or password");
                }
            }


        } catch (SQLException e) {
            getError(e);
            e.printStackTrace();
        }

    }



}
