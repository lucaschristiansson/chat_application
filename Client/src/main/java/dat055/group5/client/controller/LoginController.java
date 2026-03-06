package dat055.group5.client.controller;

import dat055.group5.client.Driver;
import dat055.group5.export.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class LoginController {
    private Driver driver;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @FXML @SuppressWarnings("unchecked")
    public void onLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isBlank() || password.isBlank()) {
            errorLabel.setText("Username and password cannot be empty.");
            return;
        }

        try {
            User user = new User(username, password);
            NetworkPackage request = driver.getClient().sendRequestBlocking(PackageType.LOGIN, user);
            if (request == null || !(boolean) request.getData()) {
                errorLabel.setText("Wrong username or password");
                return;
            }

            driver.getModel().setClientUser(user);
            driver.getClient().sendRequestAsync(
                    driver.getChannelClientPacker().getAllChannelsForUser(
                            driver.getModel().getClientUser().getUsername()),
                    networkPackage -> {
                        driver.getChannelClientManager().getAllChannelsForUser((List<Channel>) networkPackage.getData());
                        driver.getModel().setActiveChannel(driver.getModel().getChannels().getFirst());

                        driver.getClient().sendRequestAsync(
                                driver.getMessageClientPacker().getMessagesByChannel(
                                        driver.getModel().getActiveChannel().getChannelID()),
                                networkPackage2 -> driver.getMessageClientManager()
                                        .getMessagesByChannel((List<Message>) networkPackage2.getData())
                        );
                    });

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dat055/group5/client/views/chat-view.fxml"));
            Parent chatRoot = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene chatScene = new Scene(chatRoot, 900, 600);

            ChatController chatController = loader.getController();
            chatController.setDriver(driver);

            stage.setScene(chatScene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Error loading the chat interface.");
        }
    }

    @FXML
    public void onSignup(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dat055/group5/client/views/signup-view.fxml"));
            Parent signupRoot = loader.load();

            SignupController signupController = loader.getController();
            signupController.setDriver(driver);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(signupRoot, 400, 450));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Error loading the sign up interface.");
        }
    }
}