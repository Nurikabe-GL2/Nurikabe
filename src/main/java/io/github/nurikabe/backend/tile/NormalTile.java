package io.github.nurikabe.backend.tile;

public class NormalTile extends Tile {
    private final TileColor color;

    public NormalTile(TileColor color) {
        super();
        this.color = color;
    }

    @Override
    public TileType getType() {
        return TileType.NORMAL;
    }

    public TileColor getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NormalTile that = (NormalTile) o;

        return color == that.color;
    }

    @Override
    public int hashCode() {
        return color.hashCode();
    }

    @Override
    public String toString() {
        return color.getSymbol();
    }
}
