package org.flashnotes.flashnotes.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class JavaFXController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}