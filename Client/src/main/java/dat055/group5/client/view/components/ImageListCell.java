package dat055.group5.client.view.components;

import dat055.group5.client.controller.ImageListItemController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;

import java.io.IOException;

public class ImageListCell extends ListCell<Image> {

    private ImageListItemController imageListItemController;
    private FXMLLoader loader;

    public ImageListCell() {}

    @Override
    protected void updateItem(Image image, boolean empty) {
        super.updateItem(image, empty);

        if (empty || image == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("/dat055/group5/client/components/image-item.fxml"));
                try {
                    loader.load();
                    imageListItemController = loader.getController();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            imageListItemController.updateUI(image);
            setGraphic(imageListItemController.getParent());
        }
    }
}