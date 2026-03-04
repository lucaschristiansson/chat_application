package dat055.group5.client.controller;

import dat055.group5.export.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;

public class ChatMessageController {

    @FXML VBox parent;
    @FXML private Label userLabel;
    @FXML private Label messageLabel;
    @FXML private HBox imageList;

    public void updateUI(Message message) {
        userLabel.setText(message.getSender());
        messageLabel.setText(message.getContent());

        imageList.getChildren().clear();
        if (message.getImageBytes() != null && !message.getImageBytes().isEmpty()) {

            for (byte[] imageData : message.getImageBytes()) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image fxImage = new Image(bis);

                ImageView imageView = new ImageView(fxImage);

                imageView.setFitWidth(100);
                imageView.setPreserveRatio(true);

                imageList.getChildren().add(imageView);
            }
        }
    }

    public VBox getParent(){
        return parent;
    }
}
