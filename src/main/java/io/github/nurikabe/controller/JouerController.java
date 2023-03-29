/**
 * Fichier JouerController.java
 */

// Package GitHub
package io.github.nurikabe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * La classe JouerController implémente le controleur du bouton jouer en utilisant la super-classe VBox qui est la racine du menu principal
 */
public class JouerController extends VBox {
    /**
     * Variable d'instance privée qui implémente le stage du bouton jouer
     */
    private final Stage stage;

    /**
     * Variable d'instance privée qui implémente la scène précédente (elle est utilisée par le bouton retour pour remettre le stage courant en tant qu'actuel)
     */
    private final Scene scenePrecedente;

    /**
     * Constructeur de la classe JouerController
     *
     * @param stage           le stage actuel
     * @param scenePrecedente cette variable est utilisée par le bouton précédent pour revenir à la scène précédente
     */
    public JouerController(Stage stage, Scene scenePrecedente) {
        this.stage = stage;
        this.scenePrecedente = scenePrecedente;
    }

    /**
     * Méthode qui est appelée par le bouton retour, cette méthode permet de revenir à l'écran d'avant
     */
    @FXML
    private void onBackAction(ActionEvent event) {
        stage.setScene(scenePrecedente);
    }
}
