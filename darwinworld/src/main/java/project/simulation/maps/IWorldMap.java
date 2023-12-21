package project.simulation.maps;

import project.Vector2D;
import project.simulation.config.MapSettings;
import project.simulation.fetures.AnimalBehavior;
import project.simulation.worldelements.Animal;
import project.simulation.worldelements.Grass;

import javax.management.ValueExp;
import java.util.Map;

public interface IWorldMap {



    MapSettings getMapSettings();

    Boundary getBoundary();

    Map<Vector2D, Grass> getMapPlants();

    boolean canMoveTo(Vector2D position);

    Vector2D getNextPosition(Vector2D newPositnion);

    Object eatPlants();

    void moveAnimal(Animal animal);
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
