package dat055.group5.export;

public class StringMessage {
    String message;

    public StringMessage (String string1) {
        this.message = string1;
    }

    public StringMessage (String string1, String string2) {
        this.message = string1 + "/" + string2; // "Users/Channels";
    }

    public StringMessage (String string1, String string2, String string3) {
        this.message = string1 + "/" + string2 + "/" + string3; // "Users/Channels"
    }


    String[] parts = message.split("/");

    String username = parts[0]; // "Users"
    String channelId = parts[1]; // "Channels"
}
