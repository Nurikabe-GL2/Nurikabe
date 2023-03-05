package io.github.nurikabe.controller;

import io.github.nurikabe.controller.PlateauController;
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

public class SelectionNiveauxController extends VBox {
    private static final Logger LOGGER = Logging.getLogger();

    private final Stage stage;
    private final Scene previousScene;

    @FXML
    private ToggleGroup gameModeGroup;
    @FXML
    private ToggleGroup difficultyGroup;

    @FXML
    private TilePane puzzlesTilePane;

    private final ObjectProperty<GameMode> gameModeProperty = new SimpleObjectProperty<>(GameMode.AVENTURE);
    private final ObservableSet<Difficulty> difficulties = FXCollections.observableSet(Difficulty.EASY);

    public SelectionNiveauxController(Stage stage, Scene previousScene) {
        this.stage = stage;
        this.previousScene = previousScene;
    }

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

    private void refreshLevels() {
        LOGGER.info("Mode: {}", gameModeProperty.get());
        LOGGER.info("Difficulties: {}", difficulties);

        puzzlesTilePane.getChildren().addAll(/* TODO */);
    }
    
    @FXML
    private void onBackAction(ActionEvent event) {
        stage.setScene(previousScene);
    }

    @FXML
    private void niveau1(ActionEvent event) {
       PlateauController p=new PlateauController(stage);
    }
}
