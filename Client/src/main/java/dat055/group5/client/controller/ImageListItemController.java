package dat055.group5.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle; // <-- Import Rectangle

public class ImageListItemController {

    @FXML
    private ImageView imgView;

    @FXML
    private StackPane parent;

    private Image attachedImage;

    @FXML
    public void initialize() {
        Rectangle clip = new Rectangle(100, 100);

        clip.setArcWidth(24);
        clip.setArcHeight(24);

        imgView.setClip(clip);
    }

    @FXML
    public void onClose(ActionEvent actionEvent) {
        System.out.println("Removed image: " + attachedImage);
    }

    public void updateUI(Image image) {
        this.attachedImage = image;
        imgView.setImage(attachedImage);
    }

    public StackPane getParent(){
        return parent;
    }
}