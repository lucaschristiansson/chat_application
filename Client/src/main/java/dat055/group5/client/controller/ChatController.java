package dat055.group5.client.controller;

import dat055.group5.client.view.components.ChannelListCell;
import dat055.group5.client.view.components.ChatListCell;
import dat055.group5.export.Channel;
import dat055.group5.export.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;


public class ChatController {

    @FXML public ListView<Channel> channelList;
    @FXML private ListView<Message> chatList;
    @FXML
    private SplitPane mainSplitPane;

    @FXML
    private VBox channelsVBox;

    @FXML
    private VBox usersVBox;

    @FXML
    private Label currentChannelLabel;

    @FXML
    public void initialize() {
        chatList.setCellFactory(_ -> new ChatListCell());
        channelList.setCellFactory(_ -> new ChannelListCell());

        chatList.getItems().add(new Message("Theo", "hej", 0));
    }

    @FXML
    public void onToggleChannels(ActionEvent event) {
        if (mainSplitPane.getItems().contains(channelsVBox)) {
            mainSplitPane.getItems().remove(channelsVBox);
        } else {
            mainSplitPane.getItems().add(0, channelsVBox);
            mainSplitPane.setDividerPosition(0, 0.2);
        }
    }

    @FXML
    public void onToggleUsers(ActionEvent event) {
        if (mainSplitPane.getItems().contains(usersVBox)) {
            mainSplitPane.getItems().remove(usersVBox);
        } else {
            mainSplitPane.getItems().add(usersVBox);
            mainSplitPane.setDividerPosition(mainSplitPane.getDividers().size() - 1, 0.8);
        }
    }

    @FXML
    public void onSend(ActionEvent event) {
    }

    public void onAddUser(ActionEvent actionEvent) {
    }

    public void onAddChannel(ActionEvent actionEvent) {
    }
}
