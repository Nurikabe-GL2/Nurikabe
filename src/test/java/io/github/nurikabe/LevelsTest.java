package io.github.nurikabe;

import io.github.nurikabe.backend.level.Level;
import io.github.nurikabe.backend.serialization.SaveContext;
import io.github.nurikabe.backend.tile.NormalTile;
import io.github.nurikabe.backend.tile.Tile;
import io.github.nurikabe.backend.tile.TileColor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LevelsTest {
    @Test
    @DisplayName("load level")
    void loadLevel() throws IOException {
        final Level level = Level.fromFileName(SaveContext.CLASSIC, "facile_1");

        assertEquals(6, level.getWidth());
        assertEquals(8, level.getHeight());
        assertEquals("Puzzle 1", level.getName());
        assertEquals(Difficulte.EASY, level.getDifficulty());

        //Test solution
        checkNormalTile(level.getSolution().get(0, 0), TileColor.WHITE);
        checkNormalTile(level.getSolution().get(1, 0), TileColor.WHITE);
        checkNormalTile(level.getSolution().get(2, 0), TileColor.WHITE);
        checkNormalTile(level.getSolution().get(3, 0), TileColor.WHITE);
        checkNormalTile(level.getSolution().get(4, 0), TileColor.BLACK);
        checkNormalTile(level.getSolution().get(5, 0), TileColor.WHITE);
    }

    private static void checkNormalTile(Tile tile, TileColor expectedColor) {
        assertInstanceOf(NormalTile.class, tile);
        assertEquals(expectedColor, ((NormalTile) tile).getColor());
    }
}
