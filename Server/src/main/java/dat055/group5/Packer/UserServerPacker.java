package dat055.group5.Packer;

import dat055.group5.export.NetworkPackage;
import dat055.group5.export.PackageType;
import dat055.group5.export.User;
import dat055.group5.export.UserPacker;

import java.util.List;

public class UserServerPacker implements UserPacker <List<User>> {
    @Override
    public NetworkPackage getUsers(List <User> users) {
        return new NetworkPackage(PackageType.GET_USERS, users);
    }

    @Override
    public NetworkPackage addUser(User user) {
        return new NetworkPackage(PackageType.CREATE_USER, user);
    }

    @Override
    public NetworkPackage authenticateUser(User user) {
        return new NetworkPackage(PackageType.LOGIN, user);
    }
}
