package io.github.nurikabe.techniques;

import com.google.gson.Gson;
import io.github.nurikabe.Niveau;
import io.github.nurikabe.Utils;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class Techniques {
    private static class DonneesTechniques extends HashMap<String, DonneesTechnique> {}

    private static class DonneesTechnique {
        private final String nom, description, condition, cheminImage;

        private DonneesTechnique(String nom, String description, String condition, String cheminImage) {
            this.nom = nom;
            this.description = description;
            this.condition = condition;
            this.cheminImage = cheminImage;
        }
    }

    private static final List<Technique> TECHNIQUES = List.of(
            new CarreIsole(),
            new ContinuiteDeLile(),
            new ContinuiteDunMur(),
            new EviterZoneMur(),
            new IleDeUn(),
            new IleExtensibleDansDeuxDirections(),
            new IleExtensionDepuisUnIndice(),
            new IndiceAdjacentsEnDiagonale(),
            new IndicesSeparerParCaseBlanche()
    );

    private static final DonneesTechniques DONNEES_TECHNIQUES;

    static {
        try (final var stream = new InputStreamReader(Utils.getResourceAsStream(Techniques.class, "/Techniques.json"), StandardCharsets.UTF_8)) {
            DONNEES_TECHNIQUES = new Gson().fromJson(stream, DonneesTechniques.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    //TODO getter donnees techniques

    @Nullable
    public static PositionTechniques trouverTechnique(Niveau niveau) {
        for (Technique technique : TECHNIQUES) {
            final PositionTechniques positionTechniques = technique.tester(niveau);
            if (positionTechniques != null) {
                return positionTechniques;
            }
        }

        return null;
    }
}
