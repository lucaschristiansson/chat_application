package dat055.group5.export;

import java.io.Serializable;
import java.util.UUID;

public class NetworkPackage implements Serializable {

    PackageType type;
    UUID id;
    Object data;


    public NetworkPackage (PackageType type, Object data) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.data = data;
    }
    public NetworkPackage (UUID id,PackageType type, Object data) {
        this.id = id;
        this.type = type;
        this.data = data;
    }

    public UUID getID(){
        return id;
    }
    public void setID(UUID id) {
        this.id = id;
    }

    public PackageType getType() {
        return this.type;
    }

    public Object getData() {
        return this.data;
    }

    public void setType(PackageType type){
        this.type = type;
    }

    public void setData(Object data){
        this.data = data;
    }


    }



