package dat055.group5.client.Model.manager;

import dat055.group5.export.User;
import dat055.group5.export.UserManager;

import java.util.List;
//TODO Behöver vi denna?
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
    public boolean authenticateUser(User user) {
        return true;
    }
}
