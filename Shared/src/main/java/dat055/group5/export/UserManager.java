package dat055.group5.export;

import java.util.List;

public interface UserManager {
    List<String> getUsers();
    void addUser(User user);
    void authenticateUser(User user);
}
