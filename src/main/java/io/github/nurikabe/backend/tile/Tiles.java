package io.github.nurikabe.backend.tile;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public interface Tiles {
    int getWidth();
    int getHeight();

    @NotNull
    Tile get(int col, int row);

    @NotNull
    MutableTiles copy();

    @NotNull
    MutableTiles extractNumbers(TileColor startColor);

    @NotNull
    static Tiles fromLines(int width, int height, List<String> lines) {
        final List<Tile> tiles = new ArrayList<>();
        for (String line : lines) {
            for (String symbol : line.split(" ")) {
                tiles.add(Tile.fromSymbol(symbol));
            }
        }

        return new TilesImpl(width, height, tiles);
    }
}

