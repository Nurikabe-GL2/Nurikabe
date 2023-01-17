package io.github.freya022.nurikabe;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;

import java.io.IOException;

public class Main {
    private static final Logger LOGGER = Logging.getLogger();

    public static void main(String[] args) {
        LOGGER.debug("Démarrage");

        Platform.startup(() -> {
            try {
                final Parent root = FXMLLoader.load(Utils.getResource(Main.class, "view/MenuPrincipal.fxml"));

                final Scene scene = new Scene(root);
                final Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

                LOGGER.info("Menu principal affiché");
            } catch (IOException e) {
                LOGGER.error("An exception occurred while constructing the main menu", e);
            }
        });
    }
}
