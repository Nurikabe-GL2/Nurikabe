package io.github.nurikabe.backend.serialization;

import io.github.nurikabe.backend.GameMode;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public class SaveContext {
    public static final SaveContext CLASSIC = newContext(GameMode.CLASSIQUE, null);

    private final Path path;

    private SaveContext(Path path) {
        this.path = path;
    }

    public Path getSavePath(String saveName) {
        return path.resolve(saveName + ".json");
    }

    public static SaveContext newContext(GameMode gameMode, @Nullable String subdirectory) {
        var path = Path.of(System.getProperty("user.home"), "Documents", "Nurikabe-Remake", "Saves", gameMode.getModeName());
        if (subdirectory != null)
            path = path.resolve(subdirectory);

        return new SaveContext(path);
    }
}
