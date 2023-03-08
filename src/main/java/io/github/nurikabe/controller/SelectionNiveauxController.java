package io.github.nurikabe.controller;

import io.github.nurikabe.FXUtils;
import io.github.nurikabe.Logging;
import io.github.nurikabe.Difficulty;
import io.github.nurikabe.GameMode;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * Classe public représentant le controller de la sélection de niveau
 */
public class SelectionNiveauxController extends VBox {

    /**
     * initialisation du logger pour générer des messages durant l'éxécution suite à des évènements.
     */
    private static final Logger LOGGER = Logging.getLogger();

    /*
    * Variable d'instance privé qui stocke le stage actuel
    */
   private final Stage stage;
   
   /**
    * variable d'instance privé qui implémente la scène précédente, elle est utilisé par la fonction qui gère le bouton retour
    */
   private final Scene scenePrecedente;

    /**
     * Variable d'instance privé qui représente le mode de jeu 
     */
    @FXML
    private ToggleGroup gameModeGroup;
    
    /**
     * Variable d'instance privé qui représente la difficulté de jeu 
     */
    @FXML
    private ToggleGroup difficultyGroup;

    /**
     * Variable d'instance privé qui représente les tuiles de niveau
     */
    @FXML
    private TilePane puzzlesTilePane;

    /**
     * variable d'instance privé qui représente le mode jeu courant
     */
    private final ObjectProperty<GameMode> gameModeProperty = new SimpleObjectProperty<>(GameMode.AVENTURE);
    
    /**
     * variable d'instance privé qui représente la difficulté courante
     */
    private final ObservableSet<Difficulty> difficulties = FXCollections.observableSet(Difficulty.EASY);

    /**
     * Le contructeur de la classe SelectionNiveauxController 
     * @param stage la scène actuel 
     * @param previousScene la scène précédente
     */
    public SelectionNiveauxController(Stage stage, Scene previousScene) {
        this.stage = stage;
        this.scenePrecedente = previousScene;
    }

    /**
     * Méthode privé qui est appelé quand le controller est chargé
     * Elle s'occupe ... euh Bordel j'en sais foutrement rien je la comprend pas trop désolé les gars
     * Elle à l'air de s'occuper d'ajouter le groupe du mode de jeu et de la difficulté, de les ajouter au propriété du jeu en ajoutant un listener,
     * de mettre à jour la difficulté des niveau et de rafraichir les niveaux
     */
    @FXML
    private void initialize() {
        FXUtils.singleItemToggleGroup(gameModeGroup);
        FXUtils.singleItemToggleGroup(difficultyGroup);

        gameModeProperty.bind(gameModeGroup.selectedToggleProperty().map(GameMode::fromToggle));
        gameModeProperty.addListener(x -> refreshLevels());

        difficultyGroup.selectedToggleProperty().addListener((x, y, newToggle) -> {
            setNewDifficulties((Node) newToggle);
            refreshLevels();
        });

        //Ajout des niveaux
        refreshLevels();
    }

    /**
     * Méthode privé qui s'occupe de mettre à jour la difficulté
     * @param newToggle le noeud comportant la difficulté choisis
     */
    private void setNewDifficulties(Node newToggle) {
        final var newDifficulties = switch (newToggle.getId()) {
            case "allDifficultyToggle" -> Arrays.asList(Difficulty.values());
            case "easyToggle" -> List.of(Difficulty.EASY);
            case "mediumToggle" -> List.of(Difficulty.MEDIUM);
            case "hardToggle" -> List.of(Difficulty.HARD);
            default -> throw new IllegalStateException("Unexpected value: " + newToggle.getId());
        };
        difficulties.clear();
        difficulties.addAll(newDifficulties);
    }

    /**
     * Méthode privé qui se charge de rafraichir les niveaux en fonction de la difficulté et du mode de jeu choisis
     */
    private void refreshLevels() {
        LOGGER.info("Mode: {}", gameModeProperty.get());
        LOGGER.info("Difficulties: {}", difficulties);

        puzzlesTilePane.getChildren().addAll(/* TODO */);
    }
    
    /**
     * Méthode privé qui est appelé quand le bouton retour est cliqué
     * il s'occupe de revenir en arrière en chargant la scène précédente
     * @param event l'événement qui a activé la méthode, ici le clique
     */
    @FXML
    private void onBackAction(ActionEvent event) {
        stage.setScene(scenePrecedente);
    }

    /**
     * Méthode qui est appelé quand on clique sur la grille du niveau1 
     * elle appele le controller du plateau qui va chargé la grille 
     * @param event l'évènement qui a activé la méthode ici le clique
     */
    @FXML
    private void niveau1(ActionEvent event) {
       PlateauController p=new PlateauController(stage);
    }
}
