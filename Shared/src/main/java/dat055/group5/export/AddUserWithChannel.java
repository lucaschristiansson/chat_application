package dat055.group5.export;

import java.io.Serializable;
import java.util.List;

/**
 * The purpose of this class is to make an object containing both {@link User} objects
 * and {@link Channel} objects in order to be able to send both in the same {@link NetworkPackage}
 */
public class AddUserWithChannel implements Serializable {
    private final int channelID;
    private final List<String> usernames;

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
