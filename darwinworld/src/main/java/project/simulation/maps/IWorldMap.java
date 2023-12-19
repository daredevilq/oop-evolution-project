package project.simulation.maps;

import project.Vector2D;
import project.simulation.config.MapSettings;
import project.simulation.fetures.AnimalBehavior;

public interface IWorldMap {

    Vector2D getNextPosition(Vector2D currPosition, Vector2D moveVector);

    MapSettings getMapSettings();

    Boundary getBoundary();
//    int getWidth();
//
//    int getHeight();
//
//    void mapInitialize();
//
//
//    Vector2D getNextPosition(Vector2D position, Vector2D moveVector);
//
//    AnimalBehavior getAnimalBehavior();
//
//    int getMoveEnergy();

}
