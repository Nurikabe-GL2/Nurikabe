/**
 * Fichier Coup.java représentant un coup joué par le joueur
 *
 * @author Lazare Maclouf
 */

// Package GitHub
package io.github.nurikabe;

import java.io.Serializable;

/**
 * Classe Coup représentant un coup joué servant pour le Undo Redo
 */
public class Coup implements Serializable {
    /**
     * Variable d'instance représentant la coordonnée x d'un coup joué
     */
    int x;

    /**
     * Variable d'instance représentant la coordonnée y d'un coup joué
     */
    int y;

    /**
     * Constructeur de la classe Coup
     *
     * @param x la coordonnée x de l'endroit du coup joué
     * @param y la coordonnée y de l'endroit du coup joué
     */
    public Coup(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Méthode recupX qui renvoie la coordonnée x du coup joué
     *
     * @return la coordonnée x du coup
     */
    public int recupX() {
        return x;
    }

    /**
     * Méthode recupY qui renvoie la coordonnée y du coup joué
     *
     * @return la coordonnée y du coup
     */
    public int recupY() {
        return y;
    }

    /**
     * Méthode definirX qui permet de définir la coordonnée x
     *
     * @param nouveauX la coordonnée x
     */
    void definirX(int nouveauX) {
        this.x = nouveauX;
    }

    /**
     * Méthode definirY qui permet de définir la coordonnée y
     *
     * @param nouveauY la coordonnée y
     */
    void definirY(int nouveauY) {
        this.y = nouveauY;
    }
}