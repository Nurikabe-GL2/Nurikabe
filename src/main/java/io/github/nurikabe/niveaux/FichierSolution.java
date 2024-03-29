package io.github.nurikabe.niveaux;

import io.github.nurikabe.Grille;
import io.github.nurikabe.ModeDeJeu;
import io.github.nurikabe.cases.CaseSolution;
import io.github.nurikabe.utils.IOUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Classe représentant un fichier de niveau Nurikabe.
 * <br>Permet de charger les informations de sauvegarde ainsi que récupérer la grille solution.
 */
public class FichierSolution {
    private final Path cheminNiveau;
    private final Grille<CaseSolution> grille;

    private FichierSolution(Path cheminNiveau, Grille<CaseSolution> grille) {
        this.cheminNiveau = cheminNiveau;
        this.grille = grille;
    }

    public static FichierSolution chargerGrilleSolution(Path cheminNiveau) {
        try (Scanner lecture = new Scanner(Files.newBufferedReader(cheminNiveau))) {
            final int largeur = lecture.nextInt();
            final int hauteur = lecture.nextInt();
            final Grille<CaseSolution> grille = new Grille<>(largeur, hauteur);

            for (int y = 0; y < grille.getHauteur(); y++) {
                for (int x = 0; x < grille.getLargeur(); x++) {
                    grille.mettre(x, y, new CaseSolution(lecture.next()));
                }
            }

            return new FichierSolution(cheminNiveau, grille);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public Path getCheminNiveau() {
        return cheminNiveau;
    }

    public String getNomNiveau() {
        final String nomFichier = IOUtils.getFileNameNoExtensions(cheminNiveau.getFileName());

        final String[] split = nomFichier.split("_");
        final String nom = split[0];
        final String numero = String.valueOf(Integer.parseInt(split[1])); //Retire les 0 inutiles

        return "%c%s %s (%dx%d)".formatted(Character.toUpperCase(nom.charAt(0)), nom.substring(1), numero, grille.getLargeur(), grille.getHauteur());
    }

    public Grille<CaseSolution> getGrille() {
        return grille;
    }

    public MetadonneesSauvegarde getMetadonneesSauvegarde(ModeDeJeu modeDeJeu) {
        return new MetadonneesSauvegarde(this, modeDeJeu);
    }
}
