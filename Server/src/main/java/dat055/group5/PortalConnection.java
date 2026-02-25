package dat055.group5;

import java.sql.*; // JDBC stuff.
import java.time.Instant;
import java.util.Properties;

/**
 * 
 */
public class PortalConnection {
    private final Connection connection;
    private final Driver driver;

    public PortalConnection(Driver driver) throws SQLException, ClassNotFoundException{
        this(getDb(), getDbUser(), getDbPassword(), driver);
    }

    private PortalConnection(String db, String user, String pwd, Driver driver) throws SQLException, ClassNotFoundException{

       // Class.forName("org.postgresql.Driver");
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        this.connection = DriverManager.getConnection(db, props);
        this.driver = driver;
    }

    public static String getDbPassword() {
        String password = System.getenv("POSTGRES_PASSWORD");

        if (password == null) {
            throw new RuntimeException("Missing Environment Variable: POSTGRES_PASSWORD");
        }
        return password;
    }

    public static String getDbUser() {
        String user = System.getenv("POSTGRES_USER");

        if (user == null) {
            throw new RuntimeException("Missing Environment Variable: POSTGRES_USER");
        }
        return user;
    }

    public static String getDb() {
        String db = System.getenv("POSTGRES_DB");

        if (db == null) {
            throw new RuntimeException("Missing Environment Variable: POSTGRES_DB");
        }
        return "jdbc:postgresql://localhost:5433/" + db;
    }


    public static String getError(SQLException e){
       String message = e.getMessage();
       int ix = message.indexOf('\n');
       if (ix > 0) message = message.substring(0, ix);
       message = message.replace("\"","\\\"");
       return message;
    }
    public Connection getConnection(){
        return connection;
    }
}