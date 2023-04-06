package io.github.nurikabe;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * Classe représentant une case de la grille, sur l'interface.
 */
public class CaseGraphique {
    private final StackPane panneau = new StackPane();

    private final Niveau grille;
    private final int x, y;

    private int type;

    public CaseGraphique(int x, int y, Niveau grille) {
        this.type = grille.recupCase(x, y).getType();
        this.grille = grille;
        this.x = x;
        this.y = y;

        panneau.setPrefSize(30, 30);

        if (this.type <= 0) {
            panneau.setOnMouseClicked(e -> {
                // modifie le contenu graphque de la case, on sauvegarde le niveau, on lance la méthode
                // victoire pour voir si on a gagné la partie
                actionClic();
                grille.recupUndo().empiler(new Coup(x, y));
                grille.recupRedo().vider();
                grille.getController().rafraichir();
                grille.sauvegarderNiveau();
                grille.victoire();
            });
        }

        if (type == 0)
            panneau.getStyleClass().add("caseblanche");
        else if (type == -1)
            panneau.getStyleClass().add(0, "casenoire");
        else if (type == -2) {
            panneau.getStyleClass().add("caseblanche");
            mettreCercle();
        } else {
            Text nb = new Text(grille.recupCase(x, y).getContenuCase());
            panneau.getChildren().add(nb);
            panneau.getStyleClass().add("caseblanche");
        }
    }

    /**
     * methode appelée à la fin du mode hypothèse pour remettre les cases en noir si besoin
     * executer 3 fois la méthode actionClic pour remettre la couleur de base de la case
     */
    public void mettreAJour() {
        actionClic();
        actionClic();
        actionClic();
    }

    /**
     * Méthode actionClic gérant la réaction de la case au clic, elle s'occupe de changer l'état de la case de façon cyclique et vérifie si la grille est terminée
     */
    public void actionClic() {
        if (grille.estEnModeHypothese()) grille.actionHypothese();

        if (type == -2) { // Si la case contient un point
            panneau.getChildren().remove(0);
            panneau.getStyleClass().remove(0);
            panneau.getStyleClass().add(0, "caseblanche");
            type = 0;
        } else if (type == -1) { // Si la case est noire
            mettreCercle();
            type = -2;
            grille.victoire();
        } else if (type == 0) { // Si la case est blanche
            panneau.getStyleClass().remove(0);
            if (grille.estEnModeHypothese()) panneau.getStyleClass().add(0, "casenoireBleue");
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

    public StackPane getStackPane() {
        return panneau;
    }

    /**
     * Méthode privée pour mettre un cercle dans la case quand la case se trouve dans l'état point
     */
    private void mettreCercle() {
        // Couleur = 0 pour gris OU couleur = 1 pour noir
        Circle cercle = new Circle(10, 10, 7);
        if (grille.estEnModeHypothese()) cercle.setFill(Color.BLUE);
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
