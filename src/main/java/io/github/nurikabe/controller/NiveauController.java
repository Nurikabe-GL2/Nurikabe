package io.github.nurikabe.controller;

import io.github.nurikabe.Niveau;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

    @FXML private Button buttonAide;

    @FXML private TabPane tabPane;
    @FXML private Tab tabAide;

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

        niveau = new Niveau(stage, cheminNiveau, mode_jeu, select);

        jeu_grille= niveau.get_grillegraphique();
        jeu_grille.setId("gridPaneGraphicalState");

         gridPaneContainer.getChildren().remove(gridPane);
         jeu_grille.setStyle("-fx-background-color: #C0C0C0;");
         gridPaneContainer.getChildren().add(jeu_grille);

         //définition des handlers des boutons
         buttonUndo.setOnMousePressed(niveau.handlerUndo);
         buttonRedo.setOnMousePressed(niveau.handlerRedo);
         buttonReset.setOnMousePressed(niveau.handlerReset);
         buttonAide.setOnMousePressed(niveau.handlerAide);

        //obtention du tab Aide
         Node tab = (Node) root.lookup("#tabAide");
         //System.out.println(tab);

         TabPane onglet = (TabPane) root.lookup("#tabPane");
         System.out.println(onglet);


        niveau.setAide(buttonAide, tabPane, tabAide);

         //mise en place des boutons
         niveau.setUndoB(buttonUndo);
         niveau.setRedoB(buttonRedo);

         if(mode_jeu.equals("CLASSIQUE")||mode_jeu.equals("AVENTURE"))timerAndLabelParent.getChildren().clear();

         else {
          //A FAIRE

         }


         //envoie du tab Aide et le bouton aide au setter


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
