package io.github.nurikabe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Classe abstraite représentant les contrôles de retour à l'écran précédent ainsi que le titre.
 */
public abstract class FenetreController extends VBox {
    protected final Stage stage;
    protected final Parent controleurPrecedent;

    @FXML protected Label labelTitre;

    protected FenetreController(Stage stage) {
        this.stage = stage;
        this.controleurPrecedent = stage.getScene().getRoot();
    }

    protected void ecranPrecedent() {
        stage.getScene().setRoot(controleurPrecedent);
    }

    @FXML
    private void onBackAction(ActionEvent event) {
        ecranPrecedent();
    }
}
