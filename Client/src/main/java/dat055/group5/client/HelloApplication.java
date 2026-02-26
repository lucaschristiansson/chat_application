package dat055.group5.client;

import dat055.group5.client.Model.Client;
import dat055.group5.client.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static Driver driver;

    public static void setDriver(Driver newDriver) {
        driver = newDriver;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/login-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 400, 400);

        LoginController loginController = fxmlLoader.getController();
        loginController.setDriver(driver);

        stage.setTitle("Chat Client - Login");
        stage.setScene(scene);
        stage.show();
    }
}