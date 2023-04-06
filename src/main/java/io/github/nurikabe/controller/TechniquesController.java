package io.github.nurikabe.controller;

import io.github.nurikabe.techniques.Techniques;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Contrôleur représentant l'affichage des techniques.
 *
 * @see Techniques
 */
public class TechniquesController extends VBox {
    private final Stage stage;
    private final Scene scenePrecedente;

    /**
     * Le constructeur de la classe TechniquesController
     *
     * @param stage           la scène courante
     * @param scenePrecedente la scène précédente, qui sera utilisé par le bouton retour
     */
    public TechniquesController(Stage stage, Scene scenePrecedente) {
        this.stage = stage;
        this.scenePrecedente = scenePrecedente;
    }

    @FXML
    private void onBackAction(ActionEvent event) {
        stage.setScene(scenePrecedente);
    }
}
