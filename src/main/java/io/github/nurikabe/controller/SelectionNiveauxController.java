package io.github.nurikabe.controller;

import io.github.nurikabe.Difficulte;
import io.github.nurikabe.Logging;
import io.github.nurikabe.ModeDeJeu;
import io.github.nurikabe.niveaux.MetadonneesSauvegarde;
import io.github.nurikabe.niveaux.Niveaux;
import io.github.nurikabe.utils.FXUtils;
import io.github.nurikabe.utils.Utils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contrôleur représentant l'affichage des niveaux et la sélection de ceux-ci.
 * <br>Le mode classique, aventure et contre-la-montre sont gérés.
 */
public class SelectionNiveauxController extends FenetreController {
    private static final Logger LOGGER = Logging.getLogger();

    private final ObjectProperty<ModeDeJeu> proprieteModeDeJeu = new SimpleObjectProperty<>(ModeDeJeu.AVENTURE);

    private final ObservableSet<Difficulte> proprieteDifficulte = FXCollections.observableSet(Difficulte.FACILE);

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

    public SelectionNiveauxController(Stage stage) {
        super(stage);
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

        proprieteModeDeJeu.bind(gameModeGroup.selectedToggleProperty().map(ModeDeJeu::fromToggle));
        proprieteModeDeJeu.addListener(x -> refreshLevels());

        difficultyGroup.selectedToggleProperty().addListener((x, y, newToggle) -> {

            setNewDifficulties((Node) newToggle);
            refreshLevels();
            System.out.println(difficultyGroup.getSelectedToggle());
        });

        //Ajout des niveaux
        refreshLevels();
    }

    private void setNewDifficulties(Node newToggle) {
        final var newDifficulties = switch (newToggle.getId()) {
            case "easyToggle" -> List.of(Difficulte.FACILE);
            case "mediumToggle" -> List.of(Difficulte.MOYEN);
            case "hardToggle" -> List.of(Difficulte.DIFFICILE);
            default -> throw new IllegalStateException("Unexpected value: " + newToggle.getId());
        };
        proprieteDifficulte.clear();
        proprieteDifficulte.addAll(newDifficulties);
    }

    /**
     * Rafraichis l'affichage des niveaux en fonction de la difficulté et du mode de jeu.
     */
    public void refreshLevels() {
        LOGGER.info("Mode: {}", proprieteModeDeJeu.get());
        LOGGER.info("Difficulties: {}", proprieteDifficulte);

        switch (proprieteModeDeJeu.get()) {
            case CLASSIQUE -> chargerModeClassique(new ArrayList<>(proprieteDifficulte).get(0), ModeDeJeu.CLASSIQUE);
            case AVENTURE -> chargerModeAventure();
            default -> chargerModeContreLaMontre();
        }
    }

    private void chargerModeClassique(Difficulte difficulte, ModeDeJeu modeDeJeu) {
        easyToggle.setDisable(false);
        mediumToggle.setDisable(false);
        hardToggle.setDisable(false);

        puzzlesTilePane.getStyleClass().setAll("modeClassique");

        puzzlesTilePane.getChildren().setAll(Niveaux.getNiveaux(difficulte).stream()
                .map(g -> {
                    try {
                        return Utils.loadFxml(new NiveauSelectionableController(this, stage, g, modeDeJeu), "_NiveauSelectionable");
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
            } else if (i == 0 || metadonneesSauvegardes.get(i - 1).estComplete()) {
                //Le niveau est déverrouillé si c'est le premier, ou alors que le précédent est complet
                button.setText(metadonneesSauvegarde.contiensSauvegarde() ? "Continuer" : "Jouer");
                button.setOnMouseClicked(event -> {
                    try {
                        new NiveauController(stage, metadonneesSauvegarde, this);
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
        chargerModeClassique(Difficulte.FACILE, ModeDeJeu.CONTRE_LA_MONTRE);
        easyToggle.setDisable(true);
        mediumToggle.setDisable(true);
        hardToggle.setDisable(true);

        stage.show();
    }
}
