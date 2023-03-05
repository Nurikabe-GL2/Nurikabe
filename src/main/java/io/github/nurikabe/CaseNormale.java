package io.github.nurikabe;

import io.github.nurikabe.Case;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import java.lang.*;
import javafx.stage.*;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;

//1 correspond au type case normale et 0 au type caseNombre
public class CaseNormale extends Case{

    int etat;
    StackPane pane;
    Grille grid;

    public CaseNormale(int x, int y, int l, int L, Grille grid){
    
      super(x, y);
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
    
    @Override
    public int get_type(){
      return 1;
    }

    @Override
    public String get_case(){
     
      if(etat==0)return "b";
     
      else if(etat==1)return "n";
     
      else return ".";

    }

    @Override
     public StackPane get_pane(){
      return pane;
    }

    //int couleur 0 pour gris et 1 pour noir
    private void mettre_cercle(int couleur){

        Circle cercle = new Circle(10, 10, 7);
        if(couleur==1)cercle.setFill(Color.BLACK);
        else cercle.setFill(Color.GREY);
        pane.getStyleClass().remove(0);
        pane.getChildren().add(cercle);
        pane.getStyleClass().add(0, "caseblanche");
    
    }
}