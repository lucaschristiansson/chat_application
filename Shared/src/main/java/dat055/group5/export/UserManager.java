package dat055.group5.export;

import java.util.List;

/**
 * Interface of necessary operations
 * needed for managing users
 */
public interface UserManager {
    //List<String> getUsers(List<String> users);
    boolean addUser(User user);
    boolean authenticateUser(User user);
}
