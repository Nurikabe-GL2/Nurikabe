package io.github.nurikabe;

import javafx.scene.layout.*;
import javafx.scene.text.*;
import java.util.*;
import java.io.*;


/**
 * Classe servant à charger charger les niveaux pour afficher les grilles miniatures des modes de jeu (classique et ContreLaMontre)
 */
public class NiveauCharger{
  /**
   * espace_boutons (pour les espaces qu'il faudra mettre entre les boutons des niveaux)
   * complete pour savoir si le niveau est complété ou non
   * sauv pour la grille sauvegardée
   * niveau pour la gridpane miniature (la grille qui sera affichée en petit)
   */
  private int espace_boutons, complete;
  private String sauv, cheminSolution;
  private GridPane niveau;

  /**
   * constructeur de la classe NiveauCharger
   * @param cheminSolution le chemin pour récuperer le fichier à charger
   * @param mode_jeu le mode de jeu du niveau à charger
   */
  public NiveauCharger(String cheminSolution, String mode_jeu){
    this.sauv="sauvegarde/"+ cheminSolution +mode_jeu;
    this.cheminSolution ="src/main/resources/niveaux/"+ cheminSolution;
    complete=niveau_complete(sauv);
    niveau=chargerNiveauGrilleMiniature();
  }
 
  /**
   * méthode appelée pour charger la grille miniaturisée du niveau en question
   * @return un objet de type GridPane qui sera affiché dans la sélection des niveaux
   */
  private GridPane chargerNiveauGrilleMiniature(){
    try{
    
        GridPane gridpane = new GridPane();
        gridpane.getStylesheets().add("/css/Plateau.css");
        gridpane.setStyle("-fx-border-color: #51c264; -fx-border-width: 2.5; -fx-background-color: #FFFFFF;");
        //grille_solution=new String[largeur][hauteur];
        Grille<String> temp=Niveau.chargerGrilleSolution(cheminSolution);
        espace_boutons=temp.recupLargeur()*9;

        for (int y = 0; y < temp.recupHauteur(); y++) {
                
            for (int x = 0; x < temp.recupLargeur(); x++) {

                        StackPane p=new StackPane();
                        p.setPrefSize(20,20);
                        if(complete==0){
                         if(temp.recup(x, y).equals("b")||temp.recup(x, y).equals("n"))p.getStyleClass().add("caseblanche");
                        }

                        else if(temp.recup(x, y).equals("n")){
                          //p.getStyleClass().add("caseblanche");
                          p.getStyleClass().add("casenoire");
                        }

                        else if(temp.recup(x, y).equals("b")){
                          p.getStyleClass().add("caseblanche");
                         }

                        if(temp.recup(x, y).equals("b")==false&&temp.recup(x, y).equals("n")==false){
                          p.getStyleClass().add("caseblanche");
                            Text nb = new Text(temp.recup(x, y));
                            p.getChildren().add(nb); 
                        }

                        GridPane.setRowIndex(p, y);
                        GridPane.setColumnIndex(p, x);
                        gridpane.getChildren().addAll(p);
                    }
                }

                return gridpane;

        }catch (Exception e){
        e.printStackTrace();
        return null;
      }
    }

  /**
   * savoir si un fichier dont le nom est entré en paramètres existe
   * @param nom nom du fichier
   * @return 1 si le fichier existe, 0 sinon
   */    
  public static int fichier_existe(String nom){
      File fichier=new File(nom);
      if(fichier.exists()  && !fichier.isDirectory())return 1;
      return 0;
  }

  /**
   * savoir si un niveau est complété ou non
   * @param nom nom du niveau à vérifier avec son chemin complet
   * @return 1 si ce dernier est complété, 0 sinon
   */
  public int niveau_complete(String nom){
      try {
      if(NiveauCharger.fichier_existe(nom)==1){
          try (Scanner lire = new Scanner(new FileInputStream(nom))) {
              String res = lire.nextLine();
              if(res.equals("NIVEAU_COMPLETE")){
                  System.out.println("niveau complete!");
                  return 1;
              }
          }
          return 0;
      }
      }catch(Exception e){
          e.printStackTrace();
          return 0;
      }
      return 0;
  }

  /**
   * getter pour l'espace des boutons
   * @return un entier contenant l'espace à insérer entre les boutons
   */
  public int get_espace_boutons(){
    return espace_boutons;
  }

  /**
   * getter pour la gridpane miniature
   * @return un objet de type GridPane
   */
  public GridPane get_gridpane(){
    return niveau;
  }

  /**
   * savoir si le niveau est complété ou non en vérifiant que complete soit égal à 1
   * @return Booleen
   */
  public boolean get_complete(){
    return(complete==1);
  }
    
}