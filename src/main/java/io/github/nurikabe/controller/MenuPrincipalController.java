package io.github.nurikabe.controller;

import io.github.nurikabe.Logging;
import io.github.nurikabe.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;

import java.io.IOException;

// extends VBox car c'est la racine du menu principal
public class MenuPrincipalController extends VBox {
    private static final Logger LOGGER = Logging.getLogger();
    private final Stage stage;

    public MenuPrincipalController(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void onExitAction(ActionEvent event) {
        LOGGER.info("Bouton {} actionné", ((Button) event.getTarget()).getText());
    
    }

    @FXML
    private void onMethodsAction(ActionEvent event) throws IOException {
        final TechniquesController controller = Utils.loadFxml(
                new TechniquesController(stage, stage.getScene()),
                "Techniques"
        );
        stage.setScene(new Scene(controller));
    }

    @FXML
    private void onPlayAction(ActionEvent event) throws IOException {
        final SelectionNiveauxController controller = Utils.loadFxml(
                new SelectionNiveauxController(stage, stage.getScene()),
                "SelectionNiveaux"
        );
        stage.setScene(new Scene(controller));
    }

    @FXML
    private void onRulesAction(ActionEvent event) throws IOException {
         final ReglesController controller = Utils.loadFxml(
                new ReglesController(stage, stage.getScene()),
                "Regles"
        );
        stage.setScene(new Scene(controller));
    }

    @FXML
    private void onSettingsAction(ActionEvent event) {
        LOGGER.info("Bouton {} actionné", ((Button) event.getTarget()).getText());
    }

    @FXML // Execute quand le fichier FXML est chargé
    private void initialize() {
        LOGGER.info("Menu principal chargé");
    }
}
