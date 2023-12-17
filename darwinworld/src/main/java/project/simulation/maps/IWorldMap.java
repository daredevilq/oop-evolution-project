package project.simulation.maps;

import project.Vector2D;
import project.simulation.fetures.AnimalBehavior;

public interface IWorldMap {

    int getWidth();

    int getHeight();

    void mapInitialize();


    Vector2D getNextPosition(Vector2D position, Vector2D moveVector);

    AnimalBehavior getAnimalBehavior();

    int getMoveEnergy();

}
