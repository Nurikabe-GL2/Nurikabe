package io.github.nurikabe.backend.tile;

public class DotTile extends Tile {
    @Override
    public TileType getType() {
        return TileType.DOT;
    }

    @Override
    public String toString() {
        return ".";
    }
}
