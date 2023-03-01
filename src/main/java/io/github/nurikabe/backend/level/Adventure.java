package io.github.nurikabe.backend.level;

import io.github.nurikabe.IOUtils;
import io.github.nurikabe.Utils;
import io.github.nurikabe.backend.Difficulty;
import io.github.nurikabe.backend.GameMode;
import io.github.nurikabe.backend.serialization.SaveContext;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Adventure implements SerializableLevel {
    private final SaveContext saveContext;
    private final String saveName;
    private final String name;
    private final Difficulty difficulty;
    private final List<Level> levels;

    private Adventure(SaveContext saveContext, String saveName, String name, Difficulty difficulty, List<Level> levels) {
        this.saveContext = saveContext;
        this.saveName = saveName;
        this.name = name;
        this.difficulty = difficulty;
        this.levels = levels;
    }

    @NotNull
    @Override
    public SaveContext getSaveContext() {
        return saveContext;
    }

    @NotNull
    @Override
    public String getSaveName() {
        return saveName;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getSize() {
        return levels.size();
    }

    @NotNull
    public List<Level> getLevels() {
        return levels;
    }

    @NotNull
    public static Adventure fromPath(@NotNull Path levelPath) throws IOException {
        return fromData(IOUtils.getFileNameNoExtensions(levelPath), Files.newInputStream(levelPath));
    }

    @NotNull
    public static Adventure fromFileName(@NotNull String adventureFileName) throws IOException {
        return fromData(adventureFileName, Utils.getResourceAsStream(Adventure.class, "/game_data/adventures/%s.txt".formatted(adventureFileName)));
    }

    @NotNull
    private static Adventure fromData(@NotNull String fileName, @NotNull InputStream stream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            final List<String> lines = reader.lines().collect(Collectors.toList());

            final String name = lines.remove(0);
            final Difficulty difficulty = Difficulty.valueOf(lines.remove(0));
            final SaveContext saveContext = SaveContext.newContext(GameMode.AVENTURE, fileName);

            final List<Level> levels = new ArrayList<>();
            for (String levelFileName : lines) {
                levels.add(Level.fromFileName(saveContext, levelFileName));
            }

            return new Adventure(saveContext, fileName + "-adventure-state", name, difficulty, levels);
        }
    }
}
