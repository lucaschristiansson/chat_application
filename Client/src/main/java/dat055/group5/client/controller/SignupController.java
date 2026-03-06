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

public class SignupController {

    private Driver driver;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label errorLabel;

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @FXML @SuppressWarnings("unchecked")
    public void onSignup(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            errorLabel.setText("All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorLabel.setText("Passwords do not match.");
            return;
        }

        User newUser = new User(username, password);
        NetworkPackage signupResponse = driver.getClient().sendRequestBlocking(PackageType.CREATE_USER, newUser);

        if (signupResponse == null || !(boolean) signupResponse.getData()) {
            errorLabel.setText("Username already taken");
            return;
        }

        NetworkPackage loginResponse = driver.getClient().sendRequestBlocking(PackageType.LOGIN, newUser);
        if (loginResponse == null || !(boolean) loginResponse.getData()) {
            errorLabel.setText("Account created but login failed, log in manually instead.");
            navigateToLogin(event);
            return;
        }

        driver.getModel().setClientUser(newUser);
        driver.getClient().sendRequestAsync(
                driver.getChannelClientPacker().getAllChannelsForUser(username),
                networkPackage -> {
                    List<Channel> channels = (List<Channel>) networkPackage.getData();
                    driver.getChannelClientManager().getAllChannelsForUser(channels);
                    if (!channels.isEmpty()) {
                        driver.getModel().setActiveChannel(driver.getModel().getChannels().getFirst());
                        driver.getClient().sendRequestAsync(
                                driver.getMessageClientPacker().getMessagesByChannel(
                                        driver.getModel().getActiveChannel().getChannelID()),
                                networkPackage2 -> driver.getMessageClientManager()
                                        .getMessagesByChannel((List<Message>) networkPackage2.getData())
                        );
                    }
                });

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dat055/group5/client/views/chat-view.fxml"));
            Parent chatRoot = loader.load();

            ChatController chatController = loader.getController();
            chatController.setDriver(driver);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(chatRoot, 900, 600));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Error loading the chat interface.");
        }
    }

    @FXML
    public void onBack(ActionEvent event) {
        navigateToLogin(event);
    }

    private void navigateToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dat055/group5/client/views/login-view.fxml"));
            Parent loginRoot = loader.load();

            LoginController loginController = loader.getController();
            loginController.setDriver(driver);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loginRoot, 400, 400));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Error navigating back to login");
        }
    }
}

