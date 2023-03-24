package io.github.nurikabe.controller;

import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;

public class ContenuAideController extends ScrollPane {
    private final PositionTechniques positionTechniques;

    @FXML
    private ImageView image;

    @FXML
    private Label labelCategorie;

    @FXML
    private Label labelDescription;

    @FXML
    private Label labelNom;

    public ContenuAideController(PositionTechniques positionTechniques) {
        this.positionTechniques = positionTechniques;
    }

    @FXML
    private void initialize() {
        final Technique technique = positionTechniques.getTechnique();
        labelNom.setText(technique.getNom());
        labelDescription.setText(technique.getDescription());
        image.setImage(technique.getImage());
    }

    @FXML
    private void onPositionAideAction(ActionEvent event) {

    }
}
