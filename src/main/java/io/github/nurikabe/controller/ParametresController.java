package io.github.nurikabe.controller;

import io.github.nurikabe.Logging;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

public class ParametresController extends VBox {

    private static final Logger LOGGER = Logging.getLogger();
    
    private final Stage stage;
    private final Scene previousScene;
    private boolean remplissage;
    private boolean pathNumbering;
    private boolean showErrors;
    private boolean autocompleteOneTile;
    private boolean autocompleteAdjacentTiles;
    
    public ParametresController(Stage stage, Scene previousScene) {
        this.stage = stage;
        this.previousScene = previousScene;
    }

    public boolean getPathNumbering(){
        return this.pathNumbering;
    }

    public boolean getShowErrors(){
        return this.showErrors;
    }

    public boolean getAutocompleteOneTile(){
        return this.autocompleteOneTile;
    }

    public boolean getautocompleteAdjacentTiles(){
        return this.autocompleteAdjacentTiles;
    }

    public void setPathNumbering(boolean pathNumbering){
        this.pathNumbering = pathNumbering;
    }

    public void setShowErrors(boolean showErrors){
        this.showErrors = showErrors;
    }

    public void setAutoCompleteOneTile(boolean autocompleteOneTile){
        this.autocompleteOneTile = autocompleteOneTile;
    }

    public void setAutocompleteAdjacentTiles(boolean autocompleteAdjacentTiles){
        this.autocompleteAdjacentTiles = autocompleteAdjacentTiles;
    }

    public void saveParams(){
        LOGGER.info("Paramtres.JSON: {}", (String)gson.toJson(this));
    }

    public void setDefaultParams(){
        setPathNumbering(false);
        setShowErrors(true);
        setAutoCompleteOneTile(false);
        setAutocompleteAdjacentTiles(false);
    }


    @FXML
    private void onRemplirBlancAction(ActionEvent event) {
        LOGGER.info("Bouton Blanc actionné: {}", ((ToggleButton) event.getTarget()).isSelected());
        
        saveParams();
    }

    @FXML
    private void onRemplirNoirAction(ActionEvent event) {
        LOGGER.info("Bouton Noir actionné: {}", ((ToggleButton) event.getTarget()).isSelected());
        
    }

    @FXML
    private void onCheminFermeAction(ActionEvent event) {
        setPathNumbering( ((CheckBox) event.getTarget()).isSelected() );
        LOGGER.info( "Bouton numerotation chemin actionné: {}", (getPathNumbering()) );
    }
    
    @FXML
    private void onAfficherErreurAction(ActionEvent event) {
        setShowErrors( ((CheckBox) event.getTarget()).isSelected() );
        LOGGER.info( "Bouton afficher erreurs actionné: {}", (getShowErrors()) );
    }

    @FXML
    private void onCompleteTaille1Action(ActionEvent event) {
        setAutoCompleteOneTile( ((CheckBox) event.getTarget()).isSelected() );
        LOGGER.info("Bouton completeter iles taille 1 actionné: {}", (getAutocompleteOneTile()) );
    }

    @FXML
    private void onCompleteAdjacenceAction(ActionEvent event) {
        setAutocompleteAdjacentTiles( ((CheckBox) event.getTarget()).isSelected() );
        LOGGER.info("Bouton completer cases adjacentes actionné: {}", (getautocompleteAdjacentTiles()) );
    }

    @FXML // Execute quand le fichier FXML est chargé
    private void initialize() {
        LOGGER.info("Menu Paramètres chargé");
        //this.getParams();
    }
    /*
     * Classique: Chrono croissant
     * aventure: Chrono croissant, influence sur un score
     * contre la montre: chrono décroissant
     */
    //TODO bouton retour, voir ReglesController
    @FXML
    private void onBackAction(ActionEvent event) {
        stage.setScene(previousScene);
    }
}
