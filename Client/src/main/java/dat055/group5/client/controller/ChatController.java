package dat055.group5.client.controller;

import dat055.group5.client.Model.Client;
import dat055.group5.client.Model.Model;
import dat055.group5.client.Driver;
import dat055.group5.client.view.components.ChannelListCell;
import dat055.group5.client.view.components.ChatListCell;
import dat055.group5.client.view.components.ImageListCell;
import dat055.group5.export.*;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Logger;


public class ChatController {
    private Client client;
    private Driver driver;

    @FXML public ListView<Channel> channelList;
    @FXML private ListView<Message> chatList;
    @FXML private ListView<String> userList;
    @FXML private ListView<Image> imageList;

    @FXML
    private SplitPane mainSplitPane;

    @FXML private TextArea messageContentField;

    @FXML
    private VBox channelsVBox;

    @FXML
    private VBox usersVBox;

    @FXML
    private Label currentChannelLabel;

    @SuppressWarnings("unchecked")
    public void setDriver(Driver driver) {
        this.driver = driver;
        this.client = driver.getClient();

        client.sendRequestAsync(driver.getChannelClientPacker().getAllChannelsForUser(driver.getModel().getClientUser().getUsername()), (networkPackage) -> {
            driver.getChannelClientManager().getAllChannelsForUser((List<Channel>) networkPackage.getData());
        });

        ObservableList<Message> messages = driver.getModel().getMessages();

        channelList.setItems(driver.getModel().getChannels());
        chatList.setItems(messages);
        userList.setItems(driver.getModel().getUsersInActiveChannel());
        currentChannelLabel.textProperty().bind(driver.getModel().getActiveChannelProperty().asString());

        messages.addListener((ListChangeListener<Message>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    Platform.runLater(() -> {
                        chatList.scrollTo(chatList.getItems().size() - 1);
                    });
                    break;
                }
            }
        });
    }

    @FXML
    public void initialize() {

        chatList.setCellFactory(_ -> new ChatListCell());
        channelList.setCellFactory(_ -> new ChannelListCell());
        imageList.setCellFactory(_ -> new ImageListCell());

        imageList.visibleProperty().bind(Bindings.isNotEmpty(imageList.getItems()));
        imageList.managedProperty().bind(imageList.visibleProperty());

        channelList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                onChannelSelected(newValue);
            }
        });

        chatList.getItems().addListener((ListChangeListener<Message>) change -> {

        });


    }
    @SuppressWarnings("unchecked")
    private void onChannelSelected(Channel selectedChannel) {
        System.out.println("User pressed channel: " + selectedChannel.getChannelName());

        driver.getModel().setActiveChannel(selectedChannel);

        client.sendRequestAsync(driver.getMessageClientPacker().getMessagesByChannel(selectedChannel.getChannelID()), (networkPackage) ->
                driver.getMessageClientManager().getMessagesByChannel((List<Message>) networkPackage.getData())
        );

        client.sendRequestAsync(driver.getChannelClientPacker().getChannel(selectedChannel.getChannelID().toString()),
                (networkPackage) -> driver.getChannelClientManager().getAllUsersInChannel((List<String>) networkPackage.getData())
        );
    }
//(driver.getModel().getActiveChannel().getChannelID())
    @FXML
    public void onToggleChannels(ActionEvent event) {
        if (mainSplitPane.getItems().contains(channelsVBox)) {
            mainSplitPane.getItems().remove(channelsVBox);
        } else {
            mainSplitPane.getItems().addFirst( channelsVBox);
            mainSplitPane.setDividerPosition(0, 0.2);
        }
    }

    @FXML
    public void onToggleUsers(ActionEvent event) {
        if (mainSplitPane.getItems().contains(usersVBox)) {
            mainSplitPane.getItems().remove(usersVBox);
        } else {
            mainSplitPane.getItems().addLast(usersVBox);
            mainSplitPane.setDividerPosition(mainSplitPane.getDividers().size() - 1, 0.8);
        }
    }

    @FXML
    public void onAttach(ActionEvent event) throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(((Node) event.getTarget()).getScene().getWindow());

        Image image = new Image(file.toURI().toURL().toExternalForm());

        imageList.getItems().add(image);
    }

    @FXML
    public void onSend(ActionEvent event) {
        client.sendMessagePackage(messageContentField.getText(), imageList.getItems());

        Platform.runLater(() -> {
            messageContentField.clear();
            imageList.getItems().clear();
        });

    }

    public void onAddUser(ActionEvent actionEvent) {
        openDialog("/dat055/group5/client/views/add-user-dialog.fxml",
                "Add users to channel", 320, 380,
                (AddUserController ctrl) -> ctrl.setDriver(driver));
    }

    public void onAddChannel(ActionEvent actionEvent) {
        openDialog("/dat055/group5/client/views/add-channel-dialog.fxml",
                "Create channel", 360, 420,
                (AddChannelController ctrl) -> ctrl.setDriver(driver));
    }

    private <T> void openDialog(String fxmlPath, String title, double width, double height,
                                java.util.function.Consumer<T> setup) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            T controller = loader.getController();
            setup.accept(controller);

            Stage dialog = new Stage();
            dialog.setTitle(title);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(channelList.getScene().getWindow());
            dialog.setScene(new Scene(root, width, height));
            dialog.setResizable(false);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
