package io.github.nurikabe;

import io.github.nurikabe.backend.Nurikabe;
import io.github.nurikabe.backend.Settings;
import io.github.nurikabe.backend.level.Level;
import io.github.nurikabe.backend.serialization.SaveContext;
import io.github.nurikabe.backend.tile.NormalTile;
import io.github.nurikabe.backend.tile.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NurikabeTest {
    private Nurikabe nurikabe;
    private Level level;

    @BeforeEach
    void loadLevel() throws IOException {
        level = Level.fromFileName(SaveContext.CLASSIC, "facile_1");
        nurikabe = Nurikabe.fromNewLevel(level);
        Settings.reset();
    }

    @Test
    @DisplayName("check empty board")
    void checkEmptyBoard() {
        assertTrue(nurikabe.getTiles()
                .asList().stream()
                .filter(t -> t instanceof NormalTile)
                .map(t -> (NormalTile) t)
                .allMatch(t -> t.getColor() == TileColor.WHITE));
    }

    @Test
    @DisplayName("initialize as white")
    void initializeAsWhite() {
        final Nurikabe nurikabe = Nurikabe.fromNewLevel(level);

        assertTrue(nurikabe.getTiles()
                .asList().stream()
                .filter(t -> t instanceof NormalTile)
                .map(t -> (NormalTile) t)
                .allMatch(t -> t.getColor() == TileColor.WHITE));
    }

    @Test
    @DisplayName("initialize as black")
    void initializeAsBlack() {
        Settings.getSettings().setStartColor(TileColor.BLACK);
        final Nurikabe nurikabe = Nurikabe.fromNewLevel(level);

        assertTrue(nurikabe.getTiles()
                .asList().stream()
                .filter(t -> t instanceof NormalTile)
                .map(t -> (NormalTile) t)
                .allMatch(t -> t.getColor() == TileColor.WHITE));
    }
}
