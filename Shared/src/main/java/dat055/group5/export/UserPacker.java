package dat055.group5.export;

import java.util.List;

/**
 * Interface to pack user-related operations to {@link NetworkPackage}
 * @param <S> type of data for users
 */
public interface UserPacker <S>
{
    NetworkPackage getUsers(S users);
    NetworkPackage addUser (User user);
    NetworkPackage authenticateUser(User user);
}
