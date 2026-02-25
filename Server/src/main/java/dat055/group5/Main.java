package dat055.group5;

import java.sql.SQLException;
import java.time.Instant;

public class Main {
    public static void main() {
        try {
            Server server = new Server();
            server.start(4000);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
