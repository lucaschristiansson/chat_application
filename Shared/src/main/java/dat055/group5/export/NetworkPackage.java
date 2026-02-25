package dat055.group5.export;

import java.io.Serializable;

public class NetworkPackage implements Serializable {

    PackageType type;
    Object data;


    public NetworkPackage (PackageType type, Object data) {
        this.type = type;
        this.data = data;
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



