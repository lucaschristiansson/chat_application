package dat055.group5.client;
import javafx.application.Application;

/**
 * Launcher for the client.
 * Initializes view and all client-components via {@link Driver}
 */
public class Launcher {
    static void main(String[] args) {
        Driver driver = new Driver();
        Login.setDriver(driver);
        Application.launch(Login.class, args);
    }


}
