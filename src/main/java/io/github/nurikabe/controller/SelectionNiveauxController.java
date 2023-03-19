package io.github.nurikabe.controller;

import io.github.nurikabe.controller.NiveauController;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.helpers.NOPMDCAdapter;

import io.github.nurikabe.Utils;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.geometry.Pos;

import java.util.*;
import java.io.*;

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
        charger_niveaux_difficulte(liste_difficulte.get(0).getDisplayName());
    }

    private String niveauToString(String difficulte, int num){
        if(num<10)return "src/main/resources/niveaux/"+difficulte+"_"+"0"+num+".txt";
        else return "src/main/resources/niveaux/"+difficulte+"_"+num+".txt";
    }

    private GridPane chargerNiveauGrilleMiniature(String name){
    try{
        FileInputStream fichier = new FileInputStream(name);
        Scanner lecture = new Scanner(fichier);
        int hauteur = lecture.nextInt();
        int largeur = lecture.nextInt();
        GridPane gridpane = new GridPane();;
        gridpane.getStylesheets().add("/css/Plateau.css");
        gridpane.setStyle("-fx-border-color: #51c264; -fx-border-width: 2.5; -fx-background-color: #FFFFFF;");
        //grille_solution=new String[largeur][hauteur];
        String temp[][]=new String[largeur][hauteur];
                for (int i = 0; i < largeur; i++) {
                    
                    for (int j = 0; j < hauteur; j++) {
                    
                        temp[i][j] = lecture.next();
                        StackPane p=new StackPane();
                        p.setPrefSize(20,20);
                        p.getStyleClass().add("caseblanche");

                        if(temp[i][j].equals("b")==false&&temp[i][j].equals("n")==false){
                            Text nb = new Text(temp[i][j]);
                            p.getChildren().add(nb); 
                        }

                        GridPane.setRowIndex(p, i);
                        GridPane.setColumnIndex(p, j);
                        gridpane.getChildren().addAll(p);
                    }
                }

                return gridpane;

        }catch (Exception e){
        System.out.println("erreur lors de la lecture de la grille : "+e);
        return null;
      }
    }

    private void charger_niveaux_difficulte(String difficulte){

        HBox hniveau=new HBox(3), hbutton;
        if(difficulte.equals("facile")==true)hbutton=new HBox(90); 
        else if(difficulte.equals("moyen")==true)hbutton=new HBox(120); 
        else hbutton=new HBox(140); 
        int indic=0;

        puzzlesTilePane.getChildren().clear();
        for(int i=1;i<21;i++){
            
            if(indic==3){
                
                VBox v=new VBox(10);
                v.getChildren().add(hniveau);
                v.getChildren().add(hbutton);
                puzzlesTilePane.getChildren().add(v);
                hniveau=new HBox(3);
                if(difficulte.equals("facile")==true)hbutton=new HBox(90); 
                else if(difficulte.equals("moyen")==true)hbutton=new HBox(120); 
                else hbutton=new HBox(140); 
                indic=0;
            
            }
            
            hniveau.getChildren().add(chargerNiveauGrilleMiniature(niveauToString(difficulte, i)));
            Button b;
            if(SelectionNiveauxController.niveau_complete("src/main/resources/sauvegarde/"+niveauToString(difficulte, i).substring(27)+gameModeProperty.get().toString())==1){
                b=new Button("COMPLETE ");
                b.setStyle("-fx-background-color: BLACK");
            }

            else b=new Button("NIVEAU "+i);
            
            hbutton.getChildren().add(b);
            b.setAlignment(Pos.BOTTOM_LEFT);
            b.setOnMouseClicked(MouseEvent -> {
                System.out.println();
                jouer(niveauToString(difficulte, Integer.parseInt(b.getText().substring(7))));
            });
            indic++;
        }

        VBox v=new VBox(10);
        v.getChildren().add(hniveau);
        v.getChildren().add(hbutton);
        puzzlesTilePane.getChildren().add(v);

    }

    public static int fichier_existe(String nom){
        File fichier=new File(nom);
        if(fichier.exists()  && !fichier.isDirectory())return 1;
        return 0;
    }

    public static int niveau_complete(String nom){
        try {
        if(SelectionNiveauxController.fichier_existe(nom)==1){
            FileInputStream niv = new FileInputStream(nom);
            Scanner lire = new Scanner(niv); 
            String res=lire.nextLine();
            lire.close();
            niv.close();
            if(res.equals("NIVEAU_COMPLETE")==true){
                System.out.println("niveau complete!");
                return 1;
            } 
            return 0;
        }
        }catch(Exception e){
            System.out.println("erreur : "+e);
            return 0;
        }
        return 0;
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
            System.out.println(e);

        }
    }
}
