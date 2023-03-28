package io.github.nurikabe.controller;

import io.github.nurikabe.Utils;
import io.github.nurikabe.techniques.Technique;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class CategorieTechniqueController extends VBox {
    private final String nomCategorie;
    private final List<Technique> techniques;

    @FXML
    private Label categorieLabel;
    @FXML
    private VBox techniquesBox;

    public CategorieTechniqueController(String nomCategorie, List<Technique> techniques) {
        this.nomCategorie = nomCategorie;
        this.techniques = techniques;
    }

    @FXML
    private void initialize() throws IOException {
        categorieLabel.setText(nomCategorie);
        for (Technique technique : techniques) {
            final var controller = Utils.loadFxml(new ContenuTechniqueController(technique), "_ContenuTechnique");
            techniquesBox.getChildren().add(controller);
        }
    }
}
