package io.github.nurikabe.cases;

import io.github.nurikabe.niveaux.Niveau;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite représentant une case de la grille Nurikabe
 */
public abstract class Case implements Serializable {
    public enum Type {
        NOMBRE,
        BLANC,
        NOIR,
        POINT;

        public static Type depuisTexte(String texte) {
            return switch (texte) {
                case "b" -> BLANC;
                case "n" -> NOIR;
                case "." -> POINT;
                default -> {
                    try {
                        yield NOMBRE;
                    } catch (NumberFormatException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
        }
    }

    protected final int x, y;

    /**
     * Représente le type de la case
     */
    protected Type type;

    protected transient Niveau niveau;

    /**
     * Si cette case a été modifiée pendant une hypothèse
     * <br>Cela est utile quand un niveau est rechargé pour différencier les cases après hypothèse des cases avant hypothèse
     */
    private boolean affecteParHypothese;

    private transient int indice;

    private transient List<ObservateurCase> observateursCase;

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

        initialiser();
    }

    // Puisque la sérialisation ne peut pas initialiser le niveau
    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Type getType() {
        return type;
    }

    /**
     * Change l'état de la case à l'état suivant, puis sauvegarde
     */
    public abstract void etatSuivant();

    /**
     * Change l'état de la case à l'état précédent, puis sauvegarde
     */
    public abstract void etatPrecedent();

    /**
     * Change l'état de la case à l'état suivant, enregistrer le coup dans la pile undo,
     * sauvegarde, puis vérifie si le niveau est terminé
     */
    public abstract void onClic();

    public void setAffecteParHypothese(boolean affecteParHypothese) {
        this.affecteParHypothese = affecteParHypothese;
        notifierObservateurs();
    }

    public boolean estAffecteParHypothese() {
        return affecteParHypothese;
    }

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

    public void ajouterObservateur(ObservateurCase observateurCase) {
        observateursCase.add(observateurCase);
    }

    public void notifierObservateurs() {
        for (ObservateurCase observateurCase : observateursCase) {
            observateurCase.onChangement();
        }
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

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        initialiser();
    }

    /**
     * Initialise les champs non initialisés par la sérialisation
     */
    private void initialiser() {
        observateursCase = new ArrayList<>();
    }
}