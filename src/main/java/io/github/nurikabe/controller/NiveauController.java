package io.github.nurikabe.controller;

import io.github.nurikabe.Niveau;
import io.github.nurikabe.Chronometre;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;
//import org.slf4j.Logger;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import java.io.*;

/**
 * Class public représentant le controller des techniques héritant de la classe VBox, la racine du menu principal
 */
public class NiveauController extends VBox {


    /**
     * Variable d'instance privé qui stocke le stage actuel
     */
    private final Stage stage;
    
    /**
     * variable d'instance privé qui implémente la scène précédente, elle est utilisé par la fonction qui gère le bouton retour
     */
    private final Scene scenePrecedente;
    private final Niveau niveau;

    private GridPane jeu_grille;

    @FXML private VBox gridPaneContainer;

    @FXML private Button buttonUndo;

    @FXML private Button buttonRedo;

    @FXML private HBox timerAndLabelParent;

    @FXML private Label labelErreurs;

    @FXML private Label timerLabel;

    /**
     * Le constructeur de la classe TechniquesController  
     * @param stage la scène courante
     * @param scenePrecedente la scène précédente, qui sera utilisé par le bouton retour
     */
    public NiveauController(Stage stage, Scene scenePrecedente, String cheminNiveau, String mode_jeu, SelectionNiveauxController select) throws Exception {

        this.stage = stage;
        this.scenePrecedente = scenePrecedente;

          System.out.println("Working Directory = " + System.getProperty("user.dir"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Plateau.fxml"));
         loader.setController(this);
         loader.setRoot(this);
         loader.load();
        // Add some debug output
        GridPane gridPane = (GridPane) loader.getNamespace().get("gridPaneGraphicalState");

        if(mode_jeu.equals("CLASSIQUE")||mode_jeu.equals("AVENTURE")){
            
            timerAndLabelParent.getChildren().clear();
            niveau = new Niveau(stage, cheminNiveau, mode_jeu, select, null);

        }
       
        else niveau = new Niveau(stage, cheminNiveau, mode_jeu, select, timerLabel);

        jeu_grille= niveau.get_grillegraphique();
        jeu_grille.setId("gridPaneGraphicalState");

         gridPaneContainer.getChildren().remove(gridPane);
         jeu_grille.setStyle("-fx-background-color: #C0C0C0;");
         gridPaneContainer.getChildren().add(jeu_grille);

         buttonUndo.setOnMousePressed(niveau.handlerUndo);
         buttonRedo.setOnMousePressed(niveau.handlerRedo);

         niveau.setUndoB(buttonUndo);
         niveau.setRedoB(buttonRedo);

         stage.setScene(new Scene(this));
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

    @FXML
    private void onVerifierAction(ActionEvent event) {
        final int erreurs = niveau.verifier();
        labelErreurs.setText(erreurs + " erreurs");
    }

    @FXML
    private void onResetAction(ActionEvent event) {
        niveau.reset();
    }
}
