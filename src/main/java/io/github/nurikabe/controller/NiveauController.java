package io.github.nurikabe.controller;

import io.github.nurikabe.Niveau;
import io.github.nurikabe.Utils;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;
import io.github.nurikabe.techniques.Techniques;
import io.github.nurikabe.Chronometre;
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

import java.io.IOException;
import java.util.*;

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

    @FXML private Label scoreLabel;

    @FXML private Button buttonAide;

    @FXML private TabPane tabPane;

    @FXML private VBox techniquesBox;

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
            niveau = new Niveau(stage, cheminNiveau, mode_jeu, select, null, null);

        }

        else niveau = new Niveau(stage, cheminNiveau, mode_jeu, select, timerLabel, scoreLabel);

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

         stage.setScene(new Scene(this));
    }

    @FXML
    private void initialize() throws IOException {
        final Map<String, List<Technique>> categoriesTechniques = new LinkedHashMap<>();
        for (Technique technique : Techniques.TECHNIQUES) {
            categoriesTechniques.computeIfAbsent(technique.getCategorie(), s -> new ArrayList<>()).add(technique);
        }

        for (var entry : categoriesTechniques.entrySet()) {
            final var nomCategorie = entry.getKey();
            final var techniques = entry.getValue();
            final var controller = Utils.loadFxml(new CategorieTechniqueController(nomCategorie, techniques), "_CategorieTechnique");
            techniquesBox.getChildren().add(controller);
        }
    }

    /**
     * Méthode qui est appelée en cliquant sur le bouton retour
     * @param event l'évènement qui a activé la méthode ici le clique
     */
    @FXML
    private void onBackAction(ActionEvent event) {
        stage.setScene(scenePrecedente);
    }


    /**
     * Méthode qui est appelée en cliquant sur le bouton aide
     * @param event l'évènement qui a activé la méthode ici le clique
     */
    @FXML
    private void onAideAction(ActionEvent event) {
        niveau.utilisation_aide();
        try {
            final PositionTechniques positionTechniques = Techniques.trouverTechnique(niveau);

            if (positionTechniques != null) {
                final Tab tab = new Tab("Aide");
                tab.setContent(Utils.loadFxml(new ContenuAideController(niveau, positionTechniques), "_ContenuAide"));

                //Remplacement de l'onglet d'aide
                tabPane.getTabs().removeIf(t -> t.getText().equals("Aide"));
                tabPane.getTabs().add(tab);
                tabPane.getSelectionModel().select(tab);
            } else {
                final Alert alert = new Alert(Alert.AlertType.INFORMATION, "Il n'y a pas de techniques applicables, vous pouvez revenir en arrière, ou continuer à jouer.");
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui est appelée en cliquant sur le bouton verifier
     * @param event l'évènement qui a activé la méthode ici le clique
     */
    @FXML
    private void onVerifierAction(ActionEvent event) {
        final int erreurs = niveau.verifier();
        labelErreurs.setText(erreurs + " erreurs");
    }

    /**
     * Méthode qui est appelée en cliquant sur le bouton reset
     * @param event l'évènement qui a activé la méthode ici le clique
     */
    @FXML
    private void onResetAction(ActionEvent event) {
        niveau.reset();
    }
}
