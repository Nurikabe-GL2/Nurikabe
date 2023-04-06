package io.github.nurikabe.cases;

/**
 * La classe CaseNormale hérite de la classe abstraite Case et représente les cases qui sont noires ou blanches
 */
public class CaseNormale extends Case {
    /**
     * Constructeur de la classe CaseNormale
     *
     * @param x la coordonnée x de la case
     * @param y la coordonnée y de la case
     */
    public CaseNormale(int x, int y) {
        super(x, y, 0);
    }

    /**
     * Méthode redéfinie recupContenuCase renvoyant le contenu de la case sous forme de chaîne de caractères
     *
     * @return le contenu de la case
     */
    @Override
    public String getContenuCase() {
        if (type == 0)
            return "b";
        else if (type == -1)
            return "n";
        else
            return ".";

    }

    /**
     * Méthode redéfinie mettreEtat qui permet de donner le nouvel état à la case
     *
     * @param type l'état de la case
     */
    public void mettreEtat(int type) {
        this.type = type;
    }
}
