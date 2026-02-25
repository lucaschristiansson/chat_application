package dat055.group5.client.view.components;

import dat055.group5.client.controller.ChatMessageController;
import dat055.group5.export.Channel;
import dat055.group5.export.Message;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class ChatListCell extends ListCell<Message> {

    private FXMLLoader loader;
    private ChatMessageController chatMessageController;

    //setup javafx components

    public ChatListCell() {
        //initialize view stuff
    }


    @Override
    protected void updateItem(Message message, boolean empty){
        super.updateItem(message, empty);

        if (empty || message == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("/dat055/group5/client/components/chat-message.fxml"));
                try {
                    loader.load();
                    chatMessageController = loader.getController();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            chatMessageController.updateUI(message);

            setGraphic(chatMessageController.getParent());
        }

    }

}
