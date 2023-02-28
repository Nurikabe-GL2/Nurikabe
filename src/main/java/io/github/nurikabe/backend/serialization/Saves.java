package io.github.nurikabe.backend.serialization;

import com.google.gson.Gson;
import io.github.nurikabe.backend.Hypothesis;
import io.github.nurikabe.backend.Nurikabe;

public class Saves {
    private static final Gson GSON = new Gson();

    public static void save(Nurikabe nurikabe) {
        //TODO Construire un objet JSON, contenant une propriété stockant les hypothèses ainsi que leur contenu (cases, undo, redo)
        // Obtenir le BufferedWriter, voir Level#getSaveWriter (puisque Level implémente SerializableLevel)
        // Puis écrire cet objet JSON avec Gson#toJson(JsonObject, Writer)
    }

    public static Hypothesis load(Nurikabe nurikabe) {
        //TODO Obtenir le BufferedWriter, voir Level#getSaveReader (puisque Level implémente SerializableLevel)
        // Lire le JsonObject avec Gson#fromJson(Reader, JsonObject.class)
        // Lire les hypothèses et reconstruire les objets avec les données
        throw new UnsupportedOperationException();
    }
}
