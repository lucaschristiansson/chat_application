package dat055.group5.export;

import java.io.Serializable;
import java.util.UUID;

public class NetworkPackage implements Serializable {
    UUID id;
    Actions type;
    Object data;

    public NetworkPackage (Actions type, Object data) {
        id = UUID.randomUUID();
        this.type = type;
        this.data = data;
    }

    public UUID getID(){
        return id;
    }
    public void setID(UUID id) {
        this.id = id;
    }

    public Actions getType() {
        return this.type;
    }

    public Object getData() {
        return this.data;
    }

    public void setType(Actions type){
        this.type = type;
    }

    public void setData(Object data){
        this.data = data;
    }


    }



