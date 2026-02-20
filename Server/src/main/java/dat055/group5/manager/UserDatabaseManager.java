package dat055.group5.manager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dat055.group5.Driver;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        String sql = "SELECT username FROM Users";

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


    public void addUser(User user){
        String sql = "INSERT INTO Users(username, password) VALUES (?, ?)";
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
