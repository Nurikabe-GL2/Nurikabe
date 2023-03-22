package io.github.nurikabe.techniques;

import io.github.nurikabe.CaseNombre;
import io.github.nurikabe.Niveau;
import io.github.nurikabe.Utils;
import io.github.nurikabe.techniques.donnees.DonneesTechnique;
import io.github.nurikabe.techniques.donnees.DonneesTechniques;
import javafx.scene.image.Image;
import org.jetbrains.annotations.Nullable;

/**
 * Interface des différentes techniques de jeu
 */
public abstract class Technique {
    private final DonneesTechnique donneesTechnique;
    private Image imageTechnique;

    protected Technique() {
        this.donneesTechnique = DonneesTechniques.getDonnees(getIdentifiant());
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
     * Teste si la technique est applicable, si elle l'est, la position de la technique est retournée, sinon, {@code null} est retourné.
     *
     * @return La position de la technique si applicable, {@code null} sinon.
     */
    @Nullable
    public abstract PositionTechniques tester(Niveau grille);

    /**
     * Retourne l'identifiant de la technique.
     *
     * <p>Cet identifiant est utilisé afin de retrouver le nom, la description, la condition d'activation,
     * ainsi que l'illustration à partir de celui-ci, en lisant le fichier {@code Techniques.json}
     */
    protected abstract String getIdentifiant();

    /**
     * Teste si une case est valide ou non (indice valide et case blanche)
     *
     * @param grille la grille courante
     * @param x      la coordonnée x
     * @param y      la coordonnée y
     * @return {@code true} si elle est valide
     */
    protected boolean estValide(Niveau grille, int x, int y) {
        if (estCoordonneeValide(grille, x, y))
            return false;
        return (grille.etat_case(x, y).equals("b"));
    }

    protected boolean estUnNombre(Niveau grille, int x, int y) {
        try {
            if (!estCoordonneeValide(grille, x, y)) return false;

            Integer.parseInt(grille.etat_case(x, y));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    protected boolean estValideCaseNombreEspace(Niveau grille, int x, int y) {
        if (estCoordonneeValide(grille, x, y))
            return false;
        return (grille.get_case(x, y) instanceof CaseNombre);
    }

    protected boolean estCoordonneeValide(Niveau grille, int x, int y) {
        return x < 0 || y < 0 || y >= grille.get_hauteur() || x >= grille.get_largeur();
    }
}
