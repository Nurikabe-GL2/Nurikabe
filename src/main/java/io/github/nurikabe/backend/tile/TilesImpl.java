package io.github.nurikabe.backend.tile;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class TilesImpl implements MutableTiles {
    private final int width, height;
    private final List<Tile> tiles;

    public TilesImpl(int width, int height, List<Tile> tiles) {
        this.width = width;
        this.height = height;
        this.tiles = tiles;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @NotNull
    @Override
    public Tile get(int col, int row) {
        return tiles.get(getIndex(col, row));
    }

    @Override
    public void set(int col, int row, @NotNull Tile tile) {
        final Tile currentTile = get(col, row);
        if (currentTile instanceof NumberTile) {
            throw new IllegalArgumentException("Cannot modify a number tile at %s x %s".formatted(col, row));
        }

        tiles.set(getIndex(col, row), tile);
    }

    @NotNull
    @Override
    public MutableTiles copy() {
        return new TilesImpl(width, height, new ArrayList<>(tiles));
    }

    @NotNull
    @Override
    public MutableTiles extractNumbers(TileColor startColor) {
        return new TilesImpl(width, height, tiles.stream().map(t -> {
            if (t instanceof NumberTile) {
                return t;
            } else {
                return new NormalTile(startColor);
            }
        }).collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                builder.append(get(col, row)).append(' ');
            }
            builder.append('\n');
        }

        return builder.toString();
    }

    private int getIndex(int col, int row) {
        if (col < 0) throw new IndexOutOfBoundsException("Column must be positive");
        if (row < 0) throw new IndexOutOfBoundsException("Row must be positive");
        if (col >= width)
            throw new IndexOutOfBoundsException("Max width is %d, column given: %d".formatted(width, col));
        if (row >= height)
            throw new IndexOutOfBoundsException("Max width is %d, column given: %d".formatted(height, row));
        return col + row * width;
    }
}
