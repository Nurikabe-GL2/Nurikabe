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
 * La classe public implémentant le controller du menu principal, elle hérite de la super classe VBox qui est la racine du menu principal
 */
public class MenuPrincipalController extends VBox {
    
    /**
     * initialisation du logger pour générer des messages durant l'éxécution suite à des évènements.
     */
    private static final Logger LOGGER = Logging.getLogger();
    
    /**
     * Variable d'instance privé qui implémente le stage courant
     */
    private final Stage stage;

    /**
     * Constructeur de la classe MenuPrincipalController
     * @param stage le stage du menu principal
     */
    public MenuPrincipalController(Stage stage) {
        this.stage = stage;
    }

    /**
     * Méthode privé qui est appelée par le bouton quitter
     * Elle se charge d'afficher quel bouton à été appelé et de fermer la fenêtre
     * @param event l'évènement qui à été appelé cette fonction
     */
    @FXML
    private void onExitAction(ActionEvent event) {
        LOGGER.info("Bouton {} actionné", ((Button) event.getTarget()).getText());
        stage.close();
    }

    /**
     * Méthode privé appelée quand le bouton technique est cliqué
     * Elle s'occupe de gérer le chargement du controller technique et de chargé la fenêtre de technique (elle l'affiche)
     * @param event //l'évènement qui a généré l'appel de cette fonction ici le clique
     * @throws IOException le type d'éxception que peut générer la méthode
     */
    @FXML
    private void onMethodsAction(ActionEvent event) throws IOException {
        final TechniquesController controller = Utils.loadFxml(
                new TechniquesController(stage, stage.getScene()),
                "Techniques"
        );
        stage.setScene(new Scene(controller));
    }

    /**
     * Méthode privé appelée quand le bouton jouer est cliqué
     * Cette méthode s'occupe de chargé le controller de la sélection de niveau et de chargé la scène qui lui est lié 
     * et affiche la fenêtre de cette dernière
     * @param event //l'évènement qui a généré l'appel de cette fonction ici le clique
     * @throws IOException le type d'éxception que peut générer la méthode
     */
    @FXML
    private void onPlayAction(ActionEvent event) throws IOException {
      final SelectionNiveauxController controller = Utils.loadFxml(
                new SelectionNiveauxController(stage, stage.getScene()),
                "SelectionNiveaux"
        );
        stage.setScene(new Scene(controller));
        return;
    }

    /**
     * Méthode privé appelée quand le bouton règle est cliqué
     * Cette méthode s'occupe de chargé le controller des règles, de charger la scène qui lui est lié et d'affiché sa fenêtre en tant que fenètre actuel
     * @param event //l'évènement qui a généré l'appel de cette fonction ici le clique
     * @throws IOException le type d'éxception que peut générer la méthode
     */
    @FXML
    private void onRulesAction(ActionEvent event) throws IOException {
         final ReglesController controller = Utils.loadFxml(
                new ReglesController(stage, stage.getScene()),
                "Regles");
        stage.setScene(new Scene(controller));
    }

    /**
     * Méthode privé appelée quand le bouton paramètre est cliqué
     * Cette méthode pour l'instant affiche un message quand cette méthode est appelé
     * @param event //l'évènement qui a généré l'appel de cette fonction ici le clique
     * @throws IOException le type d'éxception que peut générer la méthode
     */
    @FXML
    private void onSettingsAction(ActionEvent event) {
        LOGGER.info("Bouton {} actionné", ((Button) event.getTarget()).getText());
    }

    /**
     * Méthode privé appelé quand le menu principal est chargé
     * elle s'occupe d'afficher un message quand le menu principal est chargé
     */
    @FXML
    private void initialize() {
        LOGGER.info("Menu principal chargé");
    }
}