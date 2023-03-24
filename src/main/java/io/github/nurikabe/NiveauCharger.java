package io.github.nurikabe;

import javafx.scene.layout.*;
import javafx.scene.text.*;
import java.util.*;
import java.io.*;


/**
 * Classe
 */
public class NiveauCharger{
  private int espace_boutons, complete;
  private String sauv, cheminSolution;
  private GridPane niveau;

  public NiveauCharger(String cheminSolution, String mode_jeu){
    this.sauv="src/main/resources/sauvegarde/"+ cheminSolution +mode_jeu;
    this.cheminSolution ="src/main/resources/niveaux/"+ cheminSolution;
    complete=niveau_complete(sauv);
    niveau=chargerNiveauGrilleMiniature();
  }
 
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

    
  public static int fichier_existe(String nom){
      File fichier=new File(nom);
      if(fichier.exists()  && !fichier.isDirectory())return 1;
      return 0;
  }

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

  public int get_espace_boutons(){
    return espace_boutons;
  }

  public GridPane get_gridpane(){
    return niveau;
  }

  public boolean get_complete(){
    return(complete==1);
  }
    
}