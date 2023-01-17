package io.github.freya022.nurikabe;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;

public class Main {
    private static final Logger LOGGER = Logging.getLogger();

    public static void main(String[] args) {
        LOGGER.debug("Démarrage");

        Platform.startup(() -> {
            final StackPane root = new StackPane();

            final Scene scene = new Scene(root);
            final Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            LOGGER.info("Fenêtre affichée");
        });
    }
}
