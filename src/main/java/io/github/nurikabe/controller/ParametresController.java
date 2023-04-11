package io.github.nurikabe.controller;

import io.github.nurikabe.Logging;
import io.github.nurikabe.Parametres;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import org.slf4j.Logger;

/**
 * Contrôleur représentant les différents paramètres modifiables.
 */
public class ParametresController extends FenetreController {
    private static final Logger LOGGER = Logging.getLogger();

    @FXML private CheckBox cochableRemplirCasesNoires;
    @FXML private CheckBox cochableNumeroterChemin;
    @FXML private CheckBox cochableAfficherErreurs;
    @FXML private CheckBox cochableCompleterIleDeUn;
    @FXML private CheckBox cochableCompleterCasesAdjacentes;

    private final Parametres parametres = Parametres.getParametres();

    public ParametresController(Stage stage) {
        super(stage);
    }

    @FXML
    private void onRemplirCasesNoiresAction(ActionEvent event) {
        this.parametres.setRemplirCasesNoires(cochableRemplirCasesNoires.isSelected());
        LOGGER.info("Bouton remplissage actionné: {}", this.parametres.doitRemplirCasesNoires());
    }

    @FXML
    private void onNumeroterCheminAction(ActionEvent event) {
        this.parametres.setNumeroterChemin(cochableNumeroterChemin.isSelected());
        LOGGER.info("Bouton numerotation chemin actionné: {}", (this.parametres.doitNumeroterChemin()));
    }

    @FXML
    private void onAfficherErreursAction(ActionEvent event) {
        this.parametres.setAfficherErreurs(cochableAfficherErreurs.isSelected());
        LOGGER.info("Bouton afficher erreurs actionné: {}", (this.parametres.doitAfficherErreurs()));
    }

    @FXML
    private void onCompleterIleDeUnAction(ActionEvent event) {
        this.parametres.setCompleterIleDeUn(cochableCompleterIleDeUn.isSelected());
        LOGGER.info("Bouton completeter iles taille 1 actionné: {}", (this.parametres.doitCompleterIleDeUn()));
    }

    @FXML
    private void onCompleterCasesAdjacentesAction(ActionEvent event) {
        this.parametres.setCompleterCasesAdjacentes(cochableCompleterCasesAdjacentes.isSelected());
        LOGGER.info("Bouton completer cases adjacentes actionné: {}", (this.parametres.doitCompleterCasesAdjacentes()));
    }

    @FXML // Execute quand le fichier FXML est chargé
    private void initialize() {
        LOGGER.info("Menu Paramètres chargé");

        //Remplissage ou non des cochables en conséquence
        this.cochableRemplirCasesNoires.setSelected(parametres.doitRemplirCasesNoires());
        this.cochableNumeroterChemin.setSelected(parametres.doitNumeroterChemin());
        this.cochableAfficherErreurs.setSelected(parametres.doitAfficherErreurs());
        this.cochableCompleterIleDeUn.setSelected(parametres.doitCompleterIleDeUn());
        this.cochableCompleterCasesAdjacentes.setSelected(parametres.doitCompleterCasesAdjacentes());
    }
}
