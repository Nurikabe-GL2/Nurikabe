package io.github.nurikabe.controller;

import io.github.nurikabe.Niveau;
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

public class ContenuAideController extends ScrollPane {
    private final Niveau niveau;
    private final PositionTechniques positionTechniques;

    @FXML private ImageView image;
    @FXML private Label labelCategorie, labelDescription, labelNom;
    @FXML private Button boutonPositionAide;

    public ContenuAideController(Niveau niveau, @NotNull PositionTechniques positionTechniques) {
        this.niveau = niveau;
        this.positionTechniques = positionTechniques;
    }

    @FXML
    private void initialize() {
        final Technique technique = positionTechniques.getTechnique();
        labelCategorie.setText(technique.getCategorie());
        labelNom.setText(technique.getNom());
        labelDescription.setText(technique.getDescription());
        image.setImage(technique.getImage());

        boutonPositionAide.setDisable(positionTechniques.getCibles().isEmpty());
    }

    @FXML
    private void onPositionAideAction(ActionEvent event) {
        for (Cible cible : positionTechniques.getCibles()) {
            niveau.getGrilleGraphique().recup(cible.getX(), cible.getY()).surbrillance(cible.getType());
        }
    }
}
