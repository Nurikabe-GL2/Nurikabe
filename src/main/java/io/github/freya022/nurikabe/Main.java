package io.github.freya022.nurikabe;

import io.github.freya022.nurikabe.controller.MenuPrincipalController;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;

import java.io.IOException;

public class Main {
    private static final Logger LOGGER = Logging.getLogger();

    public static void main(String[] args) {
        LOGGER.debug("Démarrage");

        // Démarre JavaFX
        Platform.startup(() -> {
            try {
                //Charge le menu principal, le contrôleur étend le type du nœud racine
                final var controller = Utils.loadFxml(new MenuPrincipalController(), "MenuPrincipal");

                final Scene scene = new Scene(controller); //Le conteneur de noeud racine
                final Stage stage = new Stage(); //La fenêtre
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                LOGGER.error("An exception occurred while constructing the main menu", e);
            }
        });
    }
}
