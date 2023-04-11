package io.github.nurikabe.controller;

import io.github.nurikabe.Grille;
import io.github.nurikabe.ModeDeJeu;
import io.github.nurikabe.cases.Case;
import io.github.nurikabe.cases.CaseSolution;
import io.github.nurikabe.niveaux.FichierSolution;
import io.github.nurikabe.niveaux.MetadonneesSauvegarde;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Contrôleur pour le composant affichant un niveau.
 * <br><b>Note : </b>Cette classe destinée à être rechargeable à chaque fois que le mode de jeu est changé
 */
public class NiveauSelectionableController extends VBox {
    @FXML private GridPane gridPane;
    @FXML private Label label;

    private final SelectionNiveauxController selectionNiveauxController;
    private final Stage stage;
    private final FichierSolution solution;

    private final MetadonneesSauvegarde metadonneesSauvegarde;
    private final boolean estComplete;

    public NiveauSelectionableController(SelectionNiveauxController selectionNiveauxController,
                                         Stage stage,
                                         FichierSolution solution,
                                         ModeDeJeu modeDeJeu) {
        this.selectionNiveauxController = selectionNiveauxController;
        this.stage = stage;
        this.solution = solution;

        metadonneesSauvegarde = solution.getMetadonneesSauvegarde(modeDeJeu);
        estComplete = metadonneesSauvegarde.estComplete();
    }

    @FXML
    private void initialize() {
        if (estComplete) {
            label.setText(solution.getNomNiveau() + " (complété)");
        } else if (metadonneesSauvegarde.contiensSauvegarde()) {
            label.setText(solution.getNomNiveau() + " (commencé)");
        } else {
            label.setText(solution.getNomNiveau());
        }

        //Grille miniature
        final Grille<CaseSolution> grilleSolution = solution.getGrille();
        for (int y = 0; y < grilleSolution.getHauteur(); y++) {
            for (int x = 0; x < grilleSolution.getLargeur(); x++) {
                final StackPane p = new StackPane();
                p.setPrefSize(20, 20);
                p.getStyleClass().add("case-solution");

                final var caseSolution = grilleSolution.recup(x, y);
                if (caseSolution.getType() == Case.Type.NOIR) {
                    //Affichage des cases noires si le niveau est complété
                    if (estComplete) {
                        p.getStyleClass().add("case-noire");
                    }
                } else if (caseSolution.getType() == Case.Type.NOMBRE) {
                    final Label label = new Label(caseSolution.getContenuCase());
                    label.setStyle("-fx-text-fill: black");
                    p.getChildren().add(label);
                }

                GridPane.setColumnIndex(p, x);
                GridPane.setRowIndex(p, y);
                gridPane.getChildren().add(p);
            }
        }
    }

    @FXML
    private void onClicNiveau(MouseEvent event) throws Exception {
        if (estComplete) {
            final Optional<ButtonType> optButtonType = new Alert(Alert.AlertType.CONFIRMATION,
                    "Voulez vous recommencer ce niveau ? Cela supprimera la sauvegarde actuelle",
                    ButtonType.YES, ButtonType.NO).showAndWait();

            if (optButtonType.isPresent()) {
                if (optButtonType.get() == ButtonType.YES) {
                    metadonneesSauvegarde.supprimerSauvegarde();
                    new NiveauController(stage, metadonneesSauvegarde, selectionNiveauxController);
                }
            }
        } else {
            new NiveauController(stage, metadonneesSauvegarde, selectionNiveauxController);
        }
    }
}
