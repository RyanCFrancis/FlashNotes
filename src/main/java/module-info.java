module org.flashnotes.flashnotes {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.flashnotes.flashnotes to javafx.fxml;
    exports org.flashnotes.flashnotes;
}