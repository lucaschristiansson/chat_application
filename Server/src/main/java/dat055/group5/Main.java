package dat055.group5;

import java.sql.SQLException;
import java.time.Instant;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

        try {
            PortalConnection db = new PortalConnection();
            db.addMessage(Instant.now(), "user1", 2, "tellooo", "gulp.png");
            db.printMessages();

        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            System.err.println(e.getMessage());
        }
        try {
            Server s = new Server(5000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
