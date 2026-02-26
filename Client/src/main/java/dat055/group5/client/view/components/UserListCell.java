package dat055.group5.client.view.components;

import dat055.group5.client.controller.ChannelListItemController;
import dat055.group5.client.controller.UserListItemController;
import dat055.group5.export.Channel;
import dat055.group5.export.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class UserListCell extends ListCell<String> {

    private FXMLLoader loader;
    private UserListItemController userListItemController;

    public UserListCell() {
        // initialization if needed
    }

    @Override
    protected void updateItem(String username, boolean empty) {
        super.updateItem(username, empty);

        if (empty || username == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Only load the FXML once per cell to save memory and CPU
            if (loader == null) {
                // Make sure to create this FXML file in your resources folder!
                loader = new FXMLLoader(getClass().getResource("/dat055/group5/client/components/channel-list-item.fxml"));
                try {
                    loader.load();
                    userListItemController = loader.getController();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            userListItemController.updateUI(username);

            setGraphic(userListItemController.getParent());
        }
    }
}