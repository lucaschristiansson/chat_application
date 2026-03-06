package dat055.group5.client.controller;

import dat055.group5.client.Driver;
import dat055.group5.export.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AddChannelController {

    private Driver driver;

    @FXML private TextField channelNameField;
    @FXML private ListView<String> userListView;
    @FXML private Label errorLabel;

    public void setDriver(Driver driver) {
        this.driver = driver;
        loadUsers();
    }

    @FXML
    public void initialize() {
        userListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        errorLabel.managedProperty().bind(errorLabel.textProperty().isNotEmpty());
        errorLabel.visibleProperty().bind(errorLabel.textProperty().isNotEmpty());
    }

    @SuppressWarnings("unchecked")
    private void loadUsers() {
        driver.getClient().sendRequestAsync(
                new NetworkPackage(PackageType.GET_USERS, null),
                networkPackage -> {
                    List<String> users = (List<String>) networkPackage.getData();
                    Platform.runLater(() -> userListView.getItems().setAll(users));
                });
    }

    @FXML
    public void onCreate(ActionEvent event) {
        String name = channelNameField.getText().trim();
        if (name.isBlank()) {
            errorLabel.setText("Channel name cannot be empty.");
            return;
        }

        Channel channel = new Channel(-1, name);

        driver.getClient().sendRequestAsync(
                driver.getChannelClientPacker().addChannel(channel),
                networkPackage -> {
                    Channel created = (Channel) networkPackage.getData();
                    if (created == null) {
                        Platform.runLater(() -> errorLabel.setText("Failed to create channel."));
                        return;
                    }

                    List<String> selected = new ArrayList<>(userListView.getSelectionModel().getSelectedItems());
                    String currentUser = driver.getModel().getClientUser().getUsername();
                    if (!selected.contains(currentUser)) {
                        selected.add(currentUser);
                    }

                    AddUserWithChannel payload = new AddUserWithChannel(selected, created.getChannelID());
                    driver.getClient().sendNetworkPackage(new NetworkPackage(PackageType.ADD_USER_TO_CHANNEL, payload));

                    Platform.runLater(() -> close(event));
                });
    }

    @FXML
    public void onCancel(ActionEvent event) {
        close(event);
    }

    private void close(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
}

