package dat055.group5.export;

import java.util.List;

public interface UserManager {
    //List<String> getUsers(List<String> users);
    void addUser(User user);
    boolean authenticateUser(User user);
}
