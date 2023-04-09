package io.github.nurikabe.cases;

import io.github.nurikabe.Coup;
import io.github.nurikabe.niveaux.Niveau;
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
    private final Case aCase;
    private int type;

    public CaseGraphique(int x, int y, Niveau grille) {
        this.aCase = grille.recupCase(x, y);
        this.type = aCase.getType();
        this.grille = grille;
        this.x = x;
        this.y = y;

        aCase.setCaseGraphique(this);

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

        mettreAJour();
    }

    public void mettreAJour() {
        if (type == 0) { //blanc
            panneau.getChildren().clear();
            panneau.getStyleClass().setAll("caseblanche");
        } else if (type == -1) { //noir
            if (grille.estEnModeHypothese()) panneau.getStyleClass().setAll("casenoireBleue");
            else panneau.getStyleClass().setAll("casenoire");
        } else if (type == -2) { //point
            if (aCase.getIndice() > 0) {
                Text indiceChemin = new Text(Integer.toString(aCase.getIndice()));
                indiceChemin.getStyleClass().add("chiffreChemin");
                panneau.getStyleClass().setAll("caseblanche");
                panneau.getChildren().setAll(indiceChemin);
            } else {
                mettreCercle();
            }
        } else if (type == 1) { //Nombre
            Text nb = new Text(aCase.getContenuCase());
            panneau.getChildren().setAll(nb);
            panneau.getStyleClass().setAll("caseblanche");
        }
    }

    /**
     * Méthode actionClic gérant la réaction de la case au clic, elle s'occupe de changer l'état de la case de façon cyclique et vérifie si la grille est terminée
     */
    public void actionClic() {
        if (grille.estEnModeHypothese()) grille.actionHypothese();

        if (type == -2) { // Si la case contient un point
            type = 0;
        } else if (type == -1) { // Si la case est noire
            type = -2;
            grille.victoire();
        } else if (type == 0) { // Si la case est blanche
            type = -1;
        }

        //Suppression des aides si la case est remplie avec le bon type
        //Point
        if (type == -2) panneau.getStyleClass().removeIf(s -> s.equals("cible-point"));
        //Noir
        if (type == -1) panneau.getStyleClass().removeIf(s -> s.equals("cible-noir"));

        aCase.mettreEtat(type);

        grille.calculerIndices();

        mettreAJour();
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
        panneau.getChildren().setAll(cercle);
        panneau.getStyleClass().setAll("caseblanche");
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