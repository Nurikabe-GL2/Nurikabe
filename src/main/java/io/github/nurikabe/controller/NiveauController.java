package io.github.nurikabe.controller;

import io.github.nurikabe.ModeDeJeu;
import io.github.nurikabe.niveaux.FichierSolution;
import io.github.nurikabe.niveaux.MetadonneesSauvegarde;
import io.github.nurikabe.niveaux.Niveau;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;
import io.github.nurikabe.techniques.Techniques;
import io.github.nurikabe.utils.Utils;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur représentant le plateau du Nurikabe.
 * Il comprend :
 * <ul>
 *     <li>Les boutons undo/redo</li>
 *     <li>Les boutons hypothèses (créer, valider, annuler)</li>
 *     <li>Le bouton d'aide</li>
 *     <li>Le bouton de vérification</li>
 *     <li>Le bouton de remise à zero</li>
 * </ul>
 */
public class NiveauController extends FenetreController {
    private final MetadonneesSauvegarde metadonneesSauvegarde;
    private final SelectionNiveauxController select;

    private final Niveau niveau;

    /**
     * Représente si le mode hypothèse est actif ou non
     */
    private boolean hypo = false;

    @FXML private GridPane gridPane;

    @FXML private Button buttonUndo, buttonRedo;

    @FXML private Button boutonHypothese, boutonHypotheseValider, boutonHypotheseAnnuler;

    @FXML private HBox timerAndLabelParent;

    @FXML private Label labelErreurs;

    @FXML private Label timerLabel;

    @FXML private Button buttonAide;

    @FXML private Label scoreLabel;

    @FXML private TabPane tabPane;

    @FXML private VBox techniquesBox;

    /**
     * Le constructeur de la classe TechniquesController
     *
     * @param stage                 la scène courante
     * @param metadonneesSauvegarde Les données concernant l'état de la sauvegarde
     * @param select                Le contrôleur de selection des niveaux
     */
    public NiveauController(Stage stage,
                            MetadonneesSauvegarde metadonneesSauvegarde,
                            SelectionNiveauxController select) throws Exception {
        super(stage);

        this.metadonneesSauvegarde = metadonneesSauvegarde;
        this.select = select;

        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Plateau.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();

        if (metadonneesSauvegarde.getModeDeJeu() == ModeDeJeu.CLASSIQUE || metadonneesSauvegarde.getModeDeJeu() == ModeDeJeu.AVENTURE) {
            timerAndLabelParent.getChildren().clear();
            niveau = new Niveau(metadonneesSauvegarde, this, gridPane, null, null);
        } else {
            niveau = new Niveau(metadonneesSauvegarde, this, gridPane, timerLabel, scoreLabel);
        }

        niveau.initialiser();

        stage.getScene().setRoot(this);
    }

    /**
     * Affiche l'écran précédent et réactualise les niveaux
     */
    @Override
    public void ecranPrecedent() {
        select.refreshLevels();
        super.ecranPrecedent();
    }

    /**
     * Rafraichis l'état des boutons undo/redo/hypothèse
     */
    public void rafraichir() {
        buttonUndo.setDisable(niveau.recupUndo().estVide());
        buttonRedo.setDisable(niveau.recupRedo().estVide());
        boutonHypotheseAnnuler.setVisible(hypo);
        boutonHypotheseValider.setVisible(hypo);
    }

    @FXML
    private void initialize() throws IOException {
        final FichierSolution solution = metadonneesSauvegarde.getSolution();
        labelTitre.setText(metadonneesSauvegarde.getModeDeJeu().getDescriptionMode() + " : " + solution.getNomNiveau());

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

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                final var chrono = niveau.getChrono();
                if (chrono != null)
                    timerLabel.setText(chrono.toString());
            }
        }.start();

        addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (event.isControlDown()) {
                if (event.getCode() == KeyCode.Z) {
                    niveau.undo();
                } else if (event.getCode() == KeyCode.Y) {
                    niveau.redo();
                }
            }
        });
    }

    @FXML
    private void onUndoAction(ActionEvent event) {
        niveau.undo();
    }

    @FXML
    private void onRedoAction(ActionEvent event) {
        niveau.redo();
    }

    @FXML
    private void onHypotheseAction(ActionEvent event) {
        hypo = true;
        niveau.activerModeHypothese();
    }

    @FXML
    private void onValiderHypotheseAction(ActionEvent event) {
        niveau.confirmerHypothese();
        hypo = false;
    }

    @FXML
    private void onAnnulerHypotheseAction(ActionEvent event) {
        niveau.annulerHypothese();
        hypo = false;
    }

    /**
     * Méthode qui est appelée en cliquant sur le bouton aide
     *
     * @param event l'évènement qui a activé la méthode ici le clic
     */
    @FXML
    private void onAideAction(ActionEvent event) {
        niveau.utilisationAide();
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
     *
     * @param event l'évènement qui a activé la méthode ici le clic
     */
    @FXML
    private void onVerifierAction(ActionEvent event) {
        final int erreurs = niveau.verifier();
        labelErreurs.setText(erreurs + " erreurs");
    }

    /**
     * Méthode qui est appelée en cliquant sur le bouton reset
     *
     * @param event l'évènement qui a activé la méthode ici le clic
     */
    @FXML
    private void onResetAction(ActionEvent event) {
        niveau.reset();
    }
}
