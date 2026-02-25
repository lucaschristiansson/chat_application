package dat055.group5.client.controller;

import dat055.group5.export.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ChatMessageController {

    @FXML VBox parent;
    @FXML private Label userLabel;
    @FXML private Label messageLabel;


    public void updateUI(Message message) {
        userLabel.setText(message.getSender());
        messageLabel.setText(message.getContent());
    }

    public VBox getParent(){
        return parent;
    }
}
