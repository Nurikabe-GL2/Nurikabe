package io.github.nurikabe.backend.tile;

public enum TileType {
    DOT(0),
    NORMAL(1),
    NUMBER(2);

    private final int key;

    TileType(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
