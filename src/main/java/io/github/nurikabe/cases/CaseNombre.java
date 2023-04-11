package io.github.nurikabe.cases;

/**
 * La classe CaseNombre hérite de la classe abstraite Case et représente les cases qui contiennent un nombre
 */
public class CaseNombre extends Case {
    private final int nombre;

    /**
     * Constructeur de la classe Nombre
     *
     * @param x      la coordonnée x de la case
     * @param y      la coordonnée y de la case
     * @param nombre le nombre de la case
     */
    public CaseNombre(int x, int y, int nombre) {
        super(x, y, Type.NOMBRE);
        this.nombre = nombre;
    }

    @Override
    public String getContenuCase() {
        return Integer.toString(nombre);
    }

    @Override
    public void etatSuivant() {
        throw new IllegalStateException("Impossible d'utiliser une case nombre");
    }

    @Override
    public void etatPrecedent() {
        throw new IllegalStateException("Impossible d'utiliser une case nombre");
    }

    @Override
    public void onClic() {
        throw new IllegalStateException("Impossible d'utiliser une case nombre");
    }

    @Override
    public void onClicPrecedent() {
        throw new IllegalStateException("Impossible d'utiliser une case nombre");
    }
}