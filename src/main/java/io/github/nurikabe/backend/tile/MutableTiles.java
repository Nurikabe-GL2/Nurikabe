package io.github.nurikabe.backend.tile;

import org.jetbrains.annotations.NotNull;

public interface MutableTiles extends Tiles {
    void set(int col, int row, @NotNull Tile tile);
}
