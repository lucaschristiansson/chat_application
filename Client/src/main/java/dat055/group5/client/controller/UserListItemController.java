package dat055.group5.client.controller;

import dat055.group5.export.Channel;
import dat055.group5.export.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class UserListItemController {

    @FXML
    private HBox parent;

    private String username;

    @FXML
    private Label userNameLabel;

    public void updateUI(String username) {
        this.username = username;
        userNameLabel.setText("# " + username);
    }

    public HBox getParent(){
        return parent;
    }
}