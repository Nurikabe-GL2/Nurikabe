package io.github.nurikabe.niveaux;

import io.github.nurikabe.Difficulte;
import io.github.nurikabe.utils.IOUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.List;

/**
 * Classe contenant les solutions pour tous les niveaux.
 */
public class Niveaux {
    private static final List<FichierSolution> NIVEAUX;

    static {
        try (final var fichiers = Files.walk(IOUtils.ROOT_PATH.resolve("niveaux"))) {
            NIVEAUX = fichiers
                    .filter(Files::isRegularFile)
                    .sorted(Comparator.comparing(IOUtils::getFileNameNoExtensions))
                    .map(FichierSolution::chargerGrilleSolution)
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException("Impossible de charger les niveaux", e);
        }
    }

    public static List<FichierSolution> getNiveaux(Difficulte difficulte) {
        return NIVEAUX.stream().filter(f -> f.getCheminNiveau().getFileName().toString().startsWith(difficulte.getPrefixeFichier())).toList();
    }
}
