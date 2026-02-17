package dat055.group5.export;

import java.util.List;

public class Channel {
    String channelID;
    List<User> users;
    List<Message> messages;

    public Channel(String channelID, List<User> users, List<Message> message) {
        this.channelID = channelID;
        this.users = users;
        this.messages = message;
    }

    public void addMessage(Message message) {//To server
        //Code for adding messages to server
    }

    public void getMessages () { //from server
        //Code for getting messages from server =
    }
}
