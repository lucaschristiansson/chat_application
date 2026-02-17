package dat055.group5;

public class NetworkPackage {
    String type;
    Object data;

    public NetworkPackage (String type, Object data) {
        this.type = type;
        this.data = data;
    }
    
    public String getType() {
        return this.type;
    }

    public Object getData() {
        return this.data;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setData(Object data){
        this.data = data;
    }


    }



