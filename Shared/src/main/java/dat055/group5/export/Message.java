package dat055.group5.export;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a message
 * Is serializable and can be sent in streams over network
 */
public class Message implements Serializable {
    /**
     * Unique identifier
     * Crucial for socket communication stability
     */
    private static final long serialVersionUID = 1L;

    private Instant timestamp;
    private String sender;
    private Integer channel;
    private String content;

    // We send raw bytes over the network, NOT JavaFX UI components
    private List<byte[]> imageBytes;

    /**
     * Creates a Message
     * @param sender username of sender
     * @param content text content of message
     * @param channel channel ID of which the message was sent to
     */
    public Message(String sender, String content, Integer channel) {
        this.sender = sender;
        this.content = content;
        this.timestamp = Instant.now();
        this.channel = channel;
        this.imageBytes = new ArrayList<>();
    }
    /**
     * Reconstructed text message (e.g., from database)
     * @param sender username of sender
     * @param content text content of message
     * @param timestamp time of message creation
     * @param channel channel ID of which the message was sent to
     */
    public Message(String sender, String content, Instant timestamp, Integer channel) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
        this.channel = channel;
        this.imageBytes = new ArrayList<>();
    }

    /**
     * Reconstructed text message with images
     * @param sender username of sender
     * @param content text content of message
     * @param timestamp time of message creation
     * @param channel channel ID of which the message was sent to
     * @param imageBytes list of pictures represented as byte-arrays
     */
    public Message(String sender, String content, Instant timestamp, Integer channel, List<byte[]> imageBytes) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
        this.channel = channel;
        this.imageBytes = (imageBytes != null) ? imageBytes : new ArrayList<>();
    }

    /**
     * New message with image
     * @param sender username of sender
     * @param content text content of message
     * @param channel channel ID of which the message was sent to
     * @param imageBytes list of pictures represented as byte-arrays
     */
    public Message(String sender, String content, Integer channel, List<byte[]> imageBytes) {
        this.sender = sender;
        this.content = content;
        this.timestamp = Instant.now(); // Fixed bug here!
        this.channel = channel;
        this.imageBytes = (imageBytes != null) ? imageBytes : new ArrayList<>();
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

    public List<byte[]> getImageBytes() {
        return imageBytes;
    }

    public void addImageBytes(byte[] bytes) {
        this.imageBytes.add(bytes);
    }
}