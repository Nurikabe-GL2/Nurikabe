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
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

    private String niveauToString(Difficulte difficulte, int num) {
        if (num < 10) return difficulte + "_" + "0" + num + ".txt";
        else return difficulte + "_" + num + ".txt";
    }

    private void chargerModeClassique(Difficulte difficulte) {
        easyToggle.setDisable(false);
        mediumToggle.setDisable(false);
        hardToggle.setDisable(false);

        //TODO introspection des niveaux
        final Path facile1 = IOUtils.ROOT_PATH.resolve("niveaux").resolve("facile_01.txt");
        final Path facile2 = IOUtils.ROOT_PATH.resolve("niveaux").resolve("facile_02.txt");
        puzzlesTilePane.getChildren().addAll(Stream.of(facile1, facile2)
                .map(FichierSolution::chargerGrilleSolution)
                .map(g -> {
                    try {
                        return Utils.loadFxml(new NiveauSelectionableController(this, stage, g, ModeDeJeu.CLASSIQUE), "_NiveauSelectionable");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList()
        );

//        HBox hniveau = new HBox(3);
//        NiveauCharger n = new NiveauCharger(niveauToString(difficulte, 1), gameModeProperty.get());
//        HBox hbutton = new HBox(n.getEspaceBoutons());
//        int indic = 0, ligne = 0;
//
//        puzzlesTilePane.getChildren().clear();
//        for (int i = 1; i < 21; i++) {
//
//            n = new NiveauCharger(niveauToString(difficulte, i), gameModeProperty.get());
//            if (indic == 5) {
//
//                VBox v = new VBox(10);
//                v.getChildren().add(hniveau);
//                v.getChildren().add(hbutton);
//                puzzlesTilePane.getChildren().add(v);
//                hniveau = new HBox(3);
//                hbutton = new HBox(n.getEspaceBoutons());
//                indic = 0;
//
//            }
//
//
//            hniveau.getChildren().add(n.getGridpane());
//            Button b;
//            if (n.isComplete()) {
//                b = new Button("COMPLETE ");
//                b.setStyle("-fx-background-color: BLACK");
//            } else b = new Button("NIVEAU " + i);
//
//            hbutton.getChildren().add(b);
//            b.setAlignment(Pos.BOTTOM_LEFT);
//            b.setOnMouseClicked(event -> {
//                jouer("src/main/resources/niveaux/" + niveauToString(difficulte, Integer.parseInt(b.getText().substring(7))));
//                refreshLevels();
//            });
//            indic++;
//        }
//
//        VBox v = new VBox(10);
//        v.getChildren().add(hniveau);
//        v.getChildren().add(hbutton);
//        puzzlesTilePane.getChildren().add(v);

    }

    private void chargerModeAventure() { //TODO selection niveau aventure
        easyToggle.setDisable(true);
        mediumToggle.setDisable(true);
        hardToggle.setDisable(true);
        puzzlesTilePane.getChildren().clear();

        HBox conteneurBoutons = new HBox(30);
        VBox conteneurHbox = new VBox(30);
        int nivCourant = 0;
        for (int i = 1, count = 0; i < 21; i++, count++) {
            NiveauCharger n = new NiveauCharger(niveauToString(Difficulte.MOYEN, i), gameModeProperty.get());
            if (count == 6) {
                conteneurHbox.getChildren().add(conteneurBoutons);
                conteneurBoutons = new HBox(30);
                count = 0;
            }
            Button bouton;

            if (n.isComplete()) {
                bouton = new Button("COMPLETE ");
                bouton.setStyle("-fx-background-color: BLACK");
            } else {
                if (nivCourant == 0) {
                    bouton = new Button("NIVEAU " + i);
                    nivCourant = i;
                    bouton.setStyle("-fx-background-color: WHITE");
                } else {
                    bouton = new Button("BLOQUE ");
                    bouton.setStyle("-fx-background-color: GREY");
                }
            }
            bouton.setPrefSize(100, 100);
            if (nivCourant == i) {
                bouton.setOnMouseClicked(event -> {
                    jouer("src/main/resources/niveaux/" + niveauToString(Difficulte.MOYEN, Integer.parseInt(bouton.getText().substring(7))));
                    refreshLevels();
                });
            }
            conteneurBoutons.getChildren().add(bouton);
        }

        puzzlesTilePane.getChildren().add(conteneurHbox);
        stage.show();
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

    private void jouer(String cheminNiveau) {
//        try {
//            new NiveauController(stage, stage.getScene(), cheminNiveau, gameModeProperty.get(), this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
