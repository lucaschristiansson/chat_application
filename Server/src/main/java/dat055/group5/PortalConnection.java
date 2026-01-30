package dat055.group5;

import java.sql.*; // JDBC stuff.
import java.util.Properties;

public class PortalConnection {

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
        return "jdbc:postgresql://localhost:5432/" + db;
    }

    private Connection conn;

    public PortalConnection() throws SQLException, ClassNotFoundException {
        this(getDb(), getDbUser(), getDbPassword());
    }

    public PortalConnection(String db, String user, String pwd) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        conn = DriverManager.getConnection(db, props);
    }

    public void printMessages() {
        String query = "SELECT time, sender_name, content, image_url FROM Messages ORDER BY time ASC";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String time = rs.getString("time");
                String sender = rs.getString("sender_name");
                String content = rs.getString("content");
                String image = rs.getString("image_url");

                System.out.println("--------------------------------");

                System.out.println(content);

                if (image != null) {
                    System.out.println("[Image: " + image + "]");
                }

                System.out.println(sender + " @ " + time);
            }
            System.out.println("--------------------------------\n");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static String getError(SQLException e){
       String message = e.getMessage();
       int ix = message.indexOf('\n');
       if (ix > 0) message = message.substring(0, ix);
       message = message.replace("\"","\\\"");
       return message;
    }
}