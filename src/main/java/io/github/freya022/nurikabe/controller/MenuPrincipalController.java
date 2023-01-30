package io.github.freya022.nurikabe.controller;

import io.github.freya022.nurikabe.Logging;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;

// extends VBox car c'est la racine du menu principal
public class MenuPrincipalController extends VBox {
    private static final Logger LOGGER = Logging.getLogger();

    @FXML
    private void onExitAction(ActionEvent event) {
        LOGGER.info("Bouton {} actionné", ((Button) event.getTarget()).getText());
    }

    @FXML
    private void onMethodsAction(ActionEvent event) {
        LOGGER.info("Bouton {} actionné", ((Button) event.getTarget()).getText());
    }

    @FXML
    private void onPlayAction(ActionEvent event) {
        LOGGER.info("Bouton {} actionné", ((Button) event.getTarget()).getText());
    }

    @FXML
    private void onRulesAction(ActionEvent event) {
        LOGGER.info("Bouton {} actionné", ((Button) event.getTarget()).getText());
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
