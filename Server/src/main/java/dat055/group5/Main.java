package dat055.group5;

import java.sql.SQLException;
import java.time.Instant;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main() {

        try {
            Server server = new Server(5000);
            server.start();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
