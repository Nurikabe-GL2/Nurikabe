package io.github.nurikabe.techniques;

public class Cible {
    private final int x, y;
    private final String type;

    public Cible(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return type;
    }
}
