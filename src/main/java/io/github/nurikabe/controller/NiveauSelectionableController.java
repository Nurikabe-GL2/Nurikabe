package io.github.nurikabe.controller;

import io.github.nurikabe.Grille;
import io.github.nurikabe.GrilleSolution;
import io.github.nurikabe.MetadonneesSauvegarde;
import io.github.nurikabe.ModeDeJeu;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Contrôleur pour le composant affichant un niveau.
 * <br><b>Note : </b>Cette classe destinée à être rechargeable à chaque fois que le mode de jeu est changé
 */
public class NiveauSelectionableController extends VBox {
    @FXML private GridPane gridPane;
    @FXML private Label label;

    private final GrilleSolution solution;
    private final ModeDeJeu modeDeJeu;

    public NiveauSelectionableController(GrilleSolution solution, ModeDeJeu modeDeJeu) {
        this.solution = solution;
        this.modeDeJeu = modeDeJeu;
    }

    @FXML
    private void initialize() {
        label.setText(solution.getNomNiveau());

        final MetadonneesSauvegarde metadonneesSauvegarde = solution.getMetadonneesSauvegarde(modeDeJeu);
        final boolean estComplete = metadonneesSauvegarde.estComplete();

        final Grille<String> grilleSolution = solution.getGrille();
        for (int y = 0; y < grilleSolution.getHauteur(); y++) {
            for (int x = 0; x < grilleSolution.getLargeur(); x++) {
                final StackPane p = new StackPane();
                p.setPrefSize(20, 20);
                p.getStyleClass().add("case-solution");

                final String contenuCase = grilleSolution.recup(x, y);

                if (contenuCase.equals("n")) {
                    //Affichage des cases noires si le niveau est complété
                    if (estComplete) {
                        p.getStyleClass().add("case-noire");
                    }
                } else if (!contenuCase.equals("b")) { //Numero
                    final Label label = new Label(contenuCase);
                    label.setStyle("-fx-text-fill: black");
                    p.getChildren().add(label);
                }

                GridPane.setColumnIndex(p, x);
                GridPane.setRowIndex(p, y);
                gridPane.getChildren().add(p);
            }
        }
    }
}
