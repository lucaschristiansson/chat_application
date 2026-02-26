package dat055.group5.client.controller;

import dat055.group5.client.Model.Client;
import dat055.group5.export.Actions;
import dat055.group5.export.NetworkPackage;
import dat055.group5.export.PackageType;
import dat055.group5.export.User;
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

public class LoginController {

    private Client client;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    public void onLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isBlank() || password.isBlank()) {
            errorLabel.setText("Username and password cannot be empty.");
            return;
        }

        try {
            NetworkPackage request = client.sendRequestBlocking(PackageType.LOGIN, new User(username, password));
            if(request == null || !(boolean)request.getData()){
                errorLabel.setText("Wrong username or password");
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dat055/group5/client/views/chat-view.fxml"));
            Parent chatRoot = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene chatScene = new Scene(chatRoot, 900, 600);
            stage.setScene(chatScene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Error loading the chat interface.");
        }
    }
}