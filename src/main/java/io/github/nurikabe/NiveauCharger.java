package io.github.nurikabe;

import javafx.scene.layout.*;
import javafx.scene.text.*;
import java.util.*;
import java.io.*;


/**
 * Classe
 */
public class NiveauCharger{

  private int largeur, hauteur, espace_boutons, complete;
  private String sauv, nom_niveau;
  private GridPane niveau;

  public NiveauCharger(String sauv, String nom_niveau){
    this.sauv=sauv;
    this.nom_niveau=nom_niveau;
    complete=niveau_complete(sauv);
    niveau=chargerNiveauGrilleMiniature();
  }
 
  private GridPane chargerNiveauGrilleMiniature(){
    try{
        FileInputStream fichier = new FileInputStream(nom_niveau);
        Scanner lecture = new Scanner(fichier);
        this.hauteur = lecture.nextInt();
        this.largeur = lecture.nextInt();
        espace_boutons=largeur*9;
    
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
                        if(complete==0){
                         if(temp[i][j].equals("b")||temp[i][j].equals("n"))p.getStyleClass().add("caseblanche");
                        }

                        else if(temp[i][j].equals("n")){
                          //p.getStyleClass().add("caseblanche");
                          p.getStyleClass().add("casenoire");
                        }

                        else if(temp[i][j].equals("b")){
                          p.getStyleClass().add("caseblanche");
                         }

                        if(temp[i][j].equals("b")==false&&temp[i][j].equals("n")==false){
                          p.getStyleClass().add("caseblanche");
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

    
  public static int fichier_existe(String nom){
      File fichier=new File(nom);
      if(fichier.exists()  && !fichier.isDirectory())return 1;
      return 0;
  }

  public int niveau_complete(String nom){
      try {
      if(NiveauCharger.fichier_existe(nom)==1){
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