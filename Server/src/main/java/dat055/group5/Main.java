package dat055.group5;

import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        try {
            PortalConnection db = new PortalConnection();
            db.printMessages();

        } catch (SQLException | ClassNotFoundException | RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }
}
