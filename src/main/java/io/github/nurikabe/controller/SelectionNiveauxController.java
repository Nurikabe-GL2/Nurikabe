package io.github.nurikabe.controller;

import io.github.nurikabe.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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

import java.util.ArrayList;
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

    @FXML
    private ToggleButton easyToggle, mediumToggle, hardToggle;

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
     * Méthode privée qui est appelée quand le controller est chargé
     * Elle s'occupe d'ajouter le groupe du mode de jeu et de la difficulté, de les ajouter au propriété du jeu en ajoutant un listener,
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
            System.out.println(difficultyGroup.getSelectedToggle());
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
          //  case "allDifficultyToggle" -> Arrays.asList(Difficulty.values());
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

        List<Difficulty> liste_difficulte = new ArrayList<>(difficulties);
        
        if(gameModeProperty.get().getModeName().equals("classic"))charger_mode_classique(liste_difficulte.get(0).getDisplayName());

        else if(gameModeProperty.get().getModeName().equals("adventure"))charger_mode_aventure();

        else charger_mode_contreLaMontre();
    }

    private String niveauToString(String difficulte, int num){
        if(num<10)return difficulte+"_"+"0"+num+".txt";
        else return difficulte+"_"+num+".txt";
    }

    private void charger_mode_classique(String difficulte){
      
        easyToggle.setDisable(false);
        mediumToggle.setDisable(false);
        hardToggle.setDisable(false);


        HBox hniveau=new HBox(3), hbutton;
        NiveauCharger n=new NiveauCharger(niveauToString(difficulte, 1), gameModeProperty.get().toString());
        hbutton=new HBox(n.get_espace_boutons());
        int indic=0, ligne=0;

        puzzlesTilePane.getChildren().clear();
        for(int i=1;i<21;i++){
            
            n=new NiveauCharger(niveauToString(difficulte, i), gameModeProperty.get().toString());
            if(indic==5){
                
                VBox v=new VBox(10);
                v.getChildren().add(hniveau);
                v.getChildren().add(hbutton);
                puzzlesTilePane.getChildren().add(v);
                hniveau=new HBox(3);
                hbutton=new HBox(n.get_espace_boutons()); 
                indic=0;
            
            }
            
            
            hniveau.getChildren().add(n.get_gridpane());
            Button b;
            if(n.get_complete()){
                b=new Button("COMPLETE ");
                b.setStyle("-fx-background-color: BLACK");
            }

            else b=new Button("NIVEAU "+i);
            
            hbutton.getChildren().add(b);
            b.setAlignment(Pos.BOTTOM_LEFT);
            b.setOnMouseClicked(MouseEvent -> {
                System.out.println();
                jouer("src/main/resources/niveaux/"+niveauToString(difficulte, Integer.parseInt(b.getText().substring(7))));
                refreshLevels();
            });
            indic++;
        }

        VBox v=new VBox(10);
        v.getChildren().add(hniveau);
        v.getChildren().add(hbutton);
        puzzlesTilePane.getChildren().add(v);

    }

    private void charger_mode_aventure(){
      
        easyToggle.setDisable(true);
        mediumToggle.setDisable(true);
        hardToggle.setDisable(true);
        puzzlesTilePane.getChildren().clear();

        HBox ConteneurBoutons=new HBox(30);
        VBox ConteneurHbox=new VBox(30);
        NiveauCharger n=new NiveauCharger(niveauToString("moyen", 1), gameModeProperty.get().toString());
        for(int i=1, count=0;i<21;i++, count++){

            n=new NiveauCharger(niveauToString("moyen", i), gameModeProperty.get().toString());
            if(count==6){
                ConteneurHbox.getChildren().add(ConteneurBoutons);
                ConteneurBoutons=new HBox(30);
                count=0;
            }
            Button bouton;
            
            if(n.get_complete()){
                bouton=new Button("COMPLETE ");
                bouton.setStyle("-fx-background-color: BLACK");
            }
            
            else {
                bouton=new Button("NIVEAU "+i);
                bouton.setStyle("-fx-background-color: GREY");
            }
            bouton.setPrefSize(100, 100);
            bouton.setOnMouseClicked(MouseEvent -> {
                System.out.println();
                jouer("src/main/resources/niveaux/"+niveauToString("moyen", Integer.parseInt(bouton.getText().substring(7))));
                refreshLevels(); 
            });
            ConteneurBoutons.getChildren().add(bouton);
        }
        /*A FAIRE */
        puzzlesTilePane.getChildren().add(ConteneurHbox);
        stage.show();
    }

    private void charger_mode_contreLaMontre(){
      
        easyToggle.setDisable(true);
        mediumToggle.setDisable(true);
        hardToggle.setDisable(true);
        puzzlesTilePane.getChildren().clear();

        /*A FAIRE */

        stage.show();
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

    private void jouer(String nom_niveau) {
        try{
            NiveauController c=new NiveauController(stage, stage.getScene(), nom_niveau, gameModeProperty.get().toString());
        }catch(Exception e){
            e.printStackTrace();

        }
    }
}
