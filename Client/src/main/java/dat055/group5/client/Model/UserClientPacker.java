package dat055.group5.client.Model;

import dat055.group5.export.NetworkPackage;
import dat055.group5.export.PackageType;
import dat055.group5.export.User;
import dat055.group5.export.UserPacker;

import java.sql.Struct;

public class UserClientPacker implements UserPacker <String> {

    @Override
    public NetworkPackage getUsers(String channelID) {
        return new NetworkPackage(PackageType.GET_USERS, channelID);
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