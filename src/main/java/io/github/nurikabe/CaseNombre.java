/**
 * Fichier CaseNombre.java héritant du fichier Case.java pour représenter les cases à nombres
 * @author Lazare Maclouf
 */

// Package GitHub
package io.github.nurikabe;

// Importation des librairies javaFX
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * La classe CaseNombre hérite de la classe abstraite Case et représente les cases qui contiennent un nombre 
 */
public class CaseNombre extends Case {
   /**
    * Variable d'instance représentant le nombre de la case
    */
   int nombre;

   /**
    * Le panneau de la case
    */
   StackPane panneau;

   /**
    * Constructeur de la classe CaseNombre
    * @param x la coordonnée x de la case
    * @param y la coordonnée y de la case
    * @param l la largeur de la case 
    * @param L la longueur de la case 
    * @param nombre le nombre de la case
    */
   public CaseNombre(int x, int y, int l, int L, int nombre){
      super(x, y,1);
      this.nombre = nombre;
      panneau = new StackPane();
      Text nb = new Text(Integer.toString(nombre));
      panneau.getChildren().add(nb); 
      panneau.getStyleClass().add("caseblanche");
      panneau.setPrefSize(l, L);

    }

   /**
    * Méthode qui définit la réaction de la case quand on clique dessus
    * @param appel Inutile dans ce cas, car cliquer sur une case à nombre ne fait rien
    * @override
    */
   @Override
   public void actionClic(boolean appel){
      System.out.println("Ne rien faire");
   }

   /**
    * Méthode recupCase qui renvoie le nombre de la case sous forme de chaine de caractère
    * @return le nombre de la case
    * @override
    */
   @Override
   public String recupCase() {
      return Integer.toString(nombre);
   }


   /**
    * Méthode recupPanneau qui renvoie le panneau de la case
    * @return le panneau de la case
    */
   @Override
   public StackPane recupPanneau() {
      return panneau;
   }
}