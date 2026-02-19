package dat055.group5.export;

public class User {
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

    public void setUsername(String userName) {
        this.username = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return this;
    }

    public void setUser(String userName, String password) {
        this.username = userName;
        this.password = password;
    }

}
