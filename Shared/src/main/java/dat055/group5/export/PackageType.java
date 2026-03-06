package dat055.group5.export;

/**
 * Defines all types of packages
 * that can be sent
 * Each type represents a request or operation
 */
public enum PackageType {
    CREATE_CHANNEL,
    CREATE_USER,
    CREATE_MESSAGE,
    ADD_USER_TO_CHANNEL,
    REMOVE_USER_FROM_CHANNEL,
    GET_CHANNELS_FOR_USER,
    GET_MESSAGES_BY_CHANNEL,
    GET_USERS,
    GET_USER_IN_CHANNEL,
    LOGIN,
    GET_CHANNEL,
    VERIFY,
    CHANNEL_ADDED
}
