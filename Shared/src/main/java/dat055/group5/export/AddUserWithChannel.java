package dat055.group5.export;

import java.io.Serializable;
import java.util.List;

public class AddUserWithChannel implements Serializable {
    private int channelID;
    private List<String> usernames;

    public AddUserWithChannel(List <String> usernames, int channelID){
        this.usernames=usernames;
        this.channelID=channelID;
    }
    public List<String> getUsernames(){
        return usernames;
    }
    public int getChannelID(){
        return channelID;
    }
}
