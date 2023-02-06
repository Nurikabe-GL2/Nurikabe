package io.github.freya022.nurikabe.controller;

import io.github.freya022.nurikabe.Logging;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;

import io.github.freya022.nurikabe.controller.MenuPrincipalController;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;

import java.io.IOException;
import io.github.freya022.nurikabe.Utils;
import io.github.freya022.nurikabe.controller.TechniqueController;

// extends VBox car c'est la racine du menu principal
public class MenuPrincipalController extends VBox {
    private static final Logger LOGGER = Logging.getLogger();

    @FXML
    private void onExitAction(ActionEvent event) {
        LOGGER.info("Bouton Quitter", ((Button) event.getTarget()).getText());
    }

    @FXML
    private void onMethodsAction(ActionEvent event) {//technique
        try {
                //Charge le menu principal, le contrôleur étend le type du nœud racine
                final var controller = Utils.loadFxml(new TechniqueController(), "Techniques");
                System.out.println("gdf");

                final Scene scene = new Scene(controller); //Le conteneur de noeud racine
                final Stage stage = new Stage(); //La fenêtre
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                LOGGER.error("An exception occurred while constructing the techniques frame", e);
            }
    }

    @FXML
    private void onPlayAction(ActionEvent event) {
        LOGGER.info("Bouton Jouer", ((Button) event.getTarget()).getText());
    }

    @FXML
    private void onRulesAction(ActionEvent event) {
        LOGGER.info("Bouton Règle", ((Button) event.getTarget()).getText());
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
