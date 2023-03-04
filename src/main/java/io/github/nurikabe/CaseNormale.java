package io.github.nurikabe;

import io.github.nurikabe.Case;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import java.lang.*;
import javafx.stage.*;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;

/**
 * La classe CaseNormale héritant de la classe abstraite Case 
 */
public class CaseNormale extends Case{

    /**
     * Variable représentant l'état de la case
     */
    int etat;

    /**
     * variable d'instance représantant le pane de la case
     */
    StackPane pane;

    /**
     * variable d'instance représentant la grille de la case
     */
    Grille grid;

    /**
     * le constructeur de la classe CaseNormale 
     * @param x la coordonnée x de la case
     * @param y la coordonnée y de la case
     * @param l la longeur de la case 
     * @param L la largeur de la case 
     * @param grid la grille de la case
     */
    public CaseNormale(int x, int y, int l, int L, Grille grid){
    
      super(x, y,0);
      this.etat=0;
      this.grid=grid;
      pane=new StackPane();
      pane.getStyleClass().add("caseblanche");
      pane.setPrefSize(l, L);

      pane.setOnMouseClicked(e -> {
        System.out.println("cliqued");
          action_clic();
      });
    }

    /**
     * méthode gérant la réaction de la case au clique, elle s'occupe de changé l'état de la case de façon cyclique et vérifie si la grille est fini
     */
    @Override
    public void action_clic(){
      
      //si la case contient un point
       if(etat==2){
         
          if(pane.getChildren()!=null)pane.getChildren().remove(0);
          pane.getStyleClass().remove(0);
          pane.getStyleClass().add(0, "caseblanche");
          etat=0;
       
        }
      //si la case est noire
        else if(etat==1){
          
          mettre_cercle(1);
          etat=2;
          grid.victoire();

        }
      //si la case est blanche
        else {
          
          //if(p.getChildren()!=null)p.getChildren().remove(0);
          pane.getStyleClass().remove(0);
          pane.getStyleClass().add(0, "casenoire");
          etat=1;
        
        }

    }

    /**
     * getter renvoyant l'état de la case sous forme de chaine de caractère
     * @return l'état de la case
     */
    @Override
    public String get_case(){
     
      if(etat==0)return "b";
     
      else if(etat==1)return "n";
     
      else return ".";

    }

    /**
     * getter renvoyant le StackPane de la case
     * @return le stackPane de la case
     */
    @Override
     public StackPane get_pane(){
      return pane;
    }

    /**
     * methode privé pour mettre un cercle dans la case quand la case se trouve dans l'état point 
     * @param couleur la couleur du point
     */
    private void mettre_cercle(int couleur){
        
      //int couleur 0 pour gris et 1 pour noir
        Circle cercle = new Circle(10, 10, 7);
        if(couleur==1)cercle.setFill(Color.BLACK);
        else cercle.setFill(Color.GREY);
        pane.getStyleClass().remove(0);
        pane.getChildren().add(cercle);
        pane.getStyleClass().add(0, "caseblanche");
    
    }
}