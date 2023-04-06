package io.github.nurikabe.techniques;

import io.github.nurikabe.Case;
import io.github.nurikabe.CaseNombre;
import io.github.nurikabe.Niveau;
import io.github.nurikabe.Utils;
import io.github.nurikabe.techniques.donnees.DonneesTechnique;
import io.github.nurikabe.techniques.donnees.DonneesTechniques;
import javafx.scene.image.Image;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Interface des différentes techniques de jeu
 */
public abstract class Technique {
    private final DonneesTechnique donneesTechnique;
    private Image imageTechnique;

    protected Technique() {
        this.donneesTechnique = DonneesTechniques.getDonnees(getIdentifiant());
    }

    public final String getCategorie() {
        return donneesTechnique.getCategorie();
    }

    public final String getNom() {
        return donneesTechnique.getNom();
    }

    public final String getDescription() {
        return donneesTechnique.getDescription();
    }

    public final String getCondition() {
        return donneesTechnique.getCondition();
    }

    public final Image getImage() {
        if (imageTechnique != null) return imageTechnique;

        final String cheminImage = donneesTechnique.getCheminImage();
        if (!cheminImage.startsWith("/"))
            throw new IllegalArgumentException("Le chemin d'une illustration de technique (" + cheminImage + ") doit commencer par un /");

        return imageTechnique = new Image(Utils.getResourceAsStream(Technique.class, cheminImage));
    }

    /**
     * Teste si la technique est applicable, si elle l'est,
     * la position de la technique est retournée, sinon, {@code null} est retourné.
     *
     * @return La position de la technique si applicable, {@code null} sinon.
     */
    @Nullable
    public abstract PositionTechniques tester(Niveau grille);

    @Override
    public String toString() {
        return "Technique '%s' (%s)".formatted(getNom(), getIdentifiant());
    }

    /**
     * Retourne l'identifiant de la technique.
     *
     * <p>Cet identifiant est utilisé afin de retrouver le nom, la description, la condition d'activation,
     * ainsi que l'illustration à partir de celui-ci, en lisant le fichier {@code Techniques.json}
     */
    protected abstract String getIdentifiant();

    protected boolean estUnNombre(Niveau grille, int x, int y) {
        try {
            if (!estCoordonneeValide(grille, x, y)) return false;

            Integer.parseInt(grille.etatCase(x, y));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    protected boolean estCaseNombre(Niveau grille, int x, int y) {
        if (!estCoordonneeValide(grille, x, y))
            return false;
        return (grille.recupCase(x, y) instanceof CaseNombre);
    }

    protected boolean estCaseBlanche(Niveau grille, int x, int y) {
        if (!estCoordonneeValide(grille, x, y))
            return false;
        final Case uneCase = grille.recupCase(x, y);
        return uneCase.recupContenuCase().equals("b");
    }

    protected boolean estCaseNoire(Niveau grille, int x, int y) {
        if (!estCoordonneeValide(grille, x, y))
            return false;
        final Case uneCase = grille.recupCase(x, y);
        return uneCase.recupContenuCase().equals("n");
    }

    /**
     * Teste si insérer une case noire à ces coordonnées donnera un carré noir.
     *
     * @param grille la grille courante
     * @param x      la coordonnée x
     * @param y      la coordonnée y
     *
     * @return {@code true} si insérer cette case noire donnera un carré noir
     */
    @SuppressWarnings("RedundantIfStatement")
    protected boolean seraUnCarre(Niveau grille, int x, int y) {
        if (estCaseNoire(grille, x + 1, y)
                && estCaseNoire(grille, x, y + 1) && estCaseNoire(grille, x + 1, y + 1))
            return true;

        if (estCaseNoire(grille, x + 1, y)
                && estCaseNoire(grille, x, y - 1) && estCaseNoire(grille, x + 1, y - 1))
            return true;

        if (estCaseNoire(grille, x - 1, y)
                && estCaseNoire(grille, x, y + 1) && estCaseNoire(grille, x - 1, y + 1))
            return true;

        if (estCaseNoire(grille, x - 1, y)
                && estCaseNoire(grille, x, y - 1) && estCaseNoire(grille, x - 1, y - 1))
            return true;

        return false;
    }

    /**
     * Teste si un carré noir existe à ces coordonnées.
     *
     * @param grille la grille courante
     * @param x      la coordonnée x
     * @param y      la coordonnée y
     *
     * @return {@code true} si un carré noir existe
     */
    @SuppressWarnings("RedundantIfStatement")
    protected boolean estUnCarre(Niveau grille, int x, int y) {
        if (estCaseNoire(grille, x, y) && estCaseNoire(grille, x + 1, y)
                && estCaseNoire(grille, x, y + 1) && estCaseNoire(grille, x + 1, y + 1))
            return true;

        if (estCaseNoire(grille, x, y) && estCaseNoire(grille, x + 1, y)
                && estCaseNoire(grille, x, y - 1) && estCaseNoire(grille, x + 1, y - 1))
            return true;

        if (estCaseNoire(grille, x, y) && estCaseNoire(grille, x - 1, y)
                && estCaseNoire(grille, x, y + 1) && estCaseNoire(grille, x - 1, y + 1))
            return true;

        if (estCaseNoire(grille, x, y) && estCaseNoire(grille, x - 1, y)
                && estCaseNoire(grille, x, y - 1) && estCaseNoire(grille, x - 1, y - 1))
            return true;

        return false;
    }

    protected boolean estCoordonneeValide(Niveau grille, int x, int y) {
        return x >= 0 && y >= 0 && y < grille.getHauteur() && x < grille.getLargeur();
    }

    /**
     * Insert un coup à la coordonnée indiquée, uniquement si la condition est remplie.
     *
     * @param cibles La liste de coups à remplir
     * @param grille La grille
     * @param x      La coordonnée X
     * @param y      La coordonnée Y
     * @param cond   La condition à remplir
     * @param type   Le type de case à insérer dans les cibles
     */
    protected void insertionCond(List<Cible> cibles, Niveau grille, int x, int y, Cond cond, String type) {
        if (cond.test(grille, x, y))
            cibles.add(new Cible(x, y, type));
    }

    protected interface Cond {
        boolean test(Niveau grille, int x, int y);
    }
}
