package dat055.group5.client.controller;

import dat055.group5.client.Model.Client;
import dat055.group5.client.Model.Model;
import dat055.group5.client.view.components.ChannelListCell;
import dat055.group5.client.view.components.ChatListCell;
import dat055.group5.export.*;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.application.Platform;

import java.util.List;


public class ChatController {
    private Client client;
    private Model model;

    @FXML public ListView<Channel> channelList;
    @FXML private ListView<Message> chatList;
    @FXML private ListView<String> userList;
    @FXML
    private SplitPane mainSplitPane;

    @FXML private TextArea messageContentField;

    @FXML
    private VBox channelsVBox;

    @FXML
    private VBox usersVBox;

    @FXML
    private Label currentChannelLabel;

    public void setModel(Model model) {
        this.model = model;

        channelList.setItems(model.getChannels());
        chatList.setItems(model.getMessages());
        userList.setItems(model.getUsersInActiveChannel());
        currentChannelLabel.textProperty().bind(model.getActiveChannelProperty().asString());

    }

    public void setClient(Client client){

        this.client = client;
        
        client.setMessageListener(message -> {
            Platform.runLater(() -> {
                if (model.getActiveChannel() != null &&
                    message.getChannel().equals(model.getActiveChannel().getChannelID())) {
                    chatList.getItems().add(message);
                }
            });
        });
        client.sendRequestAsync(new NetworkPackage(PackageType.GET_CHANNELS_FOR_USER, client.getUsername()), (e) ->{
            System.out.println(e.getData());
            if(e.getType() == PackageType.GET_CHANNELS_FOR_USER){
                try{
                    if(e.getData() instanceof List<?> list){
                        List<Channel> channels = (List<Channel>) list;
                        Platform.runLater(() -> {
                            for(Channel channel : channels){
                                channelList.getItems().add(channel);
                            }
                        });
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }

    @FXML
    public void initialize() {
        chatList.setCellFactory(_ -> new ChatListCell());
        channelList.setCellFactory(_ -> new ChannelListCell());

        channelList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                onChannelSelected(newValue);
            }
        });

        chatList.getItems().addListener((ListChangeListener<Message>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    Platform.runLater(() -> {
                        Platform.runLater(() -> {
                            chatList.scrollTo(chatList.getItems().size() - 1);
                        });
                    });
                    break;
                }
            }
        });

    }

    private void onChannelSelected(Channel selectedChannel) {
        System.out.println("User pressed channel: " + selectedChannel.getChannelName());

        currentChannelLabel.setText(selectedChannel.getChannelName());
        model.setActiveChannel(selectedChannel);

        client.sendRequestAsync(new NetworkPackage(PackageType.GET_MESSAGES_BY_CHANNEL, selectedChannel.getChannelID()), (e) ->{
            System.out.println(e.getData());
            if(e.getType() == PackageType.GET_MESSAGES_BY_CHANNEL){
                try{
                    if(e.getData() instanceof List<?> list){
                        List<Message> messages = (List<Message>) list;
                        Platform.runLater(() -> {
                            for(Message message : messages){
                                model.addMessage(message);
                            }
                        });
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        client.sendRequestAsync(new NetworkPackage(PackageType.GET_USER_IN_CHANNEL, selectedChannel.getChannelID()), (e) ->{
            System.out.println(e.getData());
            if(e.getType() == PackageType.GET_USER_IN_CHANNEL){
                try{
                    if(e.getData() instanceof List<?> list){
                        List<String> users = (List<String>) list;
                        Platform.runLater(() -> {
                            for(String user : users){
                                userList.getItems().add(user);
                            }
                        });
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

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
    public void onSend(ActionEvent event) {
        Platform.runLater(() -> {
            client.sendMessagePackage(messageContentField.getText());
            messageContentField.clear();
        });

    }

    public void onAddUser(ActionEvent actionEvent) {
    }

    public void onAddChannel(ActionEvent actionEvent) {
    }
}
