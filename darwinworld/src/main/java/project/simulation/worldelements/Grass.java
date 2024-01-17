package project.simulation.worldelements;

import project.Vector2D;

public class Grass implements IWorldElement {
    private final int energy;
    private final Vector2D position;

    public Grass(Vector2D position, int energy) {
        this.position = position;
        this.energy = energy;
    }

    public Vector2D getPosition() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public String toString() {
        return "*";

    }

    @Override
    public String getResourceName() {
        return "img/grass.png";
    }
}
