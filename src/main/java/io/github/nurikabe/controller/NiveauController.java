package io.github.nurikabe.controller;

import io.github.nurikabe.Niveau;
import io.github.nurikabe.Utils;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Techniques;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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

        jeu_grille= niveau.getGridPane();
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
        try {
            final PositionTechniques positionTechniques = Techniques.trouverTechnique(niveau);
            System.out.println(positionTechniques);

            if (positionTechniques != null) {
                final Tab tab = new Tab("Aide");
                tab.setContent(Utils.loadFxml(new ContenuAideController(niveau, positionTechniques), "_ContenuAide"));

                //Remplacement de l'onglet d'aide
                tabPane.getTabs().removeIf(t -> t.getText().equals("Aide"));
                tabPane.getTabs().add(tab);
                tabPane.getSelectionModel().select(tab);
            } else {
                final Alert alert = new Alert(Alert.AlertType.INFORMATION, "Il n'y a pas de techniques applicables, vous devriez peut être essayer de revenir en arrière.");
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
