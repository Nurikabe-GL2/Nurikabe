package io.github.nurikabe.controller;

import io.github.nurikabe.Logging;
import io.github.nurikabe.Parametres;
import java.io.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

public class ParametresController extends VBox {

    private static final Logger LOGGER = Logging.getLogger();
    private final Stage stage;
    private final Scene scenePrecedente;


    @FXML
    private CheckBox cochableRemplirCases;
    @FXML
    private CheckBox cochableCheminFerme;
    @FXML
    private CheckBox cochableAfficherErreur;
    @FXML
    private CheckBox cochableCompleteTaille1;
    @FXML
    private CheckBox cochableCompleteAdjacence;

    private Parametres parametres;

    private static String cheminSauvegarde = Parametres.getCheminSauvegarde();
    
    public ParametresController(Stage stage, Scene scenePrecedente) {
        this.stage = stage;
        this.scenePrecedente = scenePrecedente;
        this.parametres = new Parametres();
    }

    /**
     * Serialisation de parametres
     */
    public void saveParams(){
        LOGGER.info("Sauvegarde...");
        try {
            FileOutputStream fileOut = new FileOutputStream(cheminSauvegarde);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.parametres);
            out.close();
            fileOut.close();
            LOGGER.info("Sauvegarde reussie");
         } catch (IOException err) {
            err.printStackTrace();
         }
    }


    /**
     * Deserialisation de parametres, mise à jour du menu de parametres pour cocher ou décocher en conséquence
     */
    public void chargerParams(){
        LOGGER.info("Chargement...");
        try {
            FileInputStream fileIn = new FileInputStream(cheminSauvegarde);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.parametres = (Parametres) in.readObject();
            in.close();
            fileIn.close();
            LOGGER.info("Chargement réussi.");
            LOGGER.info(this.parametres.toString());
        } catch (IOException i) {
            i.printStackTrace();
            System.out.println("Pas de fichier sauvegardé, chargement des paramètres par défaut");
            this.parametres.setDefaultParams();
            this.saveParams();
            this.chargerParams();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Pas de fichier sauvegardé, chargement des paramètres par défaut");
            c.printStackTrace();
            this.parametres.setDefaultParams();
            return;
        } 
        //Remplissage ou non des cochables en conséquence
        this.cochableRemplirCases.setSelected(this.parametres.getRemplirCases());
        this.cochableCheminFerme.setSelected(this.parametres.getNumeroteChemin());
        this.cochableAfficherErreur.setSelected(this.parametres.getAfficheErreurs());
        this.cochableCompleteTaille1.setSelected(this.parametres.getCompleteTaille1());
        this.cochableCompleteAdjacence.setSelected(this.parametres.getCompleteCaseAdj());
         
         
         
    }


    @FXML
    private void onRemplirCasesAction(ActionEvent event) {
        this.parametres.setRemplirCases( ((CheckBox) event.getTarget()).isSelected() );
        LOGGER.info("Bouton remplissage actionné: {}", this.parametres.getRemplirCases() );
    }

    @FXML
    private void onCheminFermeAction(ActionEvent event) {
        this.parametres.setNumeroteChemin( ((CheckBox) event.getTarget()).isSelected() );
        LOGGER.info( "Bouton numerotation chemin actionné: {}", (this.parametres.getNumeroteChemin()) );
    }
    
    @FXML
    private void onAfficherErreurAction(ActionEvent event) {
        this.parametres.setAfficheErreurs( ((CheckBox) event.getTarget()).isSelected() );
        LOGGER.info( "Bouton afficher erreurs actionné: {}", (this.parametres.getAfficheErreurs()) );
    }

    @FXML
    private void onCompleteTaille1Action(ActionEvent event) {
        this.parametres.setCompleteTaille1( ((CheckBox) event.getTarget()).isSelected() );
        LOGGER.info("Bouton completeter iles taille 1 actionné: {}", (this.parametres.getCompleteTaille1()) );
    }

    @FXML
    private void onCompleteAdjacenceAction(ActionEvent event) {
        this.parametres.setCompleteCaseAdj( ((CheckBox) event.getTarget()).isSelected() );
        LOGGER.info("Bouton completer cases adjacentes actionné: {}", (this.parametres.getCompleteCaseAdj()) );
    }

    @FXML // Execute quand le fichier FXML est chargé
    private void initialize() {
        LOGGER.info("Menu Paramètres chargé");
        this.chargerParams();
    }
    
    /**
     * Méthode privé qui est appelée par le bouton quitter
     * Elle se charge d'afficher quel bouton à été appelé et de fermer la fenêtre
     * @param event l'évènement qui à été appelé cette fonction
     */
    @FXML
    private void onBackAction(ActionEvent event) {
        LOGGER.info("Bouton retour actionné");
        saveParams();
        stage.setScene(scenePrecedente);
    }
}
