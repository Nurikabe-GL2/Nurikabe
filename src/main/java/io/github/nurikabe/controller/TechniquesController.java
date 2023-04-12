package io.github.nurikabe.controller;

import io.github.nurikabe.techniques.Techniques;
import io.github.nurikabe.utils.Utils;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Contrôleur représentant l'affichage des techniques.
 *
 * @see Techniques
 */
public class TechniquesController extends FenetreController {
    @FXML private VBox techniquesBox;

    public TechniquesController(Stage stage) {
        super(stage);
    }

    @FXML
    private void initialize() throws IOException {
        insererCategoriesTechniques(techniquesBox);
    }

    public static void insererCategoriesTechniques(VBox techniquesBox) throws IOException {
        boolean premier = true;
        for (var entry : Techniques.CATEGORIES_TECHNIQUES.entrySet()) {
            final var nomCategorie = entry.getKey();
            final var techniques = entry.getValue();
            final var controller = Utils.loadFxml(new CategorieTechniqueController(nomCategorie, techniques), "_CategorieTechnique");
            if (!premier)
                techniquesBox.getChildren().add(new Separator(Orientation.HORIZONTAL));
            techniquesBox.getChildren().add(controller);
            premier = false;
        }
    }
}
