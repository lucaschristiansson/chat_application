package dat055.group5.export;

import java.io.Serializable;

public class User implements Serializable {
    String username;
    String password;

    public User(String userName, String password) {
        this.username = userName;
        this.password = password;
    }

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
