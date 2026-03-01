package dat055.group5.client.view.components;

import dat055.group5.client.controller.ChannelListItemController;
import dat055.group5.export.Channel;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class ChannelListCell extends ListCell<Channel> {

    private FXMLLoader loader;
    private ChannelListItemController channelListItemController;

    public ChannelListCell() {
        // initialization if needed
    }

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