package io.github.nurikabe.backend.tile;

public enum TileColor {
    BLACK(0, "n"),
    WHITE(1, "b");

    private final int key;
    private final String symbol;

    TileColor(int key, String symbol) {
        this.key = key;
        this.symbol = symbol;
    }

    public int getKey() {
        return key;
    }

    public String getSymbol() {
        return symbol;
    }
}
