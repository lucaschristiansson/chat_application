package dat055.group5.export;

import java.io.Serializable;
import java.util.List;

public class Channel implements Serializable {
    Integer channelID;
    String channelName;

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
