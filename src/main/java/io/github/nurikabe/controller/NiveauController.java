package io.github.nurikabe.controller;

import javafx.scene.layout.Pane;
import io.github.nurikabe.Niveau;
import io.github.nurikabe.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;
//import org.slf4j.Logger;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane; 
import java.io.*;
import java.util.ArrayList;

import org.w3c.dom.Node;

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

    private GridPane jeu_grille;

    @FXML private VBox gridPaneContainer;

    @FXML private Button buttonUndo;

    @FXML private Button buttonRedo;
    /**
     * Le constructeur de la classe TechniquesController  
     * @param stage la scène courante
     * @param scenePrecedente la scène précédente, qui sera utilisé par le bouton retour
     */
    public NiveauController(Stage stage, Scene scenePrecedente, String name) {
        this.stage = stage;
        this.scenePrecedente = scenePrecedente;

        try{
          System.out.println("Working Directory = " + System.getProperty("user.dir"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Plateau.fxml"));
         loader.setController(this);
         loader.setRoot(this);
        Parent root = loader.load();
        // Add some debug output
        GridPane gridPane = (GridPane) loader.getNamespace().get("gridPaneGraphicalState");
            
        Niveau niveau=new Niveau(stage, name);
            
        jeu_grille=niveau.get_grillegraphique(); 
        jeu_grille.setId("gridPaneGraphicalState");

         gridPaneContainer.getChildren().remove(gridPane);
         jeu_grille.setStyle("-fx-background-color: #C0C0C0;");
         gridPaneContainer.getChildren().add(jeu_grille);

         buttonUndo.setOnMousePressed(niveau.handlerUndo);
         buttonRedo.setOnMousePressed(niveau.handlerRedo);

         buttonUndo.setOnMouseReleased(MouseEvent -> {
          buttonUndo.setStyle("-fx-background-color: #4882bd");
         });

         buttonRedo.setOnMouseReleased(MouseEvent -> {
          buttonRedo.setStyle("-fx-background-color: #4882bd");
         });

         niveau.setUndoB(buttonUndo);
         niveau.setRedoB(buttonRedo);

         stage.setScene(new Scene(this));
        }catch (Exception e){
          System.out.println(e);
        }
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
