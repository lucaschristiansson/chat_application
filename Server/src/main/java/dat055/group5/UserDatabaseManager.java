package dat055.group5;

import java.sql.Connection;

public class UserDatabaseManager {
    private final Driver driver;
    private final Connection connection;

    public UserDatabaseManager(Driver driver){
        this.driver = driver;
        this.connection = driver.getPortalConnection().getConnection();
    }



}
