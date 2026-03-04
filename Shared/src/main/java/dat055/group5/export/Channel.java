package dat055.group5.export;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a channel in the chat application
 * Class is serializable and instances can be sent in streams.
 */
public class Channel implements Serializable {
    Integer channelID;
    String channelName;

    /**
     * Creates a channel instance.
     * @param channelID unique identifier for the channel
     * @param channelName the displayed name of the channel
     */
    public Channel(Integer channelID, String channelName) {
        this.channelID = channelID;
        this.channelName = channelName;
    }

    public Integer getChannelID() {
        return channelID;
    }

    public String getChannelName() {
        return channelName;
    }

    @Override
    public String toString() {
        return channelName;
    }
}
