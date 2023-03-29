/**
 * Fichier Grille.java pour représenter les grilles du jeu
 */

// Package GitHub
package io.github.nurikabe;

// Importation des librairies javaFX

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Classe Grille pour représenter les grilles de jeu
 */
@SuppressWarnings("unchecked")
public class Grille<T> implements Serializable {
    private final Object[] grille;
    private final int largeur;
    private final int hauteur;

    /**
     * Constructeur de la classe Grille
     *
     * @param largeur la largeur de la grille
     * @param hauteur la hauteur de la grille
     */
    public Grille(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.grille = new Object[largeur * hauteur];
    }

    /**
     * Méthode recup qui permet de récupérer les index de la grille à un x et un y défini
     *
     * @param x l'index x de la grille
     * @param y l'index y de la grille
     *
     * @return la grille avec ses coordonnées
     */
    @NotNull
    public T recup(int x, int y) {
        return (T) grille[recupIndex(x, y)];
    }

    /**
     * Méthode mettre qui permet de mettre des index d'une grille à un x et un y défini
     *
     * @param x         l'index x de la grille
     * @param y         l'index y de la grille
     * @param uneGrille une autre grille
     */
    public void mettre(int x, int y, @NotNull T uneGrille) {
        grille[recupIndex(x, y)] = uneGrille;
    }

    /**
     * Méthode recupLargeur qui retourne la largeur de la grille
     *
     * @return la largeur de la grille
     */
    public int getLargeur() {
        return largeur;
    }

    /**
     * Méthode recupHauteur qui retourne la largeur de la grille
     *
     * @return la largeur de la grille
     */
    public int getHauteur() {
        return hauteur;
    }

    /**
     * Méthode toString qui retourne une chaîne de caractères
     *
     * @return une chaîne de caractères
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                builder.append(recup(x, y));
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    /**
     * Méthode privée recupIndex qui permet de récupérer l'index de la grille
     *
     * @param x la coordonnée x
     * @param y la coordonnée y
     *
     * @return l'index de la grille ou une erreur s'il y en a une
     */
    private int recupIndex(int x, int y) {
        if (x < 0)
            throw new IllegalArgumentException("La coordonnée X ne peut pas être négatif, x = " + x);
        if (y < 0)
            throw new IllegalArgumentException("La coordonnée Y ne peut pas être négatif, y = " + y);
        if (x >= getLargeur())
            throw new IllegalArgumentException("La coordonnée X ne peut pas dépasser la largeur, x = " + x + ", largeur = " + getLargeur());
        if (y >= getHauteur())
            throw new IllegalArgumentException("La coordonnée Y ne peut pas dépasser la hauteur, y = " + y + ", hauteur = " + getHauteur());
        return x + y * largeur;
    }
}
