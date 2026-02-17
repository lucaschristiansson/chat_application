package dat055.group5.export;

import java.io.File;
import java.io.Serializable;
import java.time.Instant;

public class Message implements Serializable {
    User user;
    String text;
    File picture;
    Instant timestamp;
    Channel channel;

    public Message(User user, String text, File picture, Instant timestamp, Channel channel) {
        this.user = user;
        this.text = text;
        this.picture = picture;
        this.timestamp = timestamp;
        this.channel = channel;
    }
}
