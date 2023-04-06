/**
 * Fichier MenuPrincipalController.java
 */

// Package GitHub
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

/**
 * Contrôleur représentant le menu principal du Nurikabe.
 * <br>Il comprend les actions pour :
 * <ul>
 *     <li>Jouer</li>
 *     <li>Afficher les règles</li>
 *     <li>Afficher les techniques</li>
 *     <li>Modifier les paramètres</li>
 *     <li>Quitter</li>
 * </ul>
 */
public class MenuPrincipalController extends VBox {
    /**
     * Initialisation du logger pour générer des messages durant l'exécution suite à des évènements
     */
    private static final Logger LOGGER = Logging.getLogger();

    /**
     * Variable d'instance privée qui implémente le stage courant
     */
    private final Stage stage;

    /**
     * Constructeur de la classe MenuPrincipalController
     *
     * @param stage le stage du menu principal
     */
    public MenuPrincipalController(Stage stage) {
        this.stage = stage;
    }

    /**
     * Méthode privée qui est appelée par le bouton quitter.
     * Elle se charge d'afficher quel bouton a été appelé et de fermer la fenêtre
     *
     * @param event l'évènement qui a été appelé
     */
    @FXML
    private void onExitAction(ActionEvent event) {
        LOGGER.info("Bouton {} actionné", ((Button) event.getTarget()).getText());
        stage.close();
    }

    /**
     * Méthode privée appelée quand le bouton technique est cliqué.
     * Elle s'occupe de gérer le chargement du contrôleur technique et de charger la fenêtre de technique (elle l'affiche)
     *
     * @param event L'évènement qui a généré l'appel de cette fonction ici le clic
     */
    @FXML
    private void onMethodsAction(ActionEvent event) throws IOException {
        final TechniquesController controller = Utils.loadFxml(
                new TechniquesController(stage, stage.getScene()), "Techniques"
        );
        stage.setScene(new Scene(controller));
    }

    /**
     * Méthode privée appelée quand le bouton jouer est cliqué
     * Cette méthode s'occupe de charger le contrôleur de la sélection de niveau et de charger la scène qui lui est liée, de plus, elle affiche la fenêtre de cette dernière
     *
     * @param event L'évènement qui a généré l'appel de cette fonction ici le clic
     */
    @FXML
    private void onPlayAction(ActionEvent event) throws IOException {
        final SelectionNiveauxController controller = Utils.loadFxml(
                new SelectionNiveauxController(stage, stage.getScene()), "SelectionNiveaux"
        );
        stage.setScene(new Scene(controller));
    }

    /**
     * Méthode privée appelée quand le bouton règle est cliqué
     * Cette méthode s'occupe de chargé le controller des règles, de charger la scène qui lui est liée et d'afficher sa fenêtre en tant que fenêtre actuelle
     *
     * @param event l'évènement qui a généré l'appel de cette fonction ici le clic
     */
    @FXML
    private void onRulesAction(ActionEvent event) throws IOException {
        final ReglesController controller = Utils.loadFxml(
                new ReglesController(stage, stage.getScene()), "Regles");
        stage.setScene(new Scene(controller));
    }

    /**
     * Méthode privée appelée quand le bouton paramètre est cliqué
     * Cette méthode pour l'instant affiche un message quand cette méthode est appelée
     *
     * @param event l'évènement qui a généré l'appel de cette fonction ici le clic
     */
    @FXML
    private void onSettingsAction(ActionEvent event) {
        LOGGER.info("Bouton {} actionné", ((Button) event.getTarget()).getText());
    }

    /**
     * Méthode privée appelée quand le menu principal est chargé
     * elle s'occupe d'afficher un message quand le menu principal est chargé
     */
    @FXML
    private void initialize() {
        LOGGER.info("Menu principal chargé");
    }
}