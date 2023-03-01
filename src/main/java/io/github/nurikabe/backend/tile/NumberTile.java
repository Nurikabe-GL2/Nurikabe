package io.github.nurikabe.backend.tile;

public class NumberTile extends Tile {
    private final int number;

    public NumberTile(int number) {
        super();
        this.number = number;
    }

    @Override
    public TileType getType() {
        return TileType.NUMBER;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumberTile that = (NumberTile) o;

        return number == that.number;
    }

    @Override
    public int hashCode() {
        return number;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
