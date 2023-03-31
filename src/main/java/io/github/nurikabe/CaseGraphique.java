/**
 * Fichier CaseGraphique.java contenant la classe Case pour représenter une case de la grille Nurikabe
 */

// Package GitHub
package io.github.nurikabe;

// Importation des librairies javaFX

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * Classe CaseGraphique
 */
public class CaseGraphique {
    /**
     * Variable d'instance représantant le panneau de la case
     */
    private final StackPane panneau = new StackPane();

    /**
     * Variable d'instance représentant la grille de la case
     */
    private final Niveau grille;

    /**
     * Variable d'instance représentant la coordonnée x de la case
     */
    private final int x;

    /**
     * Variable d'instance représentant la coordonnée y de la case
     */
    private final int y;

    /**
     * Variable d'instance représentant le type de la case
     */
    private int type;

    /**
     * Constructeur de la classe CaseGraphique. On passe les coordonées de la case, ses dimensions et le niveau
     * de la case. On va y ajouter un stackpane avec un handler sur la case :
     * lorsqu'on clique sur une case on modifie le contenu graphque de la case, on sauvegarde le niveau, on lance la méthode
     * victoire pour voir si on a gagné la partie
     *
     * @param x      la coordonnée x de la case
     * @param y      la coordonnée y de la case
     * @param grille la grille de la case
     */
    public CaseGraphique(int x, int y, Niveau grille) {
        this.type = grille.recupCase(x, y).recupType();
        this.grille = grille;
        this.x = x;
        this.y = y;

        panneau.setPrefSize(30, 30);

        if (this.type <= 0) {
            panneau.setOnMouseClicked(e -> {
                actionClic();
                grille.recupUndo().empiler(new Coup(x, y));
                grille.recupRedo().vider();
                grille.getController().rafraichir();
                grille.sauvegarderNiveau();
                grille.victoire();
            });
        }

        if (this.type == 0)
            panneau.getStyleClass().add("caseblanche");
        else if (type == -1)
            panneau.getStyleClass().add(0, "casenoire");
        
        else if (type == -2) {
            panneau.getStyleClass().add("caseblanche");
            mettreCercle();
        } else {
            Text nb = new Text(grille.recupCase(x, y).recupContenuCase());
            panneau.getChildren().add(nb);
            panneau.getStyleClass().add("caseblanche");
        }
    }

    /**
     * methode appelée à la fin du mode hypothèse pour remettre les cases en noir si besoin
     * executer 3 fois la méthode actionClic pour remettre la couleur de base de la case
     */
    public void mettreAJour(){
        actionClic();
        actionClic();
        actionClic();
    }

    /**
     * Méthode actionClic gérant la réaction de la case au clic, elle s'occupe de changer l'état de la case de façon cyclique et vérifie si la grille est terminée
     */
    public void actionClic() {
        if(grille.estEnModeHypothese())grille.actionHypothese();
        // Si la case contient un point
        if (type == -2) {
            panneau.getChildren().remove(0);
            panneau.getStyleClass().remove(0);
            panneau.getStyleClass().add(0, "caseblanche");
            type = 0;
        }

        // Si la case est noire
        else if (type == -1) {
            mettreCercle();
            type = -2;
            grille.victoire();
        }

        // Si la case est blanche
        else if (type == 0) {
            panneau.getStyleClass().remove(0);
            if(grille.estEnModeHypothese())panneau.getStyleClass().add(0, "casenoireBleue");
            else panneau.getStyleClass().add(0, "casenoire");
            type = -1;
        }

        //Suppression des aides si la case est remplie avec le bon type
        //Point
        if (type == -2) panneau.getStyleClass().removeIf(s -> s.equals("cible-point"));
        //Noir
        if (type == -1) panneau.getStyleClass().removeIf(s -> s.equals("cible-noir"));

        grille.recupCase(x, y).mettreEtat(this.type);
    }

    /**
     * Méthode recupPanneau renvoyant le panneau de la case
     *
     * @return le panneau de la case
     */
    public StackPane recupPanneau() {
        return panneau;
    }

    /**
     * Méthode privée pour mettre un cercle dans la case quand la case se trouve dans l'état point
     */
    private void mettreCercle() {
        // Couleur = 0 pour gris OU couleur = 1 pour noir
        Circle cercle = new Circle(10, 10, 7);
        if(grille.estEnModeHypothese())cercle.setFill(Color.BLUE);
        else cercle.setFill(Color.BLACK);
        panneau.getStyleClass().remove(0);
        panneau.getChildren().add(cercle);
        panneau.getStyleClass().add(0, "caseblanche");
    }

    /**
     * Met la case en surbrillance lors du positionnement des techniques sur la grille
     *
     * @param type Le type de la case qui devrait être insérée
     */
    public void surbrillance(String type) {
        if (this.type == 1) //askip c'est une case nombre
            throw new IllegalStateException("Ne peut pas mettre une case nombre en surbrillance.");

        if (type.equals(".")) //askip c'est une case point
            panneau.getStyleClass().add("cible-point");
        else if (type.equals("n"))
            panneau.getStyleClass().add("cible-noir");
        else
            throw new IllegalArgumentException("Type de case en surbrillance invalide: " + type);
    }
}
