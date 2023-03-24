package io.github.nurikabe.controller;

import io.github.nurikabe.Niveau;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Techniques;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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

    @FXML private ScrollPane aidePane;

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

         //mise en place des boutons
         niveau.setUndoB(buttonUndo);
         niveau.setRedoB(buttonRedo);

         if(mode_jeu.equals("CLASSIQUE")||mode_jeu.equals("AVENTURE"))timerAndLabelParent.getChildren().clear();

         else {
          //A FAIRE

         }

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
    private void onAideAction(ActionEvent event) {
        final PositionTechniques positionTechniques = Techniques.trouverTechnique(niveau);
        System.out.println(positionTechniques);

        //création de la box recevant les labels
        VBox box = new VBox();
        //création et insertion du label catégorie
        Label labelCategorie = new Label("Technique Applicable\n");
        labelCategorie.getStyleClass().add("tabContentMainLabel");
        labelCategorie.setWrapText(true);
        box.getChildren().add(labelCategorie);

        //création et insertion du label de la technique
        Label labelTechnique = new Label(""+positionTechniques.toString());
        labelTechnique.getStyleClass().add("tipCategory");
        labelTechnique.setWrapText(true);
        box.getChildren().add(labelTechnique);

        //insertion des label dans l'onglet aide
        aidePane.setContent(box);
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
