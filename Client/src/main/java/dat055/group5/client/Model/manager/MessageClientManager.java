package dat055.group5.client.Model.manager;

import dat055.group5.client.Driver;
import dat055.group5.client.Model.Model;
import dat055.group5.export.Message;
import dat055.group5.export.MessageManager;
import dat055.group5.export.NetworkPackage;
import dat055.group5.export.PackageType;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class MessageClientManager implements MessageManager<Void, List<Message>> {

    private final Driver driver;

    public MessageClientManager(Driver driver){
        this.driver = driver;
    }


    @Override
    public boolean addMessage(Message message) {
        driver.getModel().addMessage(message);
        return true;
    }

    @Override
    public Void getMessagesByChannel(List<Message> messages) {
        for(Message message : messages){
            driver.getModel().addMessage(message);
        }
        return null;
    }

}
