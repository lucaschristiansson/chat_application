package dat055.group5.export;

public class User {
    String userName;
    String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return this;
    }

    public void setUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

}
