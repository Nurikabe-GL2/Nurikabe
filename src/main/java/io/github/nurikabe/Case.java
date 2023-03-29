/**
 * Fichier Case.java contenant la classe Case pour représenter une case de la grille Nurikabe
 */

// Package GitHub
package io.github.nurikabe;

// Importation des librairies javaFX

import java.io.Serializable;

/**
 * Classe abstraite représentant une case
 */
public abstract class Case implements Serializable {
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
    protected int type;

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
    }

    /**
     * Méthode recupType renvoyant le type de la case
     */
    public int recupType() {
        return type;
    }

    /**
     * Méthode recupX qui permet de récupérer la coordonnée x de la case
     *
     * @return la coordonnée x de la case
     */
    public int recupX() {
        return x;
    }

    /**
     * Méthode recupY qui permet de récupérer la coordonnée y de la case
     *
     * @return la coordonnée y de la case
     */
    public int recupY() {
        return y;
    }

    @Override
    public String toString() {
        return recupContenuCase();
    }

    /**
     * Méthode abstraite recupContenuCase qui renvoie le contenu de la case sous forme de chaîne de caractères
     *
     * @return le contenu de la case
     */
    public abstract String recupContenuCase();

    /**
     * Méthode abstraite setEtat qui permet de donner le nouvel état à la case
     *
     * @param type l'état de la case
     */
    public abstract void mettreEtat(int type);
}