package io.github.nurikabe.controller;

import io.github.nurikabe.techniques.Technique;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ContenuTechniqueController extends VBox {
    private final Technique technique;

    @FXML
    private Label labelNom, labelDescription;
    @FXML
    private ImageView image;

    public ContenuTechniqueController(Technique technique) {
        this.technique = technique;
    }

    @FXML
    private void initialize() {
        labelNom.setText(technique.getNom());
        labelDescription.setText(technique.getDescription());
        image.setImage(technique.getImage());
    }
}
