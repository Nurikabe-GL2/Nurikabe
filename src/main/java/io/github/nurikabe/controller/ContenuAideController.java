package io.github.nurikabe.controller;

import io.github.nurikabe.techniques.Cible;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;

/**
 * Contrôleur représentant une {@link PositionTechniques technique applicable} (aide) à afficher.
 */
public class ContenuAideController extends ScrollPane {
    private final NiveauController niveauController;
    private final PositionTechniques positionTechniques;

    @FXML private ImageView image;
    @FXML private Label labelCategorie, labelDescription, labelCondition, labelNom;
    @FXML private Button boutonPositionAide;

    public ContenuAideController(NiveauController niveauController, @NotNull PositionTechniques positionTechniques) {
        this.niveauController = niveauController;
        this.positionTechniques = positionTechniques;
    }

    @FXML
    private void initialize() {
        final Technique technique = positionTechniques.getTechnique();
        labelCategorie.setText(technique.getCategorie());
        labelNom.setText(technique.getNom());
        labelDescription.setText(technique.getDescription());
        labelCondition.setText(technique.getCondition());
        image.setImage(technique.getImage());

        boutonPositionAide.setDisable(positionTechniques.getCibles().isEmpty());
    }

    @FXML
    private void onPositionAideAction(ActionEvent event) {
        for (Cible cible : positionTechniques.getCibles()) {
            niveauController.getGrilleGraphique().recup(cible.x(), cible.y()).surbrillance(cible.type());
        }
    }
}
