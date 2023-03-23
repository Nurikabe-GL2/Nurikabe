package io.github.nurikabe;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * La classe CaseGraphique
 */
public class CaseGraphique {

    /**
     * variable d'instance représantant le pane de la case
     */
    StackPane pane;

    /**
     * variable d'instance représentant la grille de la case
     */
    Niveau grid;

    int type;

    int x;

    int y;

    /**
     * le constructeur de la classe CaseNormale 
     * @param x la coordonnée x de la case
     * @param y la coordonnée y de la case
     * @param l la longeur de la case 
     * @param L la largeur de la case 
     * @param grid la grille de la case
     */
    public CaseGraphique(int x, int y, int l, int L, Niveau grid){

      this.type=grid.get_case(x, y).recupType();
      this.grid=grid;
      this.x=x;
      this.y=y;
      pane=new StackPane();
      pane.setPrefSize(l, L);
      if(this.type<=0){
          pane.setOnMouseClicked(e -> {
            System.out.println("cliqued");
            if(grid.get_etat_partie()!=true){
              action_clic();
              grid.getUndo().empiler(new Coup(x, y));
              grid.sauvegarder_niveau();
              grid.victoire();
            }
          });
      }
      if(this.type==0){
        //System.out.println("case blanche type==0");
        pane.getStyleClass().add("caseblanche");
     
      }
      else if(type==-1){
        //System.out.println("case blanche type==-1");
        pane.getStyleClass().add(0, "casenoire");
      }
      else if(type==-2){
        //System.out.println("case blanche type==-2");
        pane.getStyleClass().add("caseblanche");
        mettre_cercle(1);
      }
      else {
        Text nb = new Text(grid.get_case(x, y).get_cont_case());
        pane.getChildren().add(nb); 
        pane.getStyleClass().add("caseblanche");
        pane.setPrefSize(l, L);
      }
    }

    /**
     * méthode gérant la réaction de la case au clique, elle s'occupe de changé l'état de la case de façon cyclique et vérifie si la grille est fini

     */
    public void action_clic(){
      //si la case contient un point
       if(type==-2){
         
          if(pane.getChildren()!=null)pane.getChildren().remove(0);
          pane.getStyleClass().remove(0);
          pane.getStyleClass().add(0, "caseblanche");
          type=0;
        }
      //si la case est noire
        else if(type==-1){
          
          mettre_cercle(1);
          type=-2;
          grid.victoire();

        }
      //si la case est blanche
        else if(type==0){
          
          //if(p.getChildren()!=null)p.getChildren().remove(0);
          pane.getStyleClass().remove(0);
          pane.getStyleClass().add(0, "casenoire");
          type=-1;
        
        }
        grid.get_case(x, y).set_etat(this.type);


    }

    /**
     * getter renvoyant le StackPane de la case
     * @return le stackPane de la case
     */
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
