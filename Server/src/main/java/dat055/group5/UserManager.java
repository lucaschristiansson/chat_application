package dat055.group5;

import java.sql.Connection;

public class UserManager {
    private final Driver driver;
    private Connection connection;

    public UserManager(Driver driver){
        this.driver=driver;
        connection=driver.getPortalConnection().getConnection();
    }



}
