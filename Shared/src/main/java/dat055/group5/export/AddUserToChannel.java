package dat055.group5.export;

import java.io.Serializable;
import java.util.List;

public class AddUserToChannel implements Serializable {
    private int channel_id;
    private List<String> user_name;

    public AddUserToChannel(List <String> user_name, int channel_id){
        this.user_name=user_name;
        this.channel_id=channel_id;
    }
    public List<String> getUser_name(){
        return user_name;
    }
    public int getChannel_id(){
        return channel_id;
    }
}
