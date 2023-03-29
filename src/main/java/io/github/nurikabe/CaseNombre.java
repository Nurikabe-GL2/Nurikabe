/**
 * Fichier CaseNombre.java héritant du fichier Case.java pour représenter les cases à nombres
 */

// Package GitHub
package io.github.nurikabe;

/**
 * La classe CaseNombre hérite de la classe abstraite Case et représente les cases qui contiennent un nombre
 */
public class CaseNombre extends Case {
    /**
     * Variable d'instance représentant le nombre de la case
     */
    private final int nombre;

    /**
     * Constructeur de la classe Nombre
     *
     * @param x      la coordonnée x de la case
     * @param y      la coordonnée y de la case
     * @param nombre le nombre de la case
     */
    public CaseNombre(int x, int y, int nombre) {
        super(x, y, 1);
        this.nombre = nombre;
    }

    /**
     * Méthode redéfinie recupContenuCase renvoyant le contenu de la case sous forme de chaîne de caractères
     *
     * @return le contenu de la case
     */
    @Override
    public String recupContenuCase() {
        return Integer.toString(nombre);
    }

    @Override
    public void mettreEtat(int type) {

    }
}