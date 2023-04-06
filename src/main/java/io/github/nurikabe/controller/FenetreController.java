package io.github.nurikabe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Classe abstraite représentant les contrôles de retour à l'écran précédent ainsi que le titre.
 */
public abstract class FenetreController extends VBox {
    protected final Stage stage;
    protected final Scene scenePrecedente;

    @FXML protected Label labelTitre;

    protected FenetreController(Stage stage, Scene scenePrecedente) {
        this.stage = stage;
        this.scenePrecedente = scenePrecedente;
    }

    protected void ecranPrecedent() {
        stage.setScene(scenePrecedente);
    }

    @FXML
    private void onBackAction(ActionEvent event) {
        ecranPrecedent();
    }
}
