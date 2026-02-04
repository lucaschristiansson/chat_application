package dat055.group5;

import java.sql.*; // JDBC stuff.
import java.time.Instant;
import java.util.Properties;

/**
 * 
 */
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
        return "jdbc:postgresql://localhost:1234/" + db;
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

    public void addMessage(Instant time, String sender_name, int channel_id, String content, String image_url){
        String sql= "INSERT INTO Messages VALUES(?, ?, ?, ?, ?)";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setTimestamp(1, Timestamp.from(time));
            ps.setString(2, sender_name);
            ps.setInt(3, channel_id);
            ps.setString(4, content);
            ps.setString(5, image_url);

            ps.executeUpdate();
        } catch (SQLException e) {
            getError(e);
            e.printStackTrace();
        }
    }

    public void printMessages() {
        String query = "SELECT M.time, M.sender_name, M.content, M.image_url, C.name AS channel_name FROM Messages M JOIN Channels C ON M.channel_id=C.id ORDER BY time ASC";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String time = rs.getString("time");
                String sender = rs.getString("sender_name");
                String content = rs.getString("content");
                String image = rs.getString("image_url");
                String channel = rs.getString("channel_name");

                System.out.println("--------------------------------");

                System.out.println(content);

                if (image != null) {
                    System.out.println("[Image: " + image + "]");
                }

                System.out.println("In " + channel + " from " + sender + " @ " + time);
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