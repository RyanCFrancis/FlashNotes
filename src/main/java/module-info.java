module org.flashnotes.flashnotes {
    requires javafx.controls;
    requires javafx.fxml;
    requires google.cloud.firestore;
    requires firebase.admin;
    requires com.google.auth.oauth2;
    requires com.google.auth;
    requires com.google.api.apicommon;
    requires google.cloud.storage;
    requires google.cloud.core;
    requires java.desktop;
    requires org.slf4j;

    opens org.flashnotes.flashnotes to javafx.fxml;
//    exports org.flashnotes.flashnotes.Firebase;
    exports org.flashnotes.flashnotes.View;
    exports org.flashnotes.flashnotes.Model;
//    opens org.flashnotes.flashnotes.Firebase to javafx.fxml;
    opens org.flashnotes.flashnotes.View to javafx.fxml;
    opens org.flashnotes.flashnotes.Model to javafx.fxml;
}