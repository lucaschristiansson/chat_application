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

public class MessageClientManager implements MessageManager {
    private Model model;
    private final Driver driver;


    public MessageClientManager(Driver driver){
        this.driver = driver;
        this.model = driver.getModel();
    }


    @Override
    public boolean addMessage(Message message) {
        model.addMessage(message);
        return true;
    }

    @Override
    public List<Message> getMessagesByChannel(int channelId) {
        driver.getClient().sendRequestAsync(new NetworkPackage(PackageType.GET_MESSAGES_BY_CHANNEL, model.getActiveChannel().getChannelID()), (networkPackage) ->{
            System.out.println(networkPackage.getData());
            System.out.println("hello!");
            if(networkPackage.getType() == PackageType.GET_MESSAGES_BY_CHANNEL){
                try{
                    if(networkPackage.getData() instanceof List<?> list){
                        for(Object message : list){
                            if(message instanceof Message m){
                                model.addMessage(m);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return List.of();
    }

}
