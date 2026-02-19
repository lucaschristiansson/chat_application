package dat055.group5.export;

import java.util.List;

public class Channel {
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
}
