package dat055.group5.client.controller;

import dat055.group5.export.Channel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class ChannelListItemController {

    @FXML
    private HBox parent;

    private Channel channel;

    @FXML
    private Label channelNameLabel;

    public void updateUI(Channel channel) {
        this.channel = channel;
        channelNameLabel.setText("# " + channel.getChannelName());
    }

    public HBox getParent(){
        return parent;
    }
}