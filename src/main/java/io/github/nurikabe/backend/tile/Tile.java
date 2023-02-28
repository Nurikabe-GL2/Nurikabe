package io.github.nurikabe.backend.tile;

public abstract class Tile {
    public abstract TileType getType();

    public static Tile fromSymbol(String symbol) {
        return switch(symbol) {
            case "b" -> new NormalTile(TileColor.WHITE);
            case "n" -> new NormalTile(TileColor.BLACK);
            default -> new NumberTile(Integer.parseInt(symbol));
        };
    }
}
