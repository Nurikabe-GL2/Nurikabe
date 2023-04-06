package io.github.nurikabe.controller;

import io.github.nurikabe.Logging;
import io.github.nurikabe.Parametres;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import org.slf4j.Logger;

/**
 * Contrôleur représentant les différents paramètres modifiables.
 */
public class ParametresController extends FenetreController {
    private static final Logger LOGGER = Logging.getLogger();

    @FXML private CheckBox cochableRemplirCases;
    @FXML private CheckBox cochableCheminFerme;
    @FXML private CheckBox cochableAfficherErreur;
    @FXML private CheckBox cochableCompleteTaille1;
    @FXML private CheckBox cochableCompleteAdjacence;

    private final Parametres parametres = Parametres.getParametres();

    public ParametresController(Stage stage, Scene scenePrecedente) {
        super(stage, scenePrecedente);
    }

    @FXML
    private void onRemplirCasesAction(ActionEvent event) {
        this.parametres.setRemplirCases(cochableRemplirCases.isSelected());
        LOGGER.info("Bouton remplissage actionné: {}", this.parametres.getRemplirCases());
    }

    @FXML
    private void onCheminFermeAction(ActionEvent event) {
        this.parametres.setNumeroteChemin(cochableCheminFerme.isSelected());
        LOGGER.info("Bouton numerotation chemin actionné: {}", (this.parametres.getNumeroteChemin()));
    }

    @FXML
    private void onAfficherErreurAction(ActionEvent event) {
        this.parametres.setAfficheErreurs(cochableAfficherErreur.isSelected());
        LOGGER.info("Bouton afficher erreurs actionné: {}", (this.parametres.getAfficheErreurs()));
    }

    @FXML
    private void onCompleteTaille1Action(ActionEvent event) {
        this.parametres.setCompleteTaille1(cochableCompleteTaille1.isSelected());
        LOGGER.info("Bouton completeter iles taille 1 actionné: {}", (this.parametres.getCompleteTaille1()));
    }

    @FXML
    private void onCompleteAdjacenceAction(ActionEvent event) {
        this.parametres.setCompleteCaseAdj(cochableCompleteAdjacence.isSelected());
        LOGGER.info("Bouton completer cases adjacentes actionné: {}", (this.parametres.getCompleteCaseAdj()));
    }

    @FXML // Execute quand le fichier FXML est chargé
    private void initialize() {
        LOGGER.info("Menu Paramètres chargé");
        LOGGER.info("Chargement...");

        //Remplissage ou non des cochables en conséquence
        this.cochableRemplirCases.setSelected(parametres.getRemplirCases());
        this.cochableCheminFerme.setSelected(parametres.getNumeroteChemin());
        this.cochableAfficherErreur.setSelected(parametres.getAfficheErreurs());
        this.cochableCompleteTaille1.setSelected(parametres.getCompleteTaille1());
        this.cochableCompleteAdjacence.setSelected(parametres.getCompleteCaseAdj());
    }
}
