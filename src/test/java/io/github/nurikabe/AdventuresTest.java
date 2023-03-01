package io.github.nurikabe;

import io.github.nurikabe.backend.Difficulty;
import io.github.nurikabe.backend.level.Adventure;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdventuresTest {
    @Test
    @DisplayName("load adventure")
    void loadAdventure() throws IOException {
        final Adventure adventure = Adventure.fromFileName("facile_1");

        assertEquals("La rivière", adventure.getName());
        assertEquals(Difficulty.EASY, adventure.getDifficulty());
        assertEquals(2, adventure.getSize());
    }
}
