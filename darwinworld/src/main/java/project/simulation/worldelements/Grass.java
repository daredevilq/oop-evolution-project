package project.simulation.worldelements;

import project.Vector2D;

public class Grass extends WorldElement {

    private static final String sign = "*";
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


}
