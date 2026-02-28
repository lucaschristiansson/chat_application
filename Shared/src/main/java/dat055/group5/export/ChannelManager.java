package dat055.group5.export;

import java.util.List;

public interface ChannelManager <C, L, U, R, X>{
    /**
     *
     * @param channel
     */
    void addChannel (C channel);

    /**
     * Adds a user to a specific channel
     * @param username
     * @param channelID
     */
    void addUserToChannel(String username, int channelID);

    /**
     * Removes a user to a specific channel
     * @param username
     * @param channelID
     */
    void removeUserFromChannel(String username, int channelID);

    /**
     * Returns a List of channels for a specific user
     * @param usernameOrListofUsernames
     * @return channels
     */
    R getAllChannelsForUser(L usernameOrListofUsernames);

    /**
     * Returns a list of users for a specific channel
     * @param channelidOrlistofUsernames
     * @return users
     */
    X getAllUsersInChannel(U channelidOrlistofUsernames);

    /**
     * returns a channel
     * @param channel
     */
    void getChannel (C channel);
}
