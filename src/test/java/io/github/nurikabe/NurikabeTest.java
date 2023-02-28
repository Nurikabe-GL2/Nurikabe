package io.github.nurikabe;

import io.github.nurikabe.backend.Nurikabe;
import io.github.nurikabe.backend.Settings;
import io.github.nurikabe.backend.level.Level;
import io.github.nurikabe.backend.serialization.SaveContext;
import io.github.nurikabe.backend.tile.DotTile;
import io.github.nurikabe.backend.tile.NormalTile;
import io.github.nurikabe.backend.tile.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

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
                .allMatch(t -> t.getColor() == TileColor.BLACK));
    }

    @Test
    @DisplayName("number tile override")
    void numberTileOverride() {
        assertThrows(IllegalArgumentException.class, () -> nurikabe.getTiles().set(2, 1, new NormalTile(TileColor.BLACK)));
    }

    @Test
    @DisplayName("out of bounds")
    void outOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> nurikabe.getTiles().get(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> nurikabe.getTiles().get(0, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> nurikabe.getTiles().get(level.getWidth() + 1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> nurikabe.getTiles().get(0, level.getHeight() + 1));
    }

    @Test
    @DisplayName("play")
    void play() {
        assertThrows(IllegalArgumentException.class, () -> nurikabe.touch(2, 1));

        //Test tile type cycle
        nurikabe.touch(0, 0);
        testTile(NormalTile.class, t -> t.getColor() == TileColor.BLACK, 0, 0);

        nurikabe.touch(0, 0);
        testTile(DotTile.class, t -> true, 0, 0);

        nurikabe.touch(0, 0);
        testTile(NormalTile.class, t -> t.getColor() == TileColor.WHITE, 0, 0);
    }

    //TODO activer le test après implémentation des hypothèses
//    @Test
//    @DisplayName("cancel hyphotesis")
//    void cancelHypothesis() {
//        for (int col = 0; col <= 4; col++) { //Black out some tiles
//            nurikabe.touch(col, 0);
//        }
//
//        assertEquals(5, nurikabe.getUndoStack().size());
//        nurikabe.newHypothesis(); //Copy
//        assertEquals(5, nurikabe.getUndoStack().size());
//
//        for (int col = 0; col <= 4; col++) { //Check black tiles
//            testTile(NormalTile.class, t -> t.getColor() == TileColor.BLACK, col, 0);
//        }
//
//        for (int col = 0; col <= 4; col++) { //Transform into dots
//            nurikabe.touch(col, 0);
//        }
//
//        assertEquals(10, nurikabe.getUndoStack().size());
//        nurikabe.cancelHypothesis(); //Go back to the black tiles
//        assertEquals(5, nurikabe.getUndoStack().size());
//
//        for (int col = 0; col <= 4; col++) { //Check black tiles, **not dots**
//            testTile(NormalTile.class, t -> t.getColor() == TileColor.BLACK, col, 0);
//        }
//    }

    //TODO activer le test après implémentation des hypothèses
//    @Test
//    @DisplayName("cancel hypothesis")
//    void cancelHypothesis() {
//        for (int col = 0; col <= 4; col++) { //Black out some tiles
//            nurikabe.touch(col, 0);
//        }
//
//        assertEquals(5, nurikabe.getUndoStack().size());
//        nurikabe.newHypothesis(); //Copy
//        assertEquals(5, nurikabe.getUndoStack().size());
//
//        for (int col = 0; col <= 4; col++) { //Check black tiles
//            testTile(NormalTile.class, t -> t.getColor() == TileColor.BLACK, col, 0);
//        }
//
//        for (int col = 0; col <= 4; col++) { //Transform into dots
//            nurikabe.touch(col, 0);
//        }
//
//        assertEquals(10, nurikabe.getUndoStack().size());
//        nurikabe.acceptHypothesis(); //Keep changes definitely
//        assertEquals(10, nurikabe.getUndoStack().size());
//
//        for (int col = 0; col <= 4; col++) { //Check dot tiles
//            testTile(DotTile.class, t -> true, col, 0);
//        }
//    }

    //TODO activer le test après implémentation des check
//    @Test
//    @DisplayName("check")
//    void check() {
//        for (int col = 0; col <= 4; col++) { //Black out some tiles
//            nurikabe.touch(col, 0);
//        }
//
//        assertEquals(28, nurikabe.check());
//    }

    //TODO activer le test après implémentation des sauvegardes
//    @Test
//    @DisplayName("save")
//    void save() {
//        for (int col = 0; col <= 4; col++) { //Black out some tiles
//            nurikabe.touch(col, 0); //Saves automatically
//        }
//
//        final Nurikabe loaded = Nurikabe.fromSavedLevel(nurikabe.getLevel());
//        assertEquals(nurikabe.getLevel().getName(), loaded.getLevel().getName());
//        assertEquals(nurikabe.getHypotheses().size(), loaded.getHypotheses().size());
//        assertEquals(nurikabe.getUndoStack().size(), loaded.getUndoStack().size());
//        assertEquals(nurikabe.getRedoStack().size(), loaded.getRedoStack().size());
//    }

    @SuppressWarnings("unchecked")
    private <T> void testTile(Class<T> expectedType, Predicate<T> check, int col, int row) {
        assertInstanceOf(expectedType, nurikabe.getTiles().get(col, row));
        assertTrue(check.test((T) nurikabe.getTiles().get(col, row)));
    }
}
