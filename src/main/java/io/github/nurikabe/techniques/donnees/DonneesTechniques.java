package io.github.nurikabe.techniques.donnees;

import com.google.gson.Gson;
import io.github.nurikabe.Utils;
import io.github.nurikabe.techniques.Techniques;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

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
