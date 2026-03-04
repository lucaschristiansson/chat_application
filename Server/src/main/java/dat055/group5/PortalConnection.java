package dat055.group5;

import java.sql.*; // JDBC stuff.
import java.time.Instant;
import java.util.Properties;

/**
 * Establishes connection to the database
 */
public class PortalConnection {
    private final Connection connection;
    private final Driver driver;

    /**
     * Creates new PortalConnection with standard configuration for database
     * @param driver the {@link Driver} that handles database-operations
     * @throws SQLException If connection to database fails
     * @throws ClassNotFoundException
     */
    public PortalConnection(Driver driver) throws SQLException, ClassNotFoundException{
        this(getDb(), getDbUser(), getDbPassword(), driver);
    }

    /**
     * Main constructor that establishes database-connection
     * @param db        database URL
     * @param user      username for authentication
     * @param pwd       user password for authentication
     * @param driver    {@link Driver} for database management
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private PortalConnection(String db, String user, String pwd, Driver driver) throws SQLException, ClassNotFoundException{

       // Class.forName("org.postgresql.Driver");
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        this.connection = DriverManager.getConnection(db, props);
        this.driver = driver;
    }

    /**
     * Gets password
     * @return password as a String
     */
    public static String getDbPassword() {
        String password = System.getenv("POSTGRES_PASSWORD");

        if (password == null) {
            throw new RuntimeException("Missing Environment Variable: POSTGRES_PASSWORD");
        }
        return password;
    }

    /**
     * Gets username
     * @return username as String
     */
    public static String getDbUser() {
        String user = System.getenv("POSTGRES_USER");

        if (user == null) {
            throw new RuntimeException("Missing Environment Variable: POSTGRES_USER");
        }
        return user;
    }

    /**
     * Gets database URL
     * @return URL as String
     */
    public static String getDb() {
        String db = System.getenv("POSTGRES_DB");

        if (db == null) {
            throw new RuntimeException("Missing Environment Variable: POSTGRES_DB");
        }
        return "jdbc:postgresql://localhost:5431/" + db;
    }


    /**
     * Formats error message to human-readable
     * @param e the raised exception
     * @return formatted version of error-message.
     */
    public static String getError(SQLException e){
       String message = e.getMessage();
       int ix = message.indexOf('\n');
       if (ix > 0) message = message.substring(0, ix);
       message = message.replace("\"","\\\"");
       return message;
    }

    /**
     * Simple getter for current connection
     * @return connection of type Connection.
     */
    public Connection getConnection(){
        return connection;
    }
}