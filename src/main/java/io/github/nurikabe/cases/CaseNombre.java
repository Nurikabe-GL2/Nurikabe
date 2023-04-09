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
        super(x, y, 1, 0);
        this.nombre = nombre;
    }

    @Override
    public String getContenuCase() {
        return Integer.toString(nombre);
    }

    @Override
    public void mettreEtat(int type) {
    }

    @Override
    public int recupeValeurChemin() {
        return 0;
    }

    @Override
    public void mettreValeurChemin(int valeurChemin) {

    }
}