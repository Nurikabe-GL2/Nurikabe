package io.github.nurikabe.controller;

import io.github.nurikabe.Logging;
import io.github.nurikabe.Parametres;
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

    @FXML // Execute quand le fichier FXML est chargé
    private void initialize() {
        LOGGER.info("Menu Paramètres chargé");

        //Les changements sur les CheckBox seront reflétés sur les paramètres
        //La propriété à droite doit être celle des paramètres,
        // car c'est celle qui doit être appliquée quand on crée le contrôleur
        cochableRemplirCasesNoires.selectedProperty().bindBidirectional(parametres.remplirCasesNoiresProperty());
        cochableNumeroterChemin.selectedProperty().bindBidirectional(parametres.numeroterCheminProperty());
        cochableAfficherErreurs.selectedProperty().bindBidirectional(parametres.afficherErreursProperty());
        cochableCompleterIleDeUn.selectedProperty().bindBidirectional(parametres.completerIleDeUnProperty());
        cochableCompleterCasesAdjacentes.selectedProperty().bindBidirectional(parametres.completerCasesAdjacentesProperty());

        //Affichage du message d'incompatibilité si les cases noires sont utilisées
        cochableAfficherErreurs.getGraphic().visibleProperty().bind(cochableRemplirCasesNoires.selectedProperty().and(cochableAfficherErreurs.selectedProperty()));
        cochableCompleterIleDeUn.getGraphic().visibleProperty().bind(cochableRemplirCasesNoires.selectedProperty().and(cochableCompleterIleDeUn.selectedProperty()));
        cochableCompleterCasesAdjacentes.getGraphic().visibleProperty().bind(cochableRemplirCasesNoires.selectedProperty().and(cochableCompleterCasesAdjacentes.selectedProperty()));
    }
}
