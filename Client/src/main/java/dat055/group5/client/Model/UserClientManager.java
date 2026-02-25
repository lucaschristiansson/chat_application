package dat055.group5.client.Model;

import dat055.group5.export.NetworkPackage;
import dat055.group5.export.User;
import dat055.group5.export.UserManager;

import java.util.List;

public class UserClientManager implements UserManager {
    @Override
    public List<String> getUsers() {
        return List.of();
    }

    @Override
    public void addUser(User user) {
        //print user
    }

    @Override
    public void authenticateUser(User user) {

    }
}
