package io.github.nurikabe.backend.level;

import io.github.nurikabe.IOUtils;
import io.github.nurikabe.ThrowingFunction;
import io.github.nurikabe.backend.serialization.SaveContext;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Levels {
    private static final Path GAME_DATA_PATH = IOUtils.ROOT_PATH.resolve("game_data");

    public static final List<Level> LEVELS = readGameDataDirectory("levels", p -> Level.fromPath(SaveContext.CLASSIC, p));

    @NotNull
    private static <T> List<T> readGameDataDirectory(@NotNull String subdirectory, ThrowingFunction<Path, T> mappingFunction) {
        try (Stream<Path> pathStream = Files.walk(GAME_DATA_PATH.resolve(subdirectory))) {
            final List<T> tmpLevels = new ArrayList<>();

            for (Path p : pathStream.toList()) {
                if (!Files.isRegularFile(p)) continue;
                if (!IOUtils.getFileNameNoExtensions(p).equals("template")) continue;

                tmpLevels.add(mappingFunction.accept(p));
            }

            return Collections.unmodifiableList(tmpLevels);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
