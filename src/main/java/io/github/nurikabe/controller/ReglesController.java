package io.github.nurikabe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Classe privé représentant le controller des règles et héritant de VBox qui est la racine du menu principal
 */
public class ReglesController extends VBox {

    /**
     * Variable d'instance privé qui stocke le stage actuel
     */
    private final Stage stage;
    
    /**
     * variable d'instance privé qui implémente la scène précédente, elle est utilisé par la fonction qui gère le bouton retour
     */
    private final Scene scenePrecedente;

    /**
     * Constructeur de la classe ReglesController 
     * @param stage le stage courant
     * @param scenePrecedente le stage précédent
     */
    public ReglesController(Stage stage, Scene scenePrecedente) {
        this.stage = stage;
        this.scenePrecedente = scenePrecedente;
    }

    /**
     * Méthode privé qui est appelé quand le bouton retour est actionné
     * Cette méthode se charge de changer la fenêtre courante par la précédente
     * @param event l'évènement ayant provoqué l'appel de la fonction ici un clique
     */
    @FXML
    private void onBackAction(ActionEvent event) {
        stage.setScene(scenePrecedente);
    }
}
