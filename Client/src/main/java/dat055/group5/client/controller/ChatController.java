package dat055.group5.client.controller;

import dat055.group5.client.view.components.ChannelListCell;
import dat055.group5.client.view.components.ChatListCell;
import dat055.group5.export.Channel;
import dat055.group5.export.Message;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;


public class ChatController {

    @FXML public ListView<Channel> channelList;
    @FXML private ListView<Message> chatList;


    @FXML
    public void initialize() {
        chatList.setCellFactory(_ -> new ChatListCell());
        channelList.setCellFactory(_ -> new ChannelListCell());

        chatList.getItems().add(new Message("Theo", "hej", 0));
    }

    public void onSend() {

    }
}
