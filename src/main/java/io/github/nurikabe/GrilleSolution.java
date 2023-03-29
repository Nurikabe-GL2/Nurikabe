package io.github.nurikabe;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class GrilleSolution {
    private final Path cheminNiveau;
    private final Grille<String> grille;

    private GrilleSolution(Path cheminNiveau, Grille<String> grille) {
        this.cheminNiveau = cheminNiveau;
        this.grille = grille;
    }

    public static GrilleSolution chargerGrilleSolution(Path cheminNiveau) {
        try (Scanner lecture = new Scanner(Files.newBufferedReader(cheminNiveau))) {
            final int largeur = lecture.nextInt();
            final int hauteur = lecture.nextInt();
            final Grille<String> grille = new Grille<>(largeur, hauteur);

            for (int y = 0; y < grille.getHauteur(); y++) {
                for (int x = 0; x < grille.getLargeur(); x++) {
                    grille.mettre(x, y, lecture.next());
                }
            }

            return new GrilleSolution(cheminNiveau, grille);
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

    public Grille<String> getGrille() {
        return grille;
    }

    public MetadonneesSauvegarde getMetadonneesSauvegarde(ModeDeJeu modeDeJeu) {
        return new MetadonneesSauvegarde(this, modeDeJeu);
    }
}
