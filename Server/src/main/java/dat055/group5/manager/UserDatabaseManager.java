package dat055.group5.manager;

import java.sql.*;

import dat055.group5.Driver;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dat055.group5.export.User;
import dat055.group5.export.UserManager;

import static dat055.group5.PortalConnection.getError;

/**
 * Communicates with the SQL database
 */
public class UserDatabaseManager implements UserManager {
    private final Driver driver;
    private final Connection connection;


    public UserDatabaseManager(Driver driver){
        this.driver = driver;
        this.connection = driver.getPortalConnection().getConnection();
    }

    @Override
    public boolean addUser(User user){
        String sql = "INSERT INTO Users(username, password) VALUES (?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            getError(e);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks the SQL database for matching login credentials
     * @param user
     * @return whether authentication succeeded or not.
     */
    @Override
    public boolean authenticateUser(User user){
        String sql = "SELECT 1 FROM Users WHERE username =? AND password =?";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    System.out.println("Login was successful! User exists");
                    return true;
                }else{
                    System.out.println("Invalid username or password");
                }
            }

        } catch (SQLException e) {
            getError(e);
            e.printStackTrace();
        }
        return false;
    }

}
