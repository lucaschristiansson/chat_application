module dat055.group5.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens dat055.group5.client to javafx.fxml;
    exports dat055.group5.client;
}