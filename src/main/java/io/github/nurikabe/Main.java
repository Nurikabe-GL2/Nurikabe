package io.github.nurikabe;

import io.github.nurikabe.controller.MenuPrincipalController;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;

import java.io.IOException;

public class Main {
    private static final Logger LOGGER = Logging.getLogger();

    public static void main(String[] args) {
        LOGGER.debug("Undo-redo");

        // Démarre JavaFX
        Platform.startup(() -> {
            try {
                final Stage stage = new Stage(); //La fenêtre

                //Charge le menu principal, le contrôleur étend le type du nœud racine
                final var controller = Utils.loadFxml(new UndoRedoController(stage), "UndoRedo");

                final Scene scene = new Scene(controller); //Le conteneur de noeud racine
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                LOGGER.error("An exception occurred while constructing the undo-redo", e);
            }
        });
    }
}
