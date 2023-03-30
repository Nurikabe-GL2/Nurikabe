package io.github.nurikabe.controller;

import io.github.nurikabe.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe public représentant le controller de la sélection de niveau
 */
public class SelectionNiveauxController extends VBox {

    /**
     * Initialisation du logger pour générer des messages durant l'exécution suite à des évènements.
     */
    private static final Logger LOGGER = Logging.getLogger();

    /**
     * Représente la fenêtre actuelle
     */
    private final Stage stage;

    /**
     * Représente la scène précédente, elle est utilisée par la fonction qui gère le bouton retour
     */
    private final Scene scenePrecedente;

    /**
     * Représente le mode jeu courant
     */
    private final ObjectProperty<ModeDeJeu> gameModeProperty = new SimpleObjectProperty<>(ModeDeJeu.AVENTURE);

    /**
     * Représente la difficulté courante
     */
    private final ObservableSet<Difficulte> difficulties = FXCollections.observableSet(Difficulte.FACILE);

    /**
     * Représente le sélecteur de mode de jeu
     */
    @FXML private ToggleGroup gameModeGroup;

    /**
     * Représente le sélecteur de difficulté de jeu
     */
    @FXML private ToggleGroup difficultyGroup;

    /**
     * Variable d'instance privée qui représente les tuiles de niveau
     */
    @FXML private TilePane puzzlesTilePane;
    @FXML private ToggleButton easyToggle, mediumToggle, hardToggle;

    /**
     * Le constructeur de la classe SelectionNiveauxController
     *
     * @param stage         la scène actuel
     * @param previousScene la scène précédente
     */
    public SelectionNiveauxController(Stage stage, Scene previousScene) {
        this.stage = stage;
        this.scenePrecedente = previousScene;
    }

    /**
     * Méthode privée qui est appelée quand le controller est chargé
     * Elle s'occupe d'ajouter le groupe du mode de jeu et de la difficulté, de les ajouter à la propriété du jeu en ajoutant un listener,
     * de mettre à jour la difficulté des niveaux et de rafraichir les niveaux
     */
    @FXML
    private void initialize() {
        FXUtils.singleItemToggleGroup(gameModeGroup);
        FXUtils.singleItemToggleGroup(difficultyGroup);

        gameModeProperty.bind(gameModeGroup.selectedToggleProperty().map(ModeDeJeu::fromToggle));
        gameModeProperty.addListener(x -> refreshLevels());

        difficultyGroup.selectedToggleProperty().addListener((x, y, newToggle) -> {

            setNewDifficulties((Node) newToggle);
            refreshLevels();
            System.out.println(difficultyGroup.getSelectedToggle());
        });

        //Ajout des niveaux
        refreshLevels();
    }

    /**
     * Méthode privée qui s'occupe de mettre à jour la difficulté
     *
     * @param newToggle le nœud comportant la difficulté choisis
     */
    private void setNewDifficulties(Node newToggle) {
        final var newDifficulties = switch (newToggle.getId()) {
            case "easyToggle" -> List.of(Difficulte.FACILE);
            case "mediumToggle" -> List.of(Difficulte.MOYEN);
            case "hardToggle" -> List.of(Difficulte.DIFFICILE);
            default -> throw new IllegalStateException("Unexpected value: " + newToggle.getId());
        };
        difficulties.clear();
        difficulties.addAll(newDifficulties);
    }

    /**
     * Méthode privée qui se charge de rafraichir les niveaux en fonction de la difficulté et du mode de jeu choisis
     */
    public void refreshLevels() {
        LOGGER.info("Mode: {}", gameModeProperty.get());
        LOGGER.info("Difficulties: {}", difficulties);

        switch (gameModeProperty.get()) {
            case CLASSIQUE -> chargerModeClassique(new ArrayList<>(difficulties).get(0));
            case AVENTURE -> chargerModeAventure();
            default -> chargerModeContreLaMontre();
        }
    }

    private void chargerModeClassique(Difficulte difficulte) {
        easyToggle.setDisable(false);
        mediumToggle.setDisable(false);
        hardToggle.setDisable(false);

        puzzlesTilePane.getStyleClass().setAll("modeClassique");

        puzzlesTilePane.getChildren().setAll(Niveaux.getNiveaux(difficulte).stream()
                .map(g -> {
                    try {
                        return Utils.loadFxml(new NiveauSelectionableController(this, stage, g, ModeDeJeu.CLASSIQUE), "_NiveauSelectionable");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList()
        );
    }

    private void chargerModeAventure() {
        easyToggle.setDisable(true);
        mediumToggle.setDisable(true);
        hardToggle.setDisable(true);

        puzzlesTilePane.getStyleClass().setAll("modeAventure");
        puzzlesTilePane.getChildren().clear();

        final var metadonneesSauvegardes = Niveaux.getNiveaux(Difficulte.MOYEN).stream().map(f -> f.getMetadonneesSauvegarde(ModeDeJeu.AVENTURE)).toList();
        for (int i = 0, sauvegardesSize = metadonneesSauvegardes.size(); i < sauvegardesSize; i++) {
            final MetadonneesSauvegarde metadonneesSauvegarde = metadonneesSauvegardes.get(i);

            final Button button = new Button();
            if (metadonneesSauvegarde.estComplete()) {
                button.getStyleClass().add("complete");
                button.setText("Complété");
            } else if (i == 0 || metadonneesSauvegardes.get(sauvegardesSize - 1).estComplete()) {
                //Le niveau est déverrouillé si c'est le premier, ou alors que le précédent est complet
                button.setText("Jouer");
                button.setOnMouseClicked(event -> {
                    try {
                        new NiveauController(stage, stage.getScene(), metadonneesSauvegarde, this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } else {
                button.setDisable(true);
                button.setText("Bloqué");
            }

            puzzlesTilePane.getChildren().add(button);
        }
    }

    private void chargerModeContreLaMontre() {
        chargerModeClassique(Difficulte.FACILE);
        easyToggle.setDisable(true);
        mediumToggle.setDisable(true);
        hardToggle.setDisable(true);

        stage.show();
    }


    /**
     * Méthode privée qui est appelé quand le bouton retour est cliqué
     * il s'occupe de revenir en arrière en chargeant la scène précédente
     *
     * @param event l'événement qui a activé la méthode, ici le clic
     */
    @FXML
    private void onBackAction(ActionEvent event) {
        stage.setScene(scenePrecedente);
    }
}
