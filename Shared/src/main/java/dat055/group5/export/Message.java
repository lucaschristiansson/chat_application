package dat055.group5.export;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable {
    // Crucial for socket communication stability
    private static final long serialVersionUID = 1L;

    private Instant timestamp;
    private String sender;
    private Integer channel;
    private String content;

    // We send raw bytes over the network, NOT JavaFX UI components
    private List<byte[]> imageBytes;

    // Constructor 1: New text message
    public Message(String sender, String content, Integer channel) {
        this.sender = sender;
        this.content = content;
        this.timestamp = Instant.now();
        this.channel = channel;
        this.imageBytes = new ArrayList<>();
    }

    // Constructor 2: Reconstructed text message (e.g., from database)
    public Message(String sender, String content, Instant timestamp, Integer channel) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
        this.channel = channel;
        this.imageBytes = new ArrayList<>();
    }

    // Constructor 3: Reconstructed message with images
    public Message(String sender, String content, Instant timestamp, Integer channel, List<byte[]> imageBytes) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
        this.channel = channel;
        this.imageBytes = (imageBytes != null) ? imageBytes : new ArrayList<>();
    }

    // Constructor 4: New message with images
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