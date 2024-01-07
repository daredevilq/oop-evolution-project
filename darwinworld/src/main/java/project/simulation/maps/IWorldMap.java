package project.simulation.maps;

import project.Vector2D;
import project.simulation.config.MapSettings;
import project.simulation.config.Modifications;
import project.simulation.fetures.MapAreaType;
import project.simulation.worldelements.Animal;
import project.simulation.worldelements.Grass;

import java.util.List;
import java.util.Map;

public interface IWorldMap {



    MapSettings getMapSettings();

    Boundary getBoundary();

    Map<Vector2D, Grass> getMapPlants();
    List<Animal> getAnimalsList();
    boolean canMoveTo(Vector2D position);

    Vector2D getNextPosition(Vector2D newPositnion);

    void eatPlants();
    void breeding(Modifications modification);
    void moveAnimals();
    void deleteDeadAnimals(List<Animal> deadAnimals);

    void spawnPlants();
    Boundary getJungleBoundary();


    Map<Vector2D, MapAreaType> getFreePlaces();

    void computeFreePlaces();

    void removeFreePlace(Vector2D randomPosition);

    void addAnimal(Animal animal);
    void addPlant(Grass grass);

    boolean isOccupied(Vector2D currentPosition);

    Object objectAt(Vector2D currentPosition);

    void updateDailyStatistics();
}
