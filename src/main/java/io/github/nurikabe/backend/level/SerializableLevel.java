package io.github.nurikabe.backend.level;

import io.github.nurikabe.backend.serialization.SaveContext;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public interface SerializableLevel {
    SaveContext getSaveContext();

    /** Name of the save file, this can be different from the level name as long as the name is consistently reproducible */
    String getSaveName();

    default boolean hasSave() {
        return Files.exists(getSavePath());
    }

    default BufferedReader getSaveReader() throws IOException {
        if (!hasSave())
            throw new IllegalStateException("No game save at " + getSavePath());

        return Files.newBufferedReader(getSavePath());
    }

    default BufferedWriter getSaveWriter() throws IOException {
        Files.createDirectories(getSavePath().getParent());
        return Files.newBufferedWriter(getSavePath());
    }

    private Path getSavePath() {
        return getSaveContext().getSavePath(getSaveName());
    }
}
