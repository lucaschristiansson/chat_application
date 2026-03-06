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
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AddUserController {

    private Driver driver;

    @FXML private Label channelLabel;
    @FXML private ListView<String> userListView;
    @FXML private Label errorLabel;

    @FXML
    public void initialize() {
        userListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        errorLabel.managedProperty().bind(errorLabel.textProperty().isNotEmpty());
        errorLabel.visibleProperty().bind(errorLabel.textProperty().isNotEmpty());
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
        Channel active = driver.getModel().getActiveChannel();
        if (active != null) {
            channelLabel.setText("Channel: " + active.getChannelName());
        }
        loadUsers();
    }

    @SuppressWarnings("unchecked")
    private void loadUsers() {
        Channel active = driver.getModel().getActiveChannel();
        driver.getClient().sendRequestAsync(
                driver.getChannelClientPacker().getAllUsersInChannel(active.getChannelID().toString()),
                membersPackage -> {
                    driver.getClient().sendRequestAsync(
                            new NetworkPackage(PackageType.GET_USERS, null),
                            usersPackage -> {
                                List<String> allUsers = (List<String>) usersPackage.getData();
                                List<String> available = new ArrayList<>();
                                for (String user : allUsers) {
                                    if(!user.equals(driver.getModel().getClientUser().getUsername())) {
                                        available.add(user);
                                    }
                                }
                                Platform.runLater(() -> userListView.getItems().setAll(available));
                            });
                });
    }

    @FXML @SuppressWarnings("unchecked")
    public void onAdd(ActionEvent event) {
        List<String> selected = List.copyOf(userListView.getSelectionModel().getSelectedItems());
        if (selected.isEmpty()) {
            errorLabel.setText("Please select at least one user.");
            return;
        }

        Channel active = driver.getModel().getActiveChannel();
        if (active == null) {
            errorLabel.setText("No active channel selected.");
            return;
        }

        AddUserWithChannel data = new AddUserWithChannel(selected, active.getChannelID());
        driver.getClient().sendNetworkPackage(new NetworkPackage(PackageType.ADD_USER_TO_CHANNEL, data));

        // refrehs the users in the channel
        driver.getClient().sendRequestAsync(
                driver.getChannelClientPacker().getAllUsersInChannel(active.getChannelID().toString()),
                np -> driver.getChannelClientManager().getAllUsersInChannel((List<String>) np.getData())
        );

        // refresh the messages
        driver.getClient().sendRequestAsync(
                driver.getMessageClientPacker().getMessagesByChannel(active.getChannelID()),
                np -> driver.getMessageClientManager().getMessagesByChannel((List<Message>) np.getData())
        );

        close(event);
    }

    @FXML
    public void onCancel(ActionEvent event) {
        close(event);
    }

    private void close(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
}



