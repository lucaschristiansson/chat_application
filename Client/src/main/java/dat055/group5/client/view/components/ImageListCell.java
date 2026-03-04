package dat055.group5.client.view.components;

import dat055.group5.client.controller.ImageListItemController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/**
 * JavaFX code for Images
 */
public class ImageListCell extends ListCell<Image> {

    private ImageListItemController imageListItemController;
    private FXMLLoader loader;

    public ImageListCell() {}
    /**
     * Checks if there are images to display and gets the necessary FXML resources.
     * Clears text and graphics if there is nothing to display.
     * @param image
     * @param empty
     */
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

            imageListItemController.setup(image, getListView());
            setGraphic(imageListItemController.getParent());
        }
    }


}