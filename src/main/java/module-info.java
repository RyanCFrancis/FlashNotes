module org.flashnotes.flashnotes {
    requires javafx.controls;
    requires javafx.fxml;
    requires google.cloud.firestore;
    requires firebase.admin;
    requires com.google.auth.oauth2;
    requires com.google.auth;
    requires com.google.api.apicommon;
    requires google.cloud.storage;


    opens org.flashnotes.flashnotes to javafx.fxml;
    exports org.flashnotes.flashnotes;
    exports org.flashnotes.flashnotes.Firebase;
    opens org.flashnotes.flashnotes.Firebase to javafx.fxml;
}