package io.github.nurikabe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Class public représentant le controller des techniques héritant de la classe VBox, la racine du menu principal
 */
public class TechniquesController extends VBox {


    /**
     * Variable d'instance privé qui stocke le stage actuel
     */
    private final Stage stage;
    
    /**
     * variable d'instance privé qui implémente la scène précédente, elle est utilisé par la fonction qui gère le bouton retour
     */
    private final Scene scenePrecedente;

    /**
     * Le constructeur de la classe TechniquesController  
     * @param stage la scène courante
     * @param scenePrecedente la scène précédente, qui sera utilisé par le bouton retour
     */
    public TechniquesController(Stage stage, Scene scenePrecedente) {
        this.stage = stage;
        this.scenePrecedente = scenePrecedente;
    }

    /**
     * Méthode qui est appelé quand on clique sur la grille du niveau1 
     * elle appele le controller du plateau qui va chargé la grille 
     * @param event l'évènement qui a activé la méthode ici le clique
     */
    @FXML
    private void onBackAction(ActionEvent event) {
        stage.setScene(scenePrecedente);
    }
}
