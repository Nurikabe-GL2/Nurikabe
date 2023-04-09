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
        super(x, y, Type.BLANC);
    }

    /**
     * Méthode redéfinie recupContenuCase renvoyant le contenu de la case sous forme de chaîne de caractères
     *
     * @return le contenu de la case
     */
    @Override
    public String getContenuCase() {
        return switch (type) {
            case BLANC -> "b";
            case NOIR -> "n";
            case POINT -> ".";
            default -> throw new IllegalArgumentException("Type invalide: " + type);
        };
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }
}
