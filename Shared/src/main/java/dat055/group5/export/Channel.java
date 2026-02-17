package dat055.group5.export;

import java.io.Serializable;
import java.util.List;

public class Channel implements Serializable {
    String channelID;
    List<String> users;
    List<Message> messages;

    public Channel(String channelID, List<String> users, List<Message> message) {
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
    public String getChannelID(){
        return channelID;
    }
    public List<String> getUsers(){
        return users.g;
    }
}
