package io.github.nurikabe.backend.level;

import io.github.nurikabe.IOUtils;
import io.github.nurikabe.Utils;
import io.github.nurikabe.backend.Difficulty;
import io.github.nurikabe.backend.serialization.SaveContext;
import io.github.nurikabe.backend.tile.Tiles;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Level implements SerializableLevel {
    private final SaveContext saveContext;
    private final String saveName;
    private final String name;
    private final Difficulty difficulty;
    private final int width, height;
    private final Tiles solution;

    private Level(SaveContext saveContext, String saveName, String name, Difficulty difficulty, int width, int height, Tiles solution) {
        this.saveContext = saveContext;
        this.saveName = saveName;
        this.name = name;
        this.difficulty = difficulty;
        this.width = width;
        this.height = height;
        this.solution = solution;
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @NotNull
    public Tiles getSolution() {
        return solution;
    }

    @NotNull
    public static Level fromPath(@NotNull SaveContext saveContext, @NotNull Path levelPath) throws IOException {
        return fromData(saveContext, IOUtils.getFileNameNoExtensions(levelPath), Files.newInputStream(levelPath));
    }

    @NotNull
    public static Level fromFileName(@NotNull SaveContext saveContext, @NotNull String levelFileName) throws IOException {
        return fromData(saveContext, levelFileName, Utils.getResourceAsStream(Level.class, "/game_data/levels/%s.txt".formatted(levelFileName)));
    }

    @NotNull
    private static Level fromData(@NotNull SaveContext saveContext, @NotNull String fileName, @NotNull InputStream stream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            final List<String> lines = reader.lines().collect(Collectors.toList());

            final String name = lines.remove(0);
            final Difficulty difficulty = Difficulty.valueOf(lines.remove(0));
            final String[] dimensions = lines.remove(0).split(" ");
            final int width = Integer.parseInt(dimensions[0]);
            final int height = Integer.parseInt(dimensions[1]);

            return new Level(saveContext, fileName, name, difficulty, width, height, Tiles.fromLines(width, height, lines));
        }
    }
}
