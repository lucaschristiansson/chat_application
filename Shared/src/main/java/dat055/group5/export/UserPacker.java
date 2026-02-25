package dat055.group5.export;

import java.util.List;

public interface UserPacker <S>
{
    NetworkPackage getUsers(S users);
    NetworkPackage addUser (User user);
    NetworkPackage authenticateUser(User user);
}
