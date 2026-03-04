package dat055.group5.client.view.components;

import dat055.group5.client.controller.ChannelListItemController;
import dat055.group5.export.Channel;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

import java.io.IOException;

/**
 * JavaFX code for the list of channels
 */
public class ChannelListCell extends ListCell<Channel> {

    private FXMLLoader loader;
    private ChannelListItemController channelListItemController;

    public ChannelListCell() {
        // initialization if needed
    }

    /**
     * Checks if there are channels to display and gets the necessary FXML resources.
     * Clears text and graphics if there is nothing to display.
     * @param channel
     * @param empty
     */
    @Override
    protected void updateItem(Channel channel, boolean empty) {
        super.updateItem(channel, empty);

        if (empty || channel == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("/dat055/group5/client/components/channel-list-item.fxml"));
                try {
                    loader.load();
                    channelListItemController = loader.getController();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            channelListItemController.updateUI(channel);

            setGraphic(channelListItemController.getParent());
        }
    }
}