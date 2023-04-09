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

    /**
     * Variable permettant de stocker la valeur du chemin le plus long
     */
    int maxChemin = 0;

    public CaseGraphique(int x, int y, Niveau grille) {
        this.aCase = grille.recupCase(x, y);
        this.type = aCase.getType();
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

    protected boolean estCoordonneeValide(Niveau grille, int x, int y) {
        return x >= 0 && y >= 0 && y < grille.getHauteur() && x < grille.getLargeur();
    }

    /**
     * @return {@code true} si la case est un nombre et si elle est dans la grille
     */
    protected boolean estCaseNombre(Niveau grille, int x, int y) {
        if (!estCoordonneeValide(grille, x, y))
            return false;
        return (grille.recupCase(x, y) instanceof CaseNombre);
    }

    /**
     * @return {@code true} si la case est un point et si elle est dans la grille
     */
    protected boolean estCaseRondNombre(Niveau grille, int x, int y) {
        if (!estCoordonneeValide(grille, x, y))
            return false;
        return grille.recupCase(x, y) instanceof CaseNormale caseNormale && caseNormale.valeurChemin != -1;
    }

    /**
     * Méthode privée pour incrémenter le chiffre du chemin de la case celon le
     * nombre le plus grand adjacent
     * 
     * @param nb
     */
    protected void maxChiffreChemin(int nb) {
        if (nb > maxChemin) {
            maxChemin = nb;
        }
    }

    /**
     * Méthode privée pour décrémenter le chiffre du chemin de la case
     */
    protected void decrementChiffreChemin() {
        if (maxChemin > -1) {
            maxChemin--;
        }
    }

    /**
     * Méthode privée pour changer le cercle en chiffre en fonction du nombre de
     * case adjacentes
     * 
     * @param grille
     */
    private void cheminChiffre(Niveau grille) {
        if (estCaseRondNombre(grille, x - 1, y)) {
            int chiffreAdjacent = grille.recupCase(x - 1, y).getValeurChemin();
            maxChiffreChemin(chiffreAdjacent);
        }

        if (estCaseRondNombre(grille, x + 1, y)) {
            int chiffreAdjacent = grille.recupCase(x + 1, y).getValeurChemin();
            maxChiffreChemin(chiffreAdjacent);
        }

        if (estCaseRondNombre(grille, x, y - 1)) {
            int chiffreAdjacent = grille.recupCase(x, y - 1).getValeurChemin();
            maxChiffreChemin(chiffreAdjacent);
        }

        if (estCaseRondNombre(grille, x, y + 1)) {
            int chiffreAdjacent = grille.recupCase(x, y + 1).getValeurChemin();
            maxChiffreChemin(chiffreAdjacent);
        }

        if (estCaseNombre(grille, x - 1, y)) {
            int chiffreAdjacent = Integer.parseInt(grille.recupCase(x - 1, y).getContenuCase());
            maxChiffreChemin(chiffreAdjacent);
        }

        if (estCaseNombre(grille, x + 1, y)) {
            int chiffreAdjacent = Integer.parseInt(grille.recupCase(x + 1, y).getContenuCase());
            maxChiffreChemin(chiffreAdjacent);
        }

        if (estCaseNombre(grille, x, y - 1)) {
            int chiffreAdjacent = Integer.parseInt(grille.recupCase(x, y - 1).getContenuCase());
            maxChiffreChemin(chiffreAdjacent);
        }
        if (estCaseNombre(grille, x, y + 1)) {
            int chiffreAdjacent = Integer.parseInt(grille.recupCase(x, y + 1).getContenuCase());
            maxChiffreChemin(chiffreAdjacent);
        }

        decrementChiffreChemin();

        if (maxChemin > -1) {
            String maxString = Integer.toString(maxChemin);
            Text nb = new Text(maxString);
            panneau.getStyleClass().remove(0);
            panneau.getStyleClass().add(0, "caseblanche");
            nb.getStyleClass().add("chiffreChemin");
            panneau.getChildren().add(nb);
            grille.recupCase(x, y).setValeurChemin(maxChemin);
        } else {
            mettreCercle();
        }

        type = -2;
        grille.victoire();
    }
}