package dat055.group5.client.Model.manager;

import dat055.group5.client.Driver;
import dat055.group5.client.Model.Client;
import dat055.group5.export.User;
import dat055.group5.export.UserManager;

import java.util.ArrayList;
import java.util.List;

public class UserClientManager implements UserManager {
    private final Driver driver;

    public UserClientManager(Driver driver){
        this.driver = driver;
    }

    @Override
    public boolean addUser(User user) {
        driver.getModel().setClientUser(user);
        return true;
    }
    @Override
    public boolean authenticateUser(User user) {
        return true;
    }

    @Override
    public List<String> getAllUsers() {
        return new ArrayList<>();
    }
}
