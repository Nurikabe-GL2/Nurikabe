package io.github.nurikabe.techniques.donnees;

import com.google.gson.Gson;
import io.github.nurikabe.techniques.Techniques;
import io.github.nurikabe.utils.Utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * Classe contenant un mapping entre un ID de technique et les {@link DonneesTechnique informations d'une technique}.
 *
 * @see DonneesTechnique
 */
public class DonneesTechniques extends HashMap<String, DonneesTechnique> {
    private static DonneesTechniques instance;

    public static DonneesTechnique getDonnees(String identifiant) {
        return getInstance().get(identifiant);
    }

    private static DonneesTechniques getInstance() {
        if (instance != null) return instance;

        try (final var stream = new InputStreamReader(Utils.getResourceAsStream(Techniques.class, "/Techniques.json"), StandardCharsets.UTF_8)) {
            return instance = new Gson().fromJson(stream, DonneesTechniques.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
