package io.github.nurikabe.backend.serialization;

import com.google.gson.Gson;
import io.github.nurikabe.backend.AdventureState;
import io.github.nurikabe.backend.Hypothesis;
import io.github.nurikabe.backend.Nurikabe;
import io.github.nurikabe.backend.level.Adventure;
import org.jetbrains.annotations.NotNull;

public class Saves {
    private static final Gson GSON = new Gson();

    public static void save(@NotNull Nurikabe nurikabe) {
        //TODO Construire un objet JSON, contenant une propriété stockant les hypothèses ainsi que leur contenu (cases, undo, redo)
        // Obtenir le BufferedWriter, voir Level#getSaveWriter (puisque Level implémente SerializableLevel)
        // Puis écrire cet objet JSON avec Gson#toJson(JsonObject, Writer)
        // Tester avec NurikabeTest#save
    }

    @NotNull
    public static Hypothesis load(@NotNull Nurikabe nurikabe) {
        //TODO Obtenir le BufferedWriter, voir Level#getSaveReader (puisque Level implémente SerializableLevel)
        // Lire le JsonObject avec Gson#fromJson(Reader, JsonObject.class)
        // Lire les hypothèses et reconstruire les objets avec les données
        // Tester avec NurikabeTest#save
        throw new UnsupportedOperationException();
    }

    public static void save(@NotNull AdventureState adventureState) {
        //TODO Construire un objet JSON, contenant l'index du niveau actuel
        // Obtenir le BufferedWriter, voir Adventure#getSaveWriter (puisque Adventure implémente SerializableLevel)
        // Puis écrire cet objet JSON avec Gson#toJson(JsonObject, Writer)
    }

    @NotNull
    public static AdventureState load(@NotNull Adventure adventure) {
        //TODO Obtenir le BufferedWriter, voir Adventure#getSaveReader (puisque Adventure implémente SerializableLevel)
        // Lire le JsonObject avec Gson#fromJson(Reader, JsonObject.class)
        // Lire l'index du niveau actuel et reconstruire l'objet avec les données
        throw new UnsupportedOperationException();
    }
}
