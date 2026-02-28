package dat055.group5.client.Model.manager;

import dat055.group5.client.Driver;
import dat055.group5.client.Model.Client;
import dat055.group5.export.User;
import dat055.group5.export.UserManager;

import java.util.List;

public class UserClientManager implements UserManager {
    Driver driver;

    @Override
    public void addUser(User user) {
        driver.getModel().setClientUser(user);
    }
    @Override
    public boolean authenticateUser(User user) {
        return true;
    }
}
