package dat055.group5.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
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

    private ListView<Image> parentListView;

    public void setup(Image image, ListView<Image> parentListView) {
        this.attachedImage = image;
        this.parentListView = parentListView;
        imgView.setImage(image);
    }

    @FXML
    public void onClose(ActionEvent actionEvent) {
        if (parentListView != null && attachedImage != null) {
            parentListView.getItems().remove(attachedImage);
        }
    }

    public StackPane getParent(){
        return parent;
    }
}