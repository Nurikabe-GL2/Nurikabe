package io.github.nurikabe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * La classe implémentant le controller du bouton jouer utilisant la super-classe VBox qui est la racine du menu principal
 */
public class JouerController extends VBox {
    
    /**
     * Variable d'instance privé qui implémente le stage du bouton jouer
     */
    private final Stage stage;
    
    /**
     * Variable d'instance privé qui implémente la scène précédente (cette variable est utilisé par le bouton retour,
     *  pour remettre le stage courant en tant qu'actuel)
     */
    private final Scene scenePrecedente;

    /**
     * Constructeur de la classe JouerController
     * @param stage le stage actuel
     * @param scenePrecedente cette variable est utilisé par le bouton précédent pour revenir à la scène précédente
     */
    public JouerController(Stage stage, Scene scenePrecedente) {
        this.stage = stage; 
        this.scenePrecedente = scenePrecedente;
    }

    /**
     * Méthode qui est appelé par le bouton retour, cette méthode permet de revenir à l'écran d'avant
     * @param event
     */
    @FXML
    private void onBackAction(ActionEvent event) {
        stage.setScene(scenePrecedente);
    }
}
