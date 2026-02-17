package dat055.group5;

import java.sql.SQLException;
import java.time.Instant;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        /*
        Driver driver = new Driver();
        driver.main();
        */

        try {
            Server server = new Server();
            server.start(5000);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
