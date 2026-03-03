package dat055.group5.export;

import java.io.Serializable;

/**
 * Contains all data related to users in the application.
 * Is serializable and can be sent over the network
 */
public class User implements Serializable {
    String username;
    String password;

    /**
     * Creates a new user-instance with a password and a username
     * @param userName unique identifier for the user
     * @param password password for the user
     */
    public User(String userName, String password) {
        this.username = userName;
        this.password = password;
    }

    /**
     * Creates a new user-instance with only a username
     * @param userName unique identifier for the user
     */
    public User(String userName) {
        this.username = userName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
