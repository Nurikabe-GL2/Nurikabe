package io.github.nurikabe.cases;

import java.io.Serializable;

/**
 * Classe abstraite représentant une case de la grille Nurikabe
 */
public abstract class Case implements Serializable {
    public enum Type {
        NOMBRE,
        BLANC,
        NOIR,
        POINT
    }

    private final int x, y;

    /**
     * Représente le type de la case
     */
    protected Type type;

    private transient int indice;

    private transient CaseGraphique caseGraphique;

    /**
     * Constructeur de la classe Case
     *
     * @param x    la coordonnée x de la case
     * @param y    la coordonnée y de la case
     * @param type le type de la case
     */
    public Case(int x, int y, Type type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public abstract void setType(Type type);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * @return le numéro de l'indice ou {@code -1} si l'indice n'est pas applicable
     */
    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public void setCaseGraphique(CaseGraphique caseGraphique) {
        this.caseGraphique = caseGraphique;
    }

    public CaseGraphique getCaseGraphique() {
        return caseGraphique;
    }

    @Override
    public String toString() {
        return getContenuCase();
    }

    /**
     * Méthode abstraite getContenuCase qui renvoie le contenu de la case sous forme de chaîne de caractères
     *
     * @return le contenu de la case
     */
    public abstract String getContenuCase();
}