package dat055.group5.export;

import java.io.Serializable;
import java.time.Instant;

public class Message implements Serializable {
    Instant timestamp;
    String sender;
    Integer channel;
    String content;
    String imagePath;

    public Message(String sender, String content, Integer channel) {
        this.sender = sender;
        this.content = content;
        this.timestamp = Instant.now();
        this.channel = channel;
        this.imagePath = null;
    }

    public Message(String sender, String content, Instant timestamp, Integer channel) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
        this.channel = channel;
        this.imagePath = null;
    }

    public Message(String sender, String content, Instant timestamp, Integer channel, String imagePath) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
        this.channel = channel;
        this.imagePath = imagePath;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public Integer getChannel() {
        return channel;
    }

    public String getImagePath() {
        return imagePath;
    }

}
