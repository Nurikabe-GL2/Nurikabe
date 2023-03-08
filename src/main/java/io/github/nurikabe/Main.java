/**
 * Fichier IOUtils.java
 */

// Package GitHub
package io.github.nurikabe;

// Importation des librairies javaFX
import io.github.nurikabe.controller.MenuPrincipalController;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import org.slf4j.Logger;

/**
 * Classe Main principale du jeu permettant de démarrer le jeu
 */
public class Main {
   /**
    * Initialisation du logger pour générer des messages durant l'exécution suite à des évènements
    */
   private static final Logger LOGGER = Logging.getLogger();

   /**
    * Méthode main de lancement du jeu
    * @param args les arguments d'entrée
    */
   public static void main(String[] args) {
      LOGGER.debug("Démarrage");

      // Démarre JavaFX
      Platform.startup(() -> {
         try {
            // Crée la fenêtre du jeu
            final Stage stage = new Stage();
            // Charge le menu principal, le contrôleur étend le type du noeud racine
            final var controller = Utils.loadFxml(new MenuPrincipalController(stage), "MenuPrincipal");
            // Le conteneur de noeud racine
            final Scene scene = new Scene(controller);
            stage.setScene(scene);
            stage.show();
         } catch (IOException e) {
            LOGGER.error("Une exception s'est produite lors de la construction du menu principal", e);
         }
      });
   }
}
