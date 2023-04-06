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
    /**
     * Variable d'instance privée qui stocke le stage actuel
     */
    private final Stage stage;

    /**
     * Variable d'instance privée qui implémente la scène précédente, elle est utilisée par la fonction qui gère le bouton retour
     */
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

    /**
     * Méthode qui est appelée quand on clique sur la grille du niveau1
     * elle appelle le controller du plateau qui va charger la grille
     *
     * @param event l'évènement qui a activé la méthode, ici le clic
     */
    @FXML
    private void onBackAction(ActionEvent event) {
        stage.setScene(scenePrecedente);
    }
}
