package io.github.freya022.nurikabe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JouerController extends VBox {
    private final Stage stage;
    private final Scene scenePrecedente;

    public JouerController(Stage stage, Scene scenePrecedente) {
        this.stage = stage;
        this.scenePrecedente = scenePrecedente;
    }

    @FXML
    private void onBackAction(ActionEvent event) {
        stage.setScene(scenePrecedente);
    }
}
