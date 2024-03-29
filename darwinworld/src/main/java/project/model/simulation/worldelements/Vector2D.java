package project.model.simulation.worldelements;

import project.model.simulation.config.RandomGenerator;
import java.util.Objects;


public class Vector2D {
    private final int x;
    private final int y;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other instanceof Vector2D otherCasted) {
            return x == otherCasted.x && y == otherCasted.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public boolean precedes(Vector2D other) {
        return x <= other.x && y <= other.y;
    }

    public boolean follows(Vector2D other) {
        return x >= other.x && y >= other.y;
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(x - other.x, y - other.y);
    }

    public Vector2D opposite() {return new Vector2D(-x, -y); }

    public static Vector2D randomVector(int minX, int maxX, int minY, int maxY) {
        return new Vector2D(RandomGenerator.randInt(minX, maxX), RandomGenerator.randInt(minY, maxY));
    }

}
