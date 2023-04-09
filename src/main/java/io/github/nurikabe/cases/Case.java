package io.github.nurikabe.cases;

import java.io.Serializable;

/**
 * Classe abstraite représentant une case de la grille Nurikabe
 */
public abstract class Case implements Serializable {
    private final int x, y;

    /**
     * Représente le type de la case
     */
    protected int type;

    /**
     * Représente le numéro virtuel de la case, -1 s'il n'est pas affiché
     */
    int valeurChemin;

    /**
     * Constructeur de la classe Case
     *
     * @param x    la coordonnée x de la case
     * @param y    la coordonnée y de la case
     * @param type le type de la case
     */
    public Case(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.valeurChemin = -1;
    }

    public int getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return getContenuCase();
    }

    /**
     * Méthode abstraite recupContenuCase qui renvoie le contenu de la case sous forme de chaîne de caractères
     *
     * @return le contenu de la case
     */
    public abstract String getContenuCase();

    public abstract void mettreEtat(int type);

    public int getValeurChemin() {
        return valeurChemin;
    }

    public void setValeurChemin(int valeurChemin) {
        this.valeurChemin = valeurChemin;
    }
}